package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.IdeeExistiertNichtException;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.repositories.IdeeRepository;
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
import java.util.Date;
import java.util.List;

@Service
public class IdeeService {

    static final Logger logger = LoggerFactory.getLogger(IdeeService.class);

    @Autowired
    private IdeeRepository ideeRepository;

    public List<Idee> meineIdeenAbrufen(String benutzername) {
        logger.warn("IdeeService.meineIdeenAbrufen()");
        List<Idee> ideenZuErfasser = ideeRepository.findAllByErfasserBenutzername(benutzername);
        return ideenZuErfasser;
    }

    public List<Idee> alleIdeenAbrufen() {
        return ideeRepository.findAll();
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


    public void ideeBearbeiten(IdeeDTO ideeDTO, Idee idee) throws IdeeExistiertNichtException {
        //Idee suchen
        List<Idee> zutreffendeIdeen = ideeRepository.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(ideeDTO.getTitel(), DatumUtil.formeStringZuDatumUm(ideeDTO.getErstellzeitpunkt()), ideeDTO.getErfasser());
        pruefeObZuLoeschendeIdeeExistiert(zutreffendeIdeen, ideeDTO.getTitel(), ideeDTO.getErfasser());
        //Idee aktualisieren
        Idee zuAktualisierendeIdee = zutreffendeIdeen.get(0);
        //TODO: Idee aktualisiern okay, aber die IDs müssen noch an Handlungsfeld, Sparte und Co. gesetzt werden
        //ideeRepository.save(idee);
    }
}
