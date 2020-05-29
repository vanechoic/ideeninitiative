package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.*;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IdeeService {

    static final Logger logger = LoggerFactory.getLogger(IdeeService.class);

    @Autowired
    private IdeeRepository ideeRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public List<Idee> meineIdeenAbrufen(String benutzername) {
        logger.warn("IdeeService.meineIdeenAbrufen()");
        List<Idee> ideenZuErfasser = ideeRepository.findAllByErfasserBenutzername(benutzername);
        return ideenZuErfasser;
    }

    public List<Idee> alleIdeenAbrufen() {
        return ideeRepository.findAllByBearbeitungsstatusNotLike(Ideenstatus.ANGELEGT);
    }

    public Idee neueIdeeAnlegen(Idee idee){
        //TODO: Interne Idee MUSS Handlungsfeld haben
        //TODO: Produktidee MUSS Sparte, Zusatzinformationen und Zielgruppe haben
        return ideeRepository.save(idee);
    }

    public void ideeLoeschen(String benutzername, Idee zuLoeschendeIdee) throws IdeeExistiertNichtException, KeineBefugnisFuerIdeeAenderungenException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "interneIdeeHandlungsfeld.idee", "produktideeSparte.idee", "produktideeVertriebsweg.idee", "produktideeZielgruppe.idee", "produktideeZusatzinformation.idee").withIgnoreNullValues();
        List<Idee> zutreffendeIdeen = ideeRepository.findAll(Example.of(zuLoeschendeIdee, matcher));
        pruefeObZuModifizierendeIdeeExistiert(zutreffendeIdeen, zuLoeschendeIdee);
        pruefeDassDieIdeeVomAuthentifiziertenBenutzerErstelltWurde(benutzername, zuLoeschendeIdee);
        ideeRepository.delete(zutreffendeIdeen.get(0));
    }

    private void pruefeDassDieIdeeVomAuthentifiziertenBenutzerErstelltWurde(String benutzername, Idee idee) throws KeineBefugnisFuerIdeeAenderungenException {
        if(idee.getErfasser() == null || !idee.getErfasser().getBenutzername().equals(benutzername)){
            throw new KeineBefugnisFuerIdeeAenderungenException(idee, benutzername);
        }
    }

    private void pruefeObZuModifizierendeIdeeExistiert(List<Idee> zutreffendeIdeen, Idee zuModifizierendeIdee) throws IdeeExistiertNichtException{
        if(zutreffendeIdeen == null || zutreffendeIdeen.isEmpty()){
            throw new IdeeExistiertNichtException(zuModifizierendeIdee, 0);
        }
        if(zutreffendeIdeen.size() > 1){
            throw new IdeeExistiertNichtException(zuModifizierendeIdee, zutreffendeIdeen.size());
        }
    }


    public void ideeBearbeiten(Idee idee) throws IdeeExistiertNichtException {
        Idee zuAktualisierendeIdee = ladeIdeeAusDatenbank(idee);
        //Idee aktualisieren - ID und Erstellzeitpunkt werden beibehalten
        zuAktualisierendeIdee.setTitel(idee.getTitel());
        zuAktualisierendeIdee.setBeschreibung(idee.getBeschreibung());
        zuAktualisierendeIdee.setBearbeitungsstatus(idee.getBearbeitungsstatus());
        zuAktualisierendeIdee.setBegruendung(idee.getBegruendung());
        zuAktualisierendeIdee.setErfasser(idee.getErfasser());
        zuAktualisierendeIdee.setFachspezialist(idee.getFachspezialist());
        zuAktualisierendeIdee.setTyp(idee.getTyp());
        if(Ideentyp.INTERNE_IDEE == idee.getTyp()){
            zuAktualisierendeIdee.setInterneIdeeHandlungsfeld(idee.getInterneIdeeHandlungsfeld());
        } else{
            zuAktualisierendeIdee.setProduktideeVertriebsweg(idee.getProduktideeVertriebsweg());
            zuAktualisierendeIdee.setProduktideeZielgruppe(idee.getProduktideeZielgruppe());
            zuAktualisierendeIdee.setProduktideeZusatzinformation(idee.getProduktideeZusatzinformation());
            zuAktualisierendeIdee.setProduktideeSparte(idee.getProduktideeSparte());
        }
        ideeRepository.save(zuAktualisierendeIdee);
    }

    protected Idee ladeIdeeAusDatenbank(Idee ideeWerteAusDTO) throws IdeeExistiertNichtException {
        List<Idee> zutreffendeIdeen = ideeRepository.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(ideeWerteAusDTO.getTitel(), ideeWerteAusDTO.getErstellzeitpunkt(), ideeWerteAusDTO.getErfasser().getBenutzername());
        pruefeObZuModifizierendeIdeeExistiert(zutreffendeIdeen, ideeWerteAusDTO);
        return zutreffendeIdeen.get(0);
    }

    public void ideeVeroeffentlichen(String benutzername, Idee idee) throws IdeeBereitsVeroeffentlichtException, KeinFachspezialistVerfuegbarException, KeineBefugnisFuerIdeeAenderungenException, IdeeExistiertNichtException {
        Idee zuVeroeffentlichendeIdee = ladeIdeeAusDatenbank(idee);
        pruefeDassDieIdeeVomAuthentifiziertenBenutzerErstelltWurde(benutzername, zuVeroeffentlichendeIdee);
        pruefeIdeeBefindetSichImStatusAngelegt(zuVeroeffentlichendeIdee);
        //Suche den Fachspezialisten mit einem der angegebenen Fachbereiche
        List<Mitarbeiter> fachspezialistenMitPassenderSpezialisierung = null;
        switch (zuVeroeffentlichendeIdee.getTyp()){
            case INTERNE_IDEE:
                //TODO: Prüfung, ob Handlungsfeld vorliegt. Wenn nicht: Fehler!
                fachspezialistenMitPassenderSpezialisierung = mitarbeiterRepository.findAllByIstFachspezialistTrueAndFachspezialistHandlungsfelderHandlungsfeldLike(zuVeroeffentlichendeIdee.getInterneIdeeHandlungsfeld().getHandlungsfeld());
                break;
            case PRODUKTIDEE:
                List<Sparte> sparte = idee.getProduktideeSparte() == null ? new ArrayList<>() : Arrays.asList(zuVeroeffentlichendeIdee.getProduktideeSparte().getSparte());
                fachspezialistenMitPassenderSpezialisierung = mitarbeiterRepository.findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(zuVeroeffentlichendeIdee.getProduktideeVertriebswegeWerte(), sparte, zuVeroeffentlichendeIdee.getProduktideeZielgruppeWerte());
                break;
        }
        pruefeObEinFachspezialistGefundenWurde(fachspezialistenMitPassenderSpezialisierung, zuVeroeffentlichendeIdee);
        Mitarbeiter fachSpezialistMitWenigstenZugewiesenenIdeen = fachspezialistenMitPassenderSpezialisierung.stream().min(Comparator.comparingInt(Mitarbeiter::getAnzahlZugewieseneIdeen)).orElseThrow();
        zuVeroeffentlichendeIdee.setFachspezialist(fachSpezialistMitWenigstenZugewiesenenIdeen);
        zuVeroeffentlichendeIdee.setBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG);
        ideeRepository.save(zuVeroeffentlichendeIdee);
    }

    private void pruefeObEinFachspezialistGefundenWurde(List<Mitarbeiter> fachspezialisten, Idee idee) throws KeinFachspezialistVerfuegbarException {
        if(fachspezialisten == null || fachspezialisten.isEmpty()){
            throw new KeinFachspezialistVerfuegbarException(idee);
        }
    }

    private void pruefeIdeeBefindetSichImStatusAngelegt(Idee idee) throws IdeeBereitsVeroeffentlichtException {
        if(!idee.getBearbeitungsstatus().equals(Ideenstatus.ANGELEGT)){
            throw new IdeeBereitsVeroeffentlichtException(idee);
        }
    }
}
