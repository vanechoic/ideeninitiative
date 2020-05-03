package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MitarbeiterRepositoryTest {

    @Autowired private MitarbeiterRepository mitarbeiterRepository;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter bob;

    @Test
    public void testCreateAndSaveNewEmployee(){
        given.einNeuerMitarbeiterNamensBob();
        when.derMitarbeiterBobGespeichertWird();
        then.verfuegtBobUeberEineID();
    }

    private class Given{
        public void einNeuerMitarbeiterNamensBob(){
            String username = "BobDerBaumeister";
            String vorname = "Bob";
            String nachname = "Baumeister";
            String email = "bob.der@baumeister.de";
            String passwort = "Passwort im Klartext"; //TODO: MUSS AUF JEDEN FALL NOCH VERSCHLUESSELT WERDEN!!!
            bob = MitarbeiterBuilder.aMitarbeiter().withEmail(email).withBenutzername(username).withVorname(vorname)//
            .withNachname(nachname).withPasswort(passwort).build();
        }
    }

    private class When{

        public void derMitarbeiterBobGespeichertWird() {
            mitarbeiterRepository.save(bob);
        }
    }

    private class Then{

        public void verfuegtBobUeberEineID() {
            assertNotNull(bob.getId());
            assertTrue(bob.getId() > 0);
        }
    }
}