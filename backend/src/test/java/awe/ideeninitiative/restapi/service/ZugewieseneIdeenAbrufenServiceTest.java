package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.FehlendeRolleFachspezialistException;
import awe.ideeninitiative.model.builder.IdeeBuilder;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.AbstrakterApiTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

public class ZugewieseneIdeenAbrufenServiceTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private List<Idee> zugewieseneIdeen;

    @InjectMocks
    private IdeeService ideeService;
    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;
    @Mock
    private IdeeRepository ideeRepositoryMock;

    @Before
    public void setup() throws Exception {
        super.setup();
        zugewieseneIdeen = new ArrayList<Idee>();
    }

    /**
     * Testet ob Fehler geworfen wird wenn ein nicht Spezialist eine Idee zugewiesen haben möchte.
     * @throws FehlendeRolleFachspezialistException
     * @author njuergens
     */
    @Test(expected = FehlendeRolleFachspezialistException.class)
    public void benutzerIstKeinFachspezialist() throws FehlendeRolleFachspezialistException {
        given.einGemocktesMitarbeiterRepositoryOhneFachspezialisten();
        when.meineZugewiesenenIdeenAbrufenVomErfasserAufgerufenWird();
    }

    /**
     * Prüft ob eine leere Liste übergeben wird wenn keine Ideen zugewiesen wurden
     * @throws FehlendeRolleFachspezialistException
     * @author njuergens
     */
    @Test
    public void keineZugewiesenenIdeenVorhanden() throws FehlendeRolleFachspezialistException {
        given.einGemocktesMitarbeiterRepositoryMitFachspezialisten();
        given.einGemocktesIdeeRepositoryMitZugewiesenenIdeenDesFachspezialisten();
        when.meineZugewiesenenIdeenAbrufenVomFachspezialistenAufgerufenWird();
        then.esWurdenKeineZugewiesenenIdeenZurueckgegeben();
    }

    /**
     * Testet ob ein Fachspezialist mehrere Ideen zugewiesen bekommen kann.
     * @throws FehlendeRolleFachspezialistException
     * @author njuergens
     */
    @Test
    public void ladeErfolgreichZweiZugewieseneIdeen() throws FehlendeRolleFachspezialistException {
        given.einFachspezialistMitZweiZugewiesenenIdeen();
        given.einGemocktesMitarbeiterRepositoryMitFachspezialisten();
        given.einGemocktesIdeeRepositoryMitZugewiesenenIdeenDesFachspezialisten();
        when.meineZugewiesenenIdeenAbrufenVomFachspezialistenAufgerufenWird();
        then.esWurdenZweiZugewiesenenIdeenZurueckgegeben();
    }

    private class Given{

        public void einGemocktesMitarbeiterRepositoryOhneFachspezialisten() {
            when(mitarbeiterRepositoryMock.findFirstByBenutzernameAndIstFachspezialistTrue(any())).thenReturn(Optional.empty());
        }

        public void einFachspezialistMitZweiZugewiesenenIdeen() {
            Idee interneIdee = IdeeBuilder.anIdee().withTyp(Ideentyp.INTERNE_IDEE).withErfasser(erfasser)
                    .withTitel("Interne Idee").withBeschreibung("Beschreibung intern").withBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG)
                    .withFachspezialist(fachspezialist).build();
            Idee produktIdee = IdeeBuilder.anIdee().withTyp(Ideentyp.PRODUKTIDEE).withErfasser(erfasser)
                    .withTitel("Produktive Idee").withBeschreibung("Beschreibung produktiv").withBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG)
                    .withFachspezialist(fachspezialist).build();
            List<Idee> ideenInBearbeitung = Arrays.asList(interneIdee, produktIdee);
            fachspezialist.setZugewieseneIdeen(ideenInBearbeitung);
        }

        public void einGemocktesIdeeRepositoryMitZugewiesenenIdeenDesFachspezialisten() {
            when(ideeRepositoryMock.findAllByFachspezialistBenutzernameAndBearbeitungsstatusLike(fachspezialist.getBenutzername(), Ideenstatus.IN_BEARBEITUNG)).thenReturn(fachspezialist.getZugewieseneIdeen());
        }

        public void einGemocktesMitarbeiterRepositoryMitFachspezialisten() {
            when(mitarbeiterRepositoryMock.findFirstByBenutzernameAndIstFachspezialistTrue(any())).thenReturn(Optional.of(fachspezialist));
        }
    }

    private class When{

        public void meineZugewiesenenIdeenAbrufenVomErfasserAufgerufenWird() throws FehlendeRolleFachspezialistException {
            ideeService.meineZugewiesenenIdeenAbrufen(erfasser.getBenutzername());
        }

        public void meineZugewiesenenIdeenAbrufenVomFachspezialistenAufgerufenWird() throws FehlendeRolleFachspezialistException {
            zugewieseneIdeen = ideeService.meineZugewiesenenIdeenAbrufen(fachspezialist.getBenutzername());
        }
    }

    private class Then{

        public void esWurdenKeineZugewiesenenIdeenZurueckgegeben() {
            assertTrue(zugewieseneIdeen == null || zugewieseneIdeen.isEmpty());
        }

        public void esWurdenZweiZugewiesenenIdeenZurueckgegeben() {
            assertNotNull(zugewieseneIdeen);
            assertFalse(zugewieseneIdeen.isEmpty());
            assertEquals(2, zugewieseneIdeen.size());
        }
    }
}
