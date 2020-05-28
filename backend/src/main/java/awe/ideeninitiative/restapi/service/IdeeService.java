package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.*;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import awe.ideeninitiative.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        return ideeRepository.save(idee);
    }

    public void ideeLoeschen(String benutzername, Idee zuLoeschendeIdee) throws IdeeExistiertNichtException, KeineBefugnisZumIdeeLoeschenException {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "interneIdeeHandlungsfeld.idee", "produktideeSparte.idee", "produktideeVertriebsweg.idee", "produktideeZielgruppe.idee", "produktideeZusatzinformation.idee").withIgnoreNullValues();
        List<Idee> zutreffendeIdeen = ideeRepository.findAll(Example.of(zuLoeschendeIdee, matcher));
        pruefeObZuLoeschendeIdeeExistiert(zutreffendeIdeen, zuLoeschendeIdee);
        pruefeDassDieIdeeVomAuthentifiziertenBenutzerErstelltWurde(benutzername, zuLoeschendeIdee);
        ideeRepository.delete(zutreffendeIdeen.get(0));
    }

    private void pruefeDassDieIdeeVomAuthentifiziertenBenutzerErstelltWurde(String benutzername, Idee zuLoeschendeIdee) throws KeineBefugnisZumIdeeLoeschenException {
        if(zuLoeschendeIdee.getErfasser() == null || !zuLoeschendeIdee.getErfasser().getBenutzername().equals(benutzername)){
            throw new KeineBefugnisZumIdeeLoeschenException(zuLoeschendeIdee, benutzername);
        }
    }

    private void pruefeObZuLoeschendeIdeeExistiert(List<Idee> zutreffendeIdeen, Idee zuLoeschendeIdee) throws IdeeExistiertNichtException{
        if(zutreffendeIdeen == null || zutreffendeIdeen.isEmpty()){
            throw new IdeeExistiertNichtException(zuLoeschendeIdee, 0);
        }
        if(zutreffendeIdeen.size() > 1){
            throw new IdeeExistiertNichtException(zuLoeschendeIdee, zutreffendeIdeen.size());
        }
    }


    public void ideeBearbeiten(Idee idee) throws IdeeExistiertNichtException {
        //Idee suchen
        List<Idee> zutreffendeIdeen = ideeRepository.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(idee.getTitel(), idee.getErstellzeitpunkt(), idee.getErfasser().getBenutzername());
        pruefeObZuLoeschendeIdeeExistiert(zutreffendeIdeen, idee);
        //Idee aktualisieren - ID und Erstellzeitpunkt werden beibehalten
        Idee zuAktualisierendeIdee = zutreffendeIdeen.get(0);
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

    public void ideeVeroeffentlichen(Idee idee) throws IdeeBereitsVeroeffentlichtException, KeinFachspezialistVerfuegbarException {
        pruefeIdeeBefindetSichImStatusAngelegt(idee);
        //Suche den Fachspezialisten mit einem der angegebenen Fachbereiche
        List<Mitarbeiter> fachspezialistenMitPassenderSpezialisierung = null;
        switch (idee.getTyp()){
            case INTERNE_IDEE:
                fachspezialistenMitPassenderSpezialisierung = mitarbeiterRepository.findAllByIstFachspezialistTrueAndFachspezialistHandlungsfelderHandlungsfeldLike(idee.getInterneIdeeHandlungsfeld().getHandlungsfeld());
                break;
            case PRODUKTIDEE:
                List<Sparte> sparte = idee.getProduktideeSparte() == null ? new ArrayList<>() : Arrays.asList(idee.getProduktideeSparte().getSparte());
                fachspezialistenMitPassenderSpezialisierung = mitarbeiterRepository.findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(idee.getProduktideeVertriebswegeWerte(), sparte, idee.getProduktideeZielgruppeWerte());
                break;
        }
        pruefeObEinFachspezialistGefundenWurde(fachspezialistenMitPassenderSpezialisierung, idee);
        Mitarbeiter fachSpezialistMitWenigstenZugewiesenenIdeen = fachspezialistenMitPassenderSpezialisierung.stream().min(Comparator.comparingInt(Mitarbeiter::getAnzahlZugewieseneIdeen)).orElseThrow();
        idee.setFachspezialist(fachSpezialistMitWenigstenZugewiesenenIdeen);
        idee.setBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG);
        ideeRepository.save(idee);
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
