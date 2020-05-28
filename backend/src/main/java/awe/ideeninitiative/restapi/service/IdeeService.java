package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.IdeeBereitsVeroeffentlichtException;
import awe.ideeninitiative.exception.IdeeExistiertNichtException;
import awe.ideeninitiative.exception.KeinFachspezialistVerfuegbarException;
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

    public void ideeLoeschen(String titel, String erfasser, String erstelldatum) throws IdeeExistiertNichtException {
        logger.error("titel: "+titel);
        logger.error("erfasser: "+erfasser);
        logger.error("erstelldatum: "+erstelldatum);
        LocalDateTime erstelldatumDate = DatumUtil.formeStringZuDatumUm(erstelldatum);
        List<Idee> zutreffendeIdeen = ideeRepository.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(titel, erstelldatumDate, erfasser);
        pruefeObZuLoeschendeIdeeExistiert(zutreffendeIdeen, titel, erfasser);
        logger.info(String.format("Die Idee %s von Benutzer %s mit Erstelldatum %s wurde gelöscht.", titel, erfasser, erstelldatum));
        ideeRepository.delete(zutreffendeIdeen.get(0));
    }

    private void pruefeObZuLoeschendeIdeeExistiert(List<Idee> zutreffendeIdeen, String titel, String erfasser) throws IdeeExistiertNichtException{
        if(zutreffendeIdeen == null || zutreffendeIdeen.isEmpty()){
            throw new IdeeExistiertNichtException(titel, erfasser, 0);
        }
        if(zutreffendeIdeen.size() > 1){
            throw new IdeeExistiertNichtException(titel, erfasser, zutreffendeIdeen.size());
        }
    }


    public void ideeBearbeiten(Idee idee) throws IdeeExistiertNichtException {
        //Idee suchen
        List<Idee> zutreffendeIdeen = ideeRepository.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(idee.getTitel(), idee.getErstellzeitpunkt(), idee.getErfasser().getBenutzername());
        pruefeObZuLoeschendeIdeeExistiert(zutreffendeIdeen, idee.getTitel(), idee.getErfasser().getBenutzername());
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
