package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.FehlendeRolleFachspezialistException;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;

public class ZugewieseneIdeenAbrufenServiceTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    @InjectMocks
    private IdeeService ideeService;
    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test(expected = FehlendeRolleFachspezialistException.class)
    public void benutzerIstKeinFachspezialist() throws FehlendeRolleFachspezialistException {
        given.einGemocktesMitarbeiterRepositoryOhneFachspezialisten();
        when.meineZugewiesenenIdeenAbrufenVomErfasserAufgerufenWird();
    }

    @Test
    public void keineZugewiesenenIdeenVorhanden(){
        //TODO: Impl
    }

    @Test
    public void ladeErfolgreichZweiZugewieseneIdeen(){
        //TODO: Impl
    }

    private class Given{

        public void einGemocktesMitarbeiterRepositoryOhneFachspezialisten() {
            when(mitarbeiterRepositoryMock.findFirstByBenutzernameAndIstFachspezialistTrue(any())).thenReturn(Optional.empty());
        }
    }

    private class When{

        public void meineZugewiesenenIdeenAbrufenVomErfasserAufgerufenWird() throws FehlendeRolleFachspezialistException {
            ideeService.meineZugewiesenenIdeenAbrufen(erfasser.getBenutzername());
        }
    }

    private class Then{

    }
}
