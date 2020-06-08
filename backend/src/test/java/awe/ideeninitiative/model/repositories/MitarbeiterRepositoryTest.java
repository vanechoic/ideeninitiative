package awe.ideeninitiative.model.repositories;

import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prüft die Funktionalität einzelner JPA-Methoden auf Korrektheit und spricht die Datenbank direkt an.
 * @author Vanessa Haubrok
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MitarbeiterRepositoryTest {

    @Autowired private MitarbeiterRepository mitarbeiterRepository;

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter bob, fachspezialist, fachspezialist2;
    private List<Mitarbeiter> gefundeneFachspezialisten;

    @Before
    public void setup(){
        gefundeneFachspezialisten = new ArrayList<>();
        bob = null;
        given.einFachspezialistMitHandlungsfeldernVertriebswegenSpartenUndZielgruppen();
    }

    @After
    public void cleanup(){
        given.keineMitarbeiterInDerDB();
    }

    /**
     * Prüft, dass ein normaler registrierter Mitarbeiter in der Datenbank abgelegt wird.
     */
    @Test
    public void mitarbeiterSpeichern(){
        given.einNeuerMitarbeiterNamensBob();
        when.derMitarbeiterBobGespeichertWird();
        then.verfuegtBobUeberEineID();
    }

    /**
     * Es liegt ein Fachspezialist mit dem Handlungsfeld Ertragssteigerung vor. Es wird geprüft, ob die Abfrage für die Ermittlung eines Fachspezialisten mit entsprechendem
     * Handlungsfeld den korrekten Fachspezialisten ermittelt.
     */
    @Test
    public void fachspezialistMitHandlungsfeldSuchen(){
        when.nachEinemFachspezialistenMitEinemBestimmtenHandlungsfeldGesuchtWird(Handlungsfeld.ERTRAGSSTEIGERUNG);
        then.esWurdeDerFachspezialistGefunden();
    }

    /**
     * Es liegt ein FS mit Vertriebsweg Versicherungsmakler mit Sparten KFZ und Haftpflicht vor. Es wird nach einem FS mit Vertriebsweg Versicherungsmakler und einer anderen Sparte gesucht.
     * Der FS mit zutreffendem Vertriebsweg wird gefunden.
     */
    @Test
    public void fachspezialistenMitVertriebswegVersicherungsmaklerSuchen(){
        when.nachEinemFachspezialistenGesuchtWird(Arrays.asList(Vertriebskanal.VERSICHERUNGSMAKLER), Arrays.asList(Sparte.HAUSRAT), new ArrayList<>());
        then.esWurdeDerFachspezialistGefunden();
    }

    /**
     * Es liegt ein FS mit Vertriebsweg Versicherungsmakler mit Sparten KFZ und Zielgruppe Singles vor. Es wird nach einem FS mit genau diesen Werten gesucht.
     * Der FS mit zutreffendem Vertriebsweg wird gefunden.
     */
    @Test
    public void fachspezialistenMitVertriebswegVersicherungsmaklerUndSparteKFZUndZielgruppeSinglesSuchen(){
        when.nachEinemFachspezialistenGesuchtWird(Arrays.asList(Vertriebskanal.VERSICHERUNGSMAKLER), Arrays.asList(Sparte.KFZ), Arrays.asList(Zielgruppe.SINGLES));
        then.esWurdeDerFachspezialistGefunden();
    }

    /**
     * Es liegen zwei FS mit der Zielgruppe Familien und sonst abweichenden Spezialisierungen vor. Es wird nach einem FS mit der Zielgruppe Familien gesucht.
     * Beide zutreffenden FS werden gefunden.
     */
    @Test
    public void zweiFachspezialistenMitZielgruppeFamilienSuchen(){
        given.einZweiterFachspezialistMitAnderenSpezialisierungen();
        when.nachEinemFachspezialistenGesuchtWird(new ArrayList<>(), Arrays.asList(Sparte.values()), Arrays.asList(Zielgruppe.FAMILIEN));
        then.esWurdenBeideFachspezialistenGefunden();
    }

    private class Given{
        public void einNeuerMitarbeiterNamensBob(){
            String username = "BobDerBaumeister";
            String vorname = "Bob";
            String nachname = "Baumeister";
            String email = "bob.der@baumeister.de";
            String passwort = "Passwort";
            bob = MitarbeiterBuilder.aMitarbeiter().withEmail(email).withBenutzername(username).withVorname(vorname)//
            .withNachname(nachname).withPasswort(passwort).build();
        }

        public void einFachspezialistMitHandlungsfeldernVertriebswegenSpartenUndZielgruppen() {
            Handlungsfeld[] spezialist1Handlungsfelder  = {Handlungsfeld.ERTRAGSSTEIGERUNG, Handlungsfeld.KOSTENSENKUNG};
            Vertriebskanal[] spezialist1Vertriebskanale  = {Vertriebskanal.VERSICHERUNGSMAKLER, Vertriebskanal.STATIONAERER_VERTRIEB};
            Sparte[] spezialist1Sparten  = {Sparte.KFZ,  Sparte.HAFTPFLICHT};
            Zielgruppe[] spezialist1Zielgruppen = {Zielgruppe.SINGLES, Zielgruppe.FAMILIEN};
            fachspezialist = MitarbeiterBuilder.aMitarbeiter()
                    .withBenutzername("test_maleficent")
                    .withVorname("Mal")
                    .withNachname("Eficent")
                    .withEmail("mal.eficent@evil.com")
                    .withPasswort("IamSoEvil")
                    .withIstFachspezialist(true)
                    .build();
            fachspezialist.addFachspezialistHandlungsfeld(spezialist1Handlungsfelder);
            fachspezialist.addFachspezialistVertriebsweg(spezialist1Vertriebskanale);
            fachspezialist.addFachspezialistZielgruppe(spezialist1Zielgruppen);
            fachspezialist.addFachspezialistSparte(spezialist1Sparten);
            mitarbeiterRepository.save(fachspezialist);
        }

        public void keineMitarbeiterInDerDB() {
            if(bob != null){
                mitarbeiterRepository.delete(bob);
            }
            if(fachspezialist != null){
                mitarbeiterRepository.delete(fachspezialist);
            }
            if(fachspezialist2 != null){
                mitarbeiterRepository.delete(fachspezialist2);
            }
        }


        public void einZweiterFachspezialistMitAnderenSpezialisierungen() {
            Handlungsfeld[] spezialist2Handlungsfelder  = {Handlungsfeld.ZUKUNFTSFAEHIGKEIT};
            Vertriebskanal[] spezialist2Vertriebskanale  = {Vertriebskanal.KOOPERATION_MIT_KREDITINSTITUTEN};
            Sparte[] spezialist2Sparten  = {Sparte.LEBENSVERSICHERUNG,  Sparte.KRANKENVERSICHERUNG};
            Zielgruppe[] spezialist2Zielgruppen = {Zielgruppe.GEWERBETREIBENDE, Zielgruppe.FAMILIEN};
            fachspezialist2 = MitarbeiterBuilder.aMitarbeiter()
                    .withBenutzername("test_maleficent2")
                    .withVorname("Mal")
                    .withNachname("Eficent")
                    .withEmail("mal2.eficent@evil.com")
                    .withPasswort("IamSoEvil")
                    .withIstFachspezialist(true)
                    .build();
            fachspezialist2.addFachspezialistHandlungsfeld(spezialist2Handlungsfelder);
            fachspezialist2.addFachspezialistVertriebsweg(spezialist2Vertriebskanale);
            fachspezialist2.addFachspezialistZielgruppe(spezialist2Zielgruppen);
            fachspezialist2.addFachspezialistSparte(spezialist2Sparten);
            mitarbeiterRepository.save(fachspezialist2);
        }
    }

    private class When{

        public void derMitarbeiterBobGespeichertWird() {
            mitarbeiterRepository.save(bob);
        }

        public void nachEinemFachspezialistenMitEinemBestimmtenHandlungsfeldGesuchtWird(Handlungsfeld handlungsfeld) {
            gefundeneFachspezialisten = mitarbeiterRepository.findAllByIstFachspezialistTrueAndFachspezialistHandlungsfelderHandlungsfeldLike(Handlungsfeld.ERTRAGSSTEIGERUNG);
        }

        public void nachEinemFachspezialistenGesuchtWird(List<Vertriebskanal> vertriebswege, List<Sparte> sparte, List<Zielgruppe> zielgruppen){
            gefundeneFachspezialisten = mitarbeiterRepository.findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(vertriebswege, sparte, zielgruppen);

        }
    }

    private class Then{

        public void verfuegtBobUeberEineID() {
            assertNotNull(bob.getId());
            assertTrue(bob.getId() > 0);
        }

        public void esWurdeDerFachspezialistGefunden() {
            assertNotNull(gefundeneFachspezialisten);
            assertFalse(gefundeneFachspezialisten.isEmpty());
            assertEquals(1, gefundeneFachspezialisten.size());
            assertEquals(fachspezialist.getBenutzername(), gefundeneFachspezialisten.get(0).getBenutzername());
        }

        public void esWurdenBeideFachspezialistenGefunden() {
            assertNotNull(gefundeneFachspezialisten);
            assertFalse(gefundeneFachspezialisten.isEmpty());
            assertEquals(2, gefundeneFachspezialisten.size());
            assertTrue(gefundeneFachspezialisten.stream().anyMatch(m -> m.getBenutzername().equals(fachspezialist.getBenutzername())));
            assertTrue(gefundeneFachspezialisten.stream().anyMatch(m -> m.getBenutzername().equals(fachspezialist2.getBenutzername())));
        }
    }
}