package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.FehlendeRolleAdminException;
import awe.ideeninitiative.exception.NachrichtBereitsVorhandenException;
import awe.ideeninitiative.exception.NachrichtExistiertNichtException;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.model.repositories.SystemnachrichtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @author Jakob
 * Serviceklasse für die Systemnachrichten -
 * Enthält die Businesslogik für das Anlegen, Laden und Löschen
 * von Systemnachrichten
 */
@Service
public class SystemnachrichtService {

    @Autowired
    private SystemnachrichtRepository systemnachrichtRepository;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public void systemnachrichtAnlegen(Nachricht nachricht) throws NachrichtBereitsVorhandenException {
        List<Nachricht> nachrichten = systemnachrichtRepository.findAllByTitel(nachricht.getTitel());
        if (nachrichten != null && !nachrichten.isEmpty())
        {
            throw new NachrichtBereitsVorhandenException(nachricht);
        }
        systemnachrichtRepository.save(nachricht);
    }

    public List<Nachricht> alleSystemnachrichtenLaden(String benutzername) throws FehlendeRolleAdminException {
        pruefeDassDerBenutzerEinAdminIst(benutzername);
        return systemnachrichtRepository.findAll();
    }

    /**
     * Prüfen ob ein Nutzer der Admin ist,
     * um die Systemnachricht ggf. anzuzeigen
     * @param benutzername
     * @throws FehlendeRolleAdminException
     */
    private void pruefeDassDerBenutzerEinAdminIst(String benutzername) throws FehlendeRolleAdminException {
        mitarbeiterRepository.findFirstByBenutzernameAndIstAdminTrue(benutzername).orElseThrow(() -> new FehlendeRolleAdminException(benutzername));
    }

    public void systemnachrichtLoeschen(String benutzername, Nachricht gemappteNachricht) throws FehlendeRolleAdminException, NachrichtExistiertNichtException {
        pruefeDassDerBenutzerEinAdminIst(benutzername);
        Nachricht nachrichtAusDB = systemnachrichtRepository.findFirstByTitel(gemappteNachricht.getTitel()).orElseThrow(() -> new NachrichtExistiertNichtException(gemappteNachricht));
        systemnachrichtRepository.delete(nachrichtAusDB);
    }
}
