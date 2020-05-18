package awe.ideeninitiative.security;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.MitarbeiterBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.Assert.*;

@SpringBootTest
public class UserDetailsServiceImplUnitTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter tomRiddle;
    private String[] rollen;

    @Test
    public void ermittleBenutzerrollenAlsStringMitAllenRollen(){
        given.einMitarbeiterMitAllenRollen();
        when.ermittleBenutzerrollenAlsStringAufgerufenWird();
        then.derRollenArrayBeinhaltetDreiRollen();
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.MITARBEITER);
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.FACHSPEZIALIST);
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.ADMIN);
    }

    @Test
    public void ermittleBenutzerrollenAlsStringNurMitarbeiterrolle(){
        given.einMitarbeiterMitNurDerMitarbeiterrolle();
        when.ermittleBenutzerrollenAlsStringAufgerufenWird();
        then.derRollenArrayBeinhaltetNurEineRolle();
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.MITARBEITER);
    }

    private class Given{

        public void einMitarbeiterMitAllenRollen() {
            tomRiddle = MitarbeiterBuilder.aMitarbeiter().withBenutzername("voldemort").withEmail("volde@mort.de")//
            .withVorname("Tom Marvolous").withNachname("Riddle").withIstFachspezialist(true).withIstAdmin(true).build();
        }

        public void einMitarbeiterMitNurDerMitarbeiterrolle() {
            tomRiddle = MitarbeiterBuilder.aMitarbeiter().withBenutzername("voldemort").withEmail("volde@mort.de")//
                    .withVorname("Tom Marvolous").withNachname("Riddle").build();
        }
    }

    private class When {
        public void ermittleBenutzerrollenAlsStringAufgerufenWird() {
            rollen = userDetailsServiceImpl.ermittleBenutzerrollenAlsString(tomRiddle);
        }
    }

    private class Then {
        public void derRollenArrayBeinhaltetDieRolle(BenutzerRollen rolle) {
            assertTrue(Arrays.asList(rollen).contains(rolle.toString()));
        }

        public void derRollenArrayBeinhaltetNurEineRolle() {
            assertNotNull(rollen);
            assertEquals(1, rollen.length);
        }

        public void derRollenArrayBeinhaltetDreiRollen() {
            assertNotNull(rollen);
            assertEquals(3, rollen.length);
        }
    }
}
