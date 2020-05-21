package awe.ideeninitiative.security;

import awe.ideeninitiative.controller.IdeeController;
import awe.ideeninitiative.controller.IdeeService;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.MitarbeiterBuilder;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.query.JpaQueryCreator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

/**
 * Prüft, dass die in der WebSecurityConfig definierten Filter passen und der JWT bei benötigten Endpunkten korrekt geprüft wird.
 * Beispiel: /idee/meineideen
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {

    static final Logger logger = LoggerFactory.getLogger(JwtRequestFilterTest.class);
    @Autowired
    private MockMvc mockMvc;
    private MvcResult aufrufergebnis;
    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter sheldon;
    private String jwt;

    @InjectMocks
    private IdeeController ideeController;

    @MockBean
    private IdeeService ideeServiceMock;
    @Captor
    private ArgumentCaptor<String> ideeServiceArgumentCaptor;

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void alleIdeenAbrufenOhneJwt() throws Exception {
        when.alleIdeenOhneBesondereAuthentifizierungAufgerufenWerden();
        then.werdenAlleIdeenGeladen();
    }

    @Test
    public void meineIdeenOhneAuthentifizierungAufrufen() throws Exception {
        given.einRegistrierterMitarbeiter();
        when.meineIdeenOhneBesondereAuthentifizierungAufgerufenWird();
        then.werdenMeineIdeenNichtGeladen();
        then.dasAufrufergebnisHatHttpStatusCode(HttpStatus.FORBIDDEN);
    }

    @Test
    public void meineIdeenMitGueltigemJwtAufrufen() throws Exception {
        given.einRegistrierterMitarbeiter();
        given.einGueltigerJwtZumMitarbeiter();
        when.meineIdeenMitEinemGueltigenJwtAufgerufenWird();
        then.werdenMeineIdeenGeladen();
    }

    private class Given{

        public void einRegistrierterMitarbeiter() {
            sheldon = MitarbeiterBuilder.aMitarbeiter().withBenutzername("planktonn")//
                    .withEmail("sheldon.j@plankton.de").withVorname("Sheldon J.")//
                    .withNachname("Plankton").withPasswort("Karen")
                    .build();
            sheldon = mitarbeiterRepository.save(sheldon);
        }

        public void einGueltigerJwtZumMitarbeiter() {
            List<String> sheldonsRollen = Arrays.asList(sheldon.ermittleBenutzerrollenAlsString());
            jwt = jwtUtil.generiereEinzelnenToken(sheldonsRollen, sheldon.getBenutzername());
            logger.error(jwt);
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

        public void meineIdeenMitEinemGueltigenJwtAufgerufenWird() throws Exception {
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
