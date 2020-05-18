package awe.ideeninitiative.api.benutzer;

import awe.ideeninitiative.api.model.Benutzer;
import awe.ideeninitiative.controller.BenutzerController;
import awe.ideeninitiative.controller.BenutzerService;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BenutzerAnlegenApiTest {

    @Autowired
    private MockMvc mockMvc;
    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private MvcResult aufrufergebnis;

    @InjectMocks
    private BenutzerController benutzerController;

    @MockBean
    private BenutzerService benutzerServiceMock;
    @Captor
    private ArgumentCaptor<Mitarbeiter> benutzerServiceArgumentCaptor;

    private Benutzer iroh;
    private String benutzername, email, vorname, nachname, passwort;

    @Before
    public void setup(){
        when(benutzerServiceMock.mitarbeiterAnlegen(any())).then(returnsFirstArg());
    }

    /**
     * Prüft, dass bei vollständigen Benutzereingaben keine Warnung erzeugt wird.
     */
    @Test
    public void vollstaendigeEingaben() throws Exception {
        given.vollstaendigeBenutzereingaben();
        when.derBenutzerControllerBenutzerAnlegenAufgerufenWird();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.OK);
        then.derBenutzerServiceMitarbeiterAnlegenWurdeAufgerufen();
    }

    @Test
    public void fehlenderBenutzername() throws Exception {
        given.vollstaendigeBenutzereingabenOhneBenutzername();
        when.derBenutzerControllerBenutzerAnlegenAufgerufenWird();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void zuKurzerBenutzername() throws Exception {
        given.vollstaendigeBenutzereingabenMitZuKurzemBenutzernamen();
        when.derBenutzerControllerBenutzerAnlegenAufgerufenWird();
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
            iroh = new Benutzer();
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
        public void derBenutzerControllerBenutzerAnlegenAufgerufenWird() throws Exception {
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
            verify(benutzerServiceMock).mitarbeiterAnlegen(benutzerServiceArgumentCaptor.capture());
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
