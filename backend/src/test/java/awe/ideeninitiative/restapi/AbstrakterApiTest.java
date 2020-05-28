package awe.ideeninitiative.restapi;

import awe.ideeninitiative.exception.MitarbeiterExistiertBereitsException;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.model.repositories.InterneIdeeHandlungsfeldRepository;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.security.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public abstract class AbstrakterApiTest {

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected UserDetailsServiceImpl userDetailsService;


    @Autowired
    private JwtUtil jwtUtil;
    protected String jwt;

    protected MvcResult aufrufergebnis;
    protected Mitarbeiter erfasser, fachspezialist;

    @Autowired
    protected IdeeRepository ideeRepository;

    @Autowired
    protected InterneIdeeHandlungsfeldRepository interneIdeeHandlungsfeldRepository;

    @Autowired
    protected MitarbeiterRepository mitarbeiterRepository;

    @Before
    public void setup() throws Exception {
        einRegistrierterMitarbeiterAlsErfasser();
        einRegistrierterMitarbeiterAlsFachspezialist();
        einGemockterUserDetailsService();
        einGueltigerJwtZumMitarbeiter(erfasser);
    }

    private void einRegistrierterMitarbeiterAlsErfasser() {
        erfasser = MitarbeiterBuilder.aMitarbeiter().withBenutzername("plankton")//
                .withEmail("sheldon.j@plankton.de").withVorname("Sheldon J.")//
                .withNachname("Plankton").withPasswort("Karen")
                .build();
    }

    private void einRegistrierterMitarbeiterAlsFachspezialist() {
        fachspezialist = MitarbeiterBuilder.aMitarbeiter()
                .withBenutzername("maleficent")
                .withVorname("Mal")
                .withNachname("Eficent")
                .withEmail("mal.eficent@evil.com")
                .withPasswort("IamSoEvil")
                .withIstFachspezialist(true)
                .build();
    }

    protected void einGueltigerJwtZumMitarbeiter(Mitarbeiter mitarbeiter) {
        List<String> registrierterMitarbeiterRollen = Arrays.asList(mitarbeiter.ermittleBenutzerrollenAlsString());
        jwt = jwtUtil.generiereEinzelnenToken(registrierterMitarbeiterRollen, mitarbeiter.getBenutzername());
    }

    private void einGemockterUserDetailsService() {
        when(userDetailsService.formeMitarbeiterZuUserDetailsUm(any())).thenCallRealMethod();
        UserDetails userDetailsErfasser = userDetailsService.formeMitarbeiterZuUserDetailsUm(erfasser);
        when(userDetailsService.loadUserByUsername(erfasser.getBenutzername())).thenReturn(userDetailsErfasser);
        UserDetails userDetailsFachspezialist = userDetailsService.formeMitarbeiterZuUserDetailsUm(fachspezialist);
        when(userDetailsService.loadUserByUsername(fachspezialist.getBenutzername())).thenReturn(userDetailsFachspezialist);
    }
}
