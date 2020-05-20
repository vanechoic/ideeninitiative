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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    @Autowired
    private MockMvc mockMvc;
    private MvcResult aufrufergebnis;
    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter sheldon;

    @InjectMocks
    private IdeeController ideeController;

    @MockBean
    private IdeeService ideeServiceMock;
    @Captor
    private ArgumentCaptor<String> ideeServiceArgumentCaptor;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Test
    public void alleIdeenAbrufenOhneJwt() throws Exception {
        when.alleIdeenOhneBesondereAuthentifizierungAufgerufenWerden();
        then.werdenAlleIdeenGeladen();
    }

    @Test
    public void meineIdeenOhneAuthentifizierungAufrufen() throws Exception {
        given.einRegistrierterMitarbeiter();
        when.meineIdeenOhneBesondereAuthentifizierungAufgerufenWird();
        then.werdenKeineIdeenGeladen();
    }

    private class Given{

        public void einRegistrierterMitarbeiter() {
            sheldon = MitarbeiterBuilder.aMitarbeiter().withBenutzername("planktonn")//
                    .withEmail("sheldon.j@plankton.de").withVorname("Sheldon J.")//
                    .withNachname("Plankton").withPasswort("Karen")
                    .build();
            sheldon = mitarbeiterRepository.save(sheldon);
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
    }

    private class Then{

        public void werdenAlleIdeenGeladen() {
            verify(ideeServiceMock).alleIdeenAbrufen();
        }

        public void werdenKeineIdeenGeladen() {
            verify(ideeServiceMock, times(0)).meineIdeenAbrufen(ideeServiceArgumentCaptor.capture());
        }
    }
}
