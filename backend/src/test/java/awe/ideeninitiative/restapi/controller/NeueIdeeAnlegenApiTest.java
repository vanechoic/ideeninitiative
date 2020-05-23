package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.model.builder.IdeeDTOBuilder;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.InterneIdeeHandlungsfeld;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.restapi.AbstrakterApiTest;
import awe.ideeninitiative.restapi.service.BenutzerService;
import awe.ideeninitiative.restapi.service.IdeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public class NeueIdeeAnlegenApiTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    @InjectMocks
    private IdeeController ideeController;

    @MockBean
    private IdeeService ideeServiceMock;
    @Captor
    private ArgumentCaptor<Idee> ideeServiceArgumentCaptor;

    private IdeeDTO ideeDTO;
    private Idee idee;
    private InterneIdeeHandlungsfeld interneIdeeHandlungsfeld;

    @Before
    public void setup(){
        super.setup();
        //mitarbeiterRepository.save(erfasser);
        //mitarbeiterRepository.save(fachspezialist);
        when(ideeServiceMock.neueIdeeAnlegen(any())).then(returnsFirstArg());
    }

    @Test
    public void neueInterneIdeeErfolgreichAnlegen() throws Exception {
        given.vollstaendigeEingabenFuerEineInterneIdee();
        when.derIdeeControllerNeueIdeeAnlegenMitDenEingabenAufgerufenWird();
        then.derAufrufWarErfolgreich();
        then.dieIdeeWurdeGespeichert();
        then.zuDerInternenIdeeWurdeEinHandlungsfeldGespeichert();
    }

    private class Given{
        public void vollstaendigeEingabenFuerEineInterneIdee() {
            ideeDTO = IdeeDTOBuilder.anIdeeDTO()//
                    .withTitel("Obst für die Mitarbeiter")//
                    .withBeschreibung("Wöchentliche Lieferung von kostenfreiem Obst, damit die Mitarbeiter mit genug Nährstoffen versorgt werden.")
                    .withFachspezialist(fachspezialist.getBenutzername())
                    .withTyp(Ideentyp.INTERNE_IDEE.toString())
                    .withErfasser(erfasser.getBenutzername())
                    .withBegruendung("Macht Sinn.")
                    .withBearbeitungsstatus(Ideenstatus.AKZEPTIERT.toString())
                    .withHandlungsfeld(Handlungsfeld.ZUKUNFTSFAEHIGKEIT.toString())
                    .withVorteile(Arrays.asList(new String[]{"Kostenloses Essen", "Mitarbeiter leben länger", "Mitarbeiter sind glücklicher"}))
                    .build();
        }

    }

    private class When{

        public void derIdeeControllerNeueIdeeAnlegenMitDenEingabenAufgerufenWird() throws Exception {
            String ideeDTOJson = new ObjectMapper().writeValueAsString(ideeDTO);

            aufrufergebnis = mockMvc.perform(MockMvcRequestBuilders.post("/idee")
                    .with(csrf())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwt))
                    .content(ideeDTOJson)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        }
    }

    private class Then{

        public void derAufrufWarErfolgreich() {
            assertNotNull(aufrufergebnis);
            assertEquals(HttpStatus.OK.value(), aufrufergebnis.getResponse().getStatus());
        }

        public void dieIdeeWurdeGespeichert() {
            verify(ideeServiceMock).neueIdeeAnlegen(ideeServiceArgumentCaptor.capture());
            assertNotNull(ideeServiceArgumentCaptor.getValue());
            //Ausführlicher Test im IdeeMapperTest
            idee = ideeServiceArgumentCaptor.getValue();
        }

        public void zuDerInternenIdeeWurdeEinHandlungsfeldGespeichert() {
            assertNotNull(idee.getInterneIdeeHandlungsfeld());
            assertEquals(ideeDTO.getHandlungsfeld().toUpperCase(), idee.getInterneIdeeHandlungsfeld().getHandlungsfeld().toString());
        }
    }
}
