package awe.ideeninitiative.model;

import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.restapi.security.BenutzerRollen;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Testet die Umformungsmethode im Mitarbeiter darauf, dass sie die Benutzerrollen korrekt als eine String-Liste zurückgibt.
 * @author //TODO
 */
@SpringBootTest
public class MitarbeiterBenutzerrollenUnitTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter tomRiddle;
    private String[] rollen;

    /**
     * Gegeben ist ein Mitarbeiter mit allen drei zutreffenden Rollen FS, Admin und Mitarbeiter. Als Ergebnis geliefert wird
     * eine Liste mit drei String-Einträgen. Zu jeder Rolle gibt es einen Eintrag.
     * @author //TODO
     */
    @Test
    public void ermittleBenutzerrollenAlsStringMitAllenRollen(){
        given.einMitarbeiterMitAllenRollen();
        when.ermittleBenutzerrollenAlsStringAufgerufenWird();
        then.derRollenArrayBeinhaltetDreiRollen();
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.MITARBEITER);
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.FACHSPEZIALIST);
        then.derRollenArrayBeinhaltetDieRolle(BenutzerRollen.ADMIN);
    }

    /**
     * Gegeben ist ein Mitarbeiter mit nur einer Rolle Mitarbeiter. Als Ergebnis geliefert wird
     * eine Liste mit einem String-Eintrag "Mitarbeiter".
     * @author //TODO
     */
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
            rollen = tomRiddle.ermittleBenutzerrollenAlsString();
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
