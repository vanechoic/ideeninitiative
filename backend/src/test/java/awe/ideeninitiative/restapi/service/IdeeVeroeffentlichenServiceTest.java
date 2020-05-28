package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.model.builder.IdeeBuilder;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.restapi.AbstrakterApiTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class IdeeVeroeffentlichenServiceTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    @InjectMocks
    private IdeeService ideeService;
    @Mock
    private IdeeRepository ideeRepositoryMock;
    @Captor
    private ArgumentCaptor<Idee> ideeZugewiesenArgumentCaptor;
    private Idee idee;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void interneIdeeErfolgreichVeroeffentlicht(){
        given.eineGespeicherteInterneIdee();
       /* when.ideeVeroeffentlichenAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();*/
    }

    @Test
    public void produktideeErfolgreichVeroeffentlicht(){

    }

    @Test
    public void produktideeErfolgreichVeroeffentlichtMehrereVertriebswege(){

    }

    @Test
    public void produktideeErfolgreichVeroeffentlichtBeiMehrerenInfragekommendenSpezialisten(){

    }


    private class Given{

        public void eineGespeicherteInterneIdee() {
            idee = IdeeBuilder.anIdee().withTitel("Titel").withBeschreibung("Beschreibung")
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT).withErfasser(erfasser).withTyp(Ideentyp.INTERNE_IDEE).build();
        }
    }

    private class When{

    }

    private class Then{

    }
}
