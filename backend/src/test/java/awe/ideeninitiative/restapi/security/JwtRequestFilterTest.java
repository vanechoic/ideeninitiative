package awe.ideeninitiative.restapi.security;

import awe.ideeninitiative.restapi.AbstrakterApiTest;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.MitarbeiterBuilder;
import awe.ideeninitiative.restapi.controller.IdeeController;
import awe.ideeninitiative.restapi.service.IdeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

/**
 * Prüft, dass die in der WebSecurityConfig definierten Filter passen und der JWT bei benötigten Endpunkten korrekt geprüft wird.
 * Beispiel: /idee/meineideen
 */
public class JwtRequestFilterTest extends AbstrakterApiTest {

    static final Logger logger = LoggerFactory.getLogger(JwtRequestFilterTest.class);
    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter sheldon;
    private String jwt, sheldonBenutzername;

    @InjectMocks
    private IdeeController ideeController;

    @MockBean
    private IdeeService ideeServiceMock;
    @Captor
    private ArgumentCaptor<String> ideeServiceArgumentCaptor;

    @Autowired
    private JwtUtil jwtUtil;

    @Before
    public void setup(){
        sheldonBenutzername = "planktonn";
        given.einRegistrierterMitarbeiter();
        given.einGemockterUserDetailsServiceFuerSheldon();
    }

    @Test
    public void alleIdeenAbrufenOhneJwt() throws Exception {
        when.alleIdeenOhneBesondereAuthentifizierungAufgerufenWerden();
        then.werdenAlleIdeenGeladen();
    }

    @Test
    public void meineIdeenOhneAuthentifizierungAufrufen() throws Exception {
        when.meineIdeenOhneBesondereAuthentifizierungAufgerufenWird();
        then.werdenMeineIdeenNichtGeladen();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.FORBIDDEN);
    }

    @Test
    public void meineIdeenMitGueltigemJwtAufrufen() throws Exception {
        given.einGueltigerJwtZumMitarbeiter();
        when.meineIdeenMitDemErstelltenJwtAufgerufenWird();
        then.werdenMeineIdeenGeladen();
    }

    @Test
    public void meineIdeenMitAbgelaufenemJwtAufrufen() throws Exception{
        given.einAbgelaufenerJwtZumMitarbeiter();
        when.meineIdeenMitDemErstelltenJwtAufgerufenWird();
        then.werdenMeineIdeenNichtGeladen();
    }

    private class Given{

        public void einRegistrierterMitarbeiter() {
            sheldon = MitarbeiterBuilder.aMitarbeiter().withBenutzername(sheldonBenutzername)//
                    .withEmail("sheldon.j@plankton.de").withVorname("Sheldon J.")//
                    .withNachname("Plankton").withPasswort("Karen")
                    .build();
        }

        public void einGueltigerJwtZumMitarbeiter() {
            List<String> sheldonsRollen = Arrays.asList(sheldon.ermittleBenutzerrollenAlsString());
            jwt = jwtUtil.generiereEinzelnenToken(sheldonsRollen, sheldon.getBenutzername());
        }

        public void einGemockterUserDetailsServiceFuerSheldon() {
            when(userDetailsService.formeMitarbeiterZuUserDetailsUm(any())).thenCallRealMethod();
            UserDetails userDetails = userDetailsService.formeMitarbeiterZuUserDetailsUm(sheldon);
            when(userDetailsService.loadUserByUsername(sheldonBenutzername)).thenReturn(userDetails);
        }

        public void einAbgelaufenerJwtZumMitarbeiter() {
            List<String> sheldonsRollen = Arrays.asList(sheldon.ermittleBenutzerrollenAlsString());
            jwt = jwtUtil.generiereEinzelnenTokenMitAblaufzeitpunkt(sheldonsRollen, sheldon.getBenutzername(), new Date(System.currentTimeMillis() - 10000));


        }
    }

    private class When{

        public void alleIdeenOhneBesondereAuthentifizierungAufgerufenWerden() throws Exception {
            aufrufergebnis = mockMvc.perform(MockMvcRequestBuilders.get("/idee")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }

        public void meineIdeenOhneBesondereAuthentifizierungAufgerufenWird() throws Exception {
            aufrufergebnis = mockMvc.perform(MockMvcRequestBuilders.get("/idee/meineideen")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }

        public void meineIdeenMitDemErstelltenJwtAufgerufenWird() throws Exception {
            aufrufergebnis = mockMvc.perform(MockMvcRequestBuilders.get("/idee/meineideen")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwt))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }
    }

    private class Then{

        public void werdenAlleIdeenGeladen() {
            verify(ideeServiceMock).alleIdeenAbrufen();
        }

        public void werdenMeineIdeenNichtGeladen() {
            verify(ideeServiceMock, times(0)).meineIdeenAbrufen(ideeServiceArgumentCaptor.capture());
        }

        public void dasAufrufergebnisHatHttpStatusCode(HttpStatus httpStatus) {
            int status = aufrufergebnis.getResponse().getStatus();
            assertEquals(httpStatus.value(), status);
        }

        public void werdenMeineIdeenGeladen() {
            verify(ideeServiceMock).meineIdeenAbrufen(ideeServiceArgumentCaptor.capture());
            assertEquals(sheldon.getBenutzername(), ideeServiceArgumentCaptor.getValue());
        }
    }
}
