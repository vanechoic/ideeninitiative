package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.restapi.AbstrakterApiTest;
import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.restapi.service.BenutzerService;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public class BenutzerRegistrierenApiTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    @InjectMocks
    private BenutzerController benutzerController;

    @MockBean
    private BenutzerService benutzerServiceMock;
    @Captor
    private ArgumentCaptor<Mitarbeiter> benutzerServiceArgumentCaptor;

    private BenutzerDTO iroh;
    private String benutzername, email, vorname, nachname, passwort;

    @Before
    public void setup(){
        when(benutzerServiceMock.mitarbeiterRegistrieren(any())).then(returnsFirstArg());
    }

    /**
     * Prüft, dass bei vollständigen Benutzereingaben keine Warnung erzeugt wird.
     */
    @Test
    public void vollstaendigeEingaben() throws Exception {
        given.vollstaendigeBenutzereingaben();
        when.derBenutzerControllerBenutzerRegistrierenAufgerufenWird();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.OK);
        then.derBenutzerServiceMitarbeiterAnlegenWurdeAufgerufen();
    }

    @Test
    public void fehlenderBenutzername() throws Exception {
        given.vollstaendigeBenutzereingabenOhneBenutzername();
        when.derBenutzerControllerBenutzerRegistrierenAufgerufenWird();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void zuKurzerBenutzername() throws Exception {
        given.vollstaendigeBenutzereingabenMitZuKurzemBenutzernamen();
        when.derBenutzerControllerBenutzerRegistrierenAufgerufenWird();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.BAD_REQUEST);
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
            iroh = new BenutzerDTO();
            iroh.setBenutzername(benutzername);
            iroh.setVorname(vorname);
            iroh.setNachname(nachname);
            iroh.setEmail(email);
            iroh.setPasswort(passwort);
        }

        public void vollstaendigeBenutzereingabenOhneBenutzername() {
            vollstaendigeBenutzereingaben();
            iroh.setBenutzername(null);
        }

        public void vollstaendigeBenutzereingabenMitZuKurzemBenutzernamen() {
            vollstaendigeBenutzereingaben();
            iroh.setBenutzername("");
        }
    }

    private class When{
        public void derBenutzerControllerBenutzerRegistrierenAufgerufenWird() throws Exception {
            String irohJson = new ObjectMapper().writeValueAsString(iroh);

            aufrufergebnis = mockMvc.perform(MockMvcRequestBuilders.post("/benutzer")
                    .with(csrf())
                    .content(irohJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }
    }

    private class Then{

        public void derBenutzerServiceMitarbeiterAnlegenWurdeAufgerufen() {
            verify(benutzerServiceMock).mitarbeiterRegistrieren(benutzerServiceArgumentCaptor.capture());
            assertNotNull(benutzerServiceArgumentCaptor.getValue());
            dieMitarbeiterdatenStimmenMitDenBenutzerdatenUberein(benutzerServiceArgumentCaptor.getValue());
        }

        private void dieMitarbeiterdatenStimmenMitDenBenutzerdatenUberein(Mitarbeiter mitarbeiter){
            assertEquals(iroh.getBenutzername(), mitarbeiter.getBenutzername());
            assertEquals(iroh.getEmail(), mitarbeiter.getEmail());
            assertEquals(iroh.getVorname(), mitarbeiter.getVorname());
            assertEquals(iroh.getNachname(), mitarbeiter.getNachname());
            assertEquals(iroh.getPasswort(), mitarbeiter.getPasswort());
        }

        public void dasAufrufergebnisHatHttpStatusCode(HttpStatus httpStatus) {
            int status = aufrufergebnis.getResponse().getStatus();
            assertEquals(httpStatus.value(), status);
        }
    }
}
