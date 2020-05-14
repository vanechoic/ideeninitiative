package awe.ideeninitiative.api.benutzer;

import awe.ideeninitiative.api.model.Benutzer;
import awe.ideeninitiative.api.model.BenutzerBuilder;
import awe.ideeninitiative.controller.BenutzerController;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class BenutzerAnlegenTest {

    static final Logger logger = LoggerFactory.getLogger(BenutzerAnlegenTest.class);

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private BenutzerController benutzerController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Benutzer iroh;
    private String benutzername, email, vorname, nachname, passwort;
    private ResponseEntity<String> aufrufergebnis;

    @Test
    public void benutzerErfolgreichAnlegen(){
        given.vollstaendigeBenutzereingaben();
        when.derBenutzerControllerBenutzerAnlegenAufgerufenWird();
        then.liegtDerBenutzerInDerDatenbankVor();
    }

    @Test
    public void fehlenderBenutzername(){
        given.vollstaendigeBenutzereingabenOhneBenutzernamen();
        when.derBenutzerControllerBenutzerAnlegenAufgerufenWird();
        then.esWirdEinApiFehlerZurueckgegeben();
    }

    private class Given{

        public Given(){
            benutzername = "Iroh";
            email = "general.iroh@feuernation.de";
            vorname = "Iroh";
            nachname = "nicht bekannt";
            passwort = "Jasmindrache";
        }

        public void vollstaendigeBenutzereingaben() {
            iroh = BenutzerBuilder.aBenutzer().withBenutzername(benutzername)//
                .withEmail(email).withVorname(vorname).withNachname(nachname).withPasswort(passwort).build();
        }

        public void vollstaendigeBenutzereingabenOhneBenutzernamen() {
            vollstaendigeBenutzereingaben();
            iroh.setBenutzername(null);
        }
    }

    private class When{

        public void derBenutzerControllerBenutzerAnlegenAufgerufenWird() {
            aufrufergebnis = benutzerController.benutzerAnlegen(iroh);
        }
    }

    private class Then{

        public void liegtDerBenutzerInDerDatenbankVor() {
            List<Mitarbeiter> angelegteMitarbeiter = mitarbeiterRepository.findAllByBenutzername(benutzername);
            assertNotNull(angelegteMitarbeiter);
            assertEquals(1, angelegteMitarbeiter.size());
            Mitarbeiter angelegterMitarbeiter = angelegteMitarbeiter.get(0);
            dieGespeichertenWerteStimmenMitDerEingabeUeberein(angelegterMitarbeiter);
        }

        private void dieGespeichertenWerteStimmenMitDerEingabeUeberein(Mitarbeiter angelegterMitarbeiter) {
            assertEquals(benutzername, angelegterMitarbeiter.getBenutzername());
            assertEquals(email, angelegterMitarbeiter.getEmail());
            assertEquals(vorname, angelegterMitarbeiter.getVorname());
            assertEquals(nachname, angelegterMitarbeiter.getNachname());
            assertTrue(bCryptPasswordEncoder.matches(passwort,angelegterMitarbeiter.getPasswort()));
        }

        public void esWirdEinApiFehlerZurueckgegeben() {
            assertNotNull(aufrufergebnis);
            assertEquals(400, aufrufergebnis.getStatusCodeValue());
        }
    }
}
