package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.NachrichtBereitsVorhandenException;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.model.repositories.SystemnachrichtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemnachrichtService {

    @Autowired
    private SystemnachrichtRepository systemnachrichtRepository;

    public void systemnachrichtAnlegen(Nachricht nachricht) throws NachrichtBereitsVorhandenException {
        List<Nachricht> nachrichten = systemnachrichtRepository.findAllByTitel(nachricht.getTitel());
        if (nachrichten != null && !nachrichten.isEmpty())
        {
            throw new NachrichtBereitsVorhandenException(nachricht);
        }
        systemnachrichtRepository.save(nachricht);
    }
}
