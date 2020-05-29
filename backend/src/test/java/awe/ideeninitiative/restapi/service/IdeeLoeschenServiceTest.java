package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.IdeeExistiertNichtException;
import awe.ideeninitiative.model.builder.IdeeBuilder;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IdeeLoeschenServiceTest{

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private Mitarbeiter erfasser;
    private Idee idee;
    private List<Idee> ideen;

    @InjectMocks
    private IdeeService ideeService;

    @Mock
    private IdeeRepository ideeRepositoryMock;
    @Captor
    private ArgumentCaptor<Idee> ideeLoeschenArgumentCaptor;

    @Before
    public void setup(){
        idee = null;
        ideen = null;
        erfasser = null;
    }

    @Test
    public void ideeErfolgreichLoeschen() throws IdeeExistiertNichtException {
        given.einRegistrierterMitarbeiterAlsErfasser();
        given.eineGespeicherteInterneIdee();
        given.einGemocktesRepositoryMitEinerIdee();
        when.ideeLoeschenMitDenWertenDerIdeeAufgerufenWird();
        then.dieIdeeWurdeGeloescht();
    }

    @Test(expected = IdeeExistiertNichtException.class)
    public void ideeExistiertNicht() throws IdeeExistiertNichtException {
        given.einGemocktesRepositoryOhneIdee();
        when.ideeLoeschenMitNichtZutreffendenWertenAufgerufenWird();
    }

    private class Given{
        private void einGemocktesRepositoryMitEinerIdee(){
            List<Idee> ideen = new ArrayList<>();
            ideen.add(idee);
            when(ideeRepositoryMock.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(any(), any(), any())).thenReturn(ideen);
        }

        private void einGemocktesRepositoryOhneIdee(){
            when(ideeRepositoryMock.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(any(), any(), any())).thenReturn(null);
        }

        public void einRegistrierterMitarbeiterAlsErfasser() {
            erfasser = MitarbeiterBuilder.aMitarbeiter().withBenutzername("bennutzerr").withVorname("Ben").withNachname("Benutzer").withPasswort("Passwort").withEmail("b@benutzer.de").build();

        }

        public void eineGespeicherteInterneIdee() {
            idee = IdeeBuilder.anIdee().withTitel("Titel").withBeschreibung("Beschreibung")
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT).withErfasser(erfasser).withTyp(Ideentyp.INTERNE_IDEE).build();
        }
    }

    private class When{

        public void ideeLoeschenMitDenWertenDerIdeeAufgerufenWird() throws IdeeExistiertNichtException {
            ideeService.ideeLoeschen(idee.getTitel(), idee.getErfasser().getBenutzername(), DatumUtil.formeDatumZuStringUm(idee.getErstellzeitpunkt()));
        }

        public void ideeLoeschenMitNichtZutreffendenWertenAufgerufenWird() throws IdeeExistiertNichtException {
            ideeService.ideeLoeschen("Falscher Titel", "nichtexistent", DatumUtil.formeDatumZuStringUm(LocalDateTime.now()));
        }
    }

    private class Then{

        public void dieIdeeWurdeGeloescht() {
            verify(ideeRepositoryMock).delete(ideeLoeschenArgumentCaptor.capture());
            assertNotNull(ideeLoeschenArgumentCaptor.getValue());
            assertEquals(idee.getTitel(), ideeLoeschenArgumentCaptor.getValue().getTitel());
            assertEquals(idee.getErfasser().getBenutzername(), ideeLoeschenArgumentCaptor.getValue().getErfasser().getBenutzername());
            assertEquals(idee.getErstellzeitpunkt(), ideeLoeschenArgumentCaptor.getValue().getErstellzeitpunkt());
        }
    }

}
