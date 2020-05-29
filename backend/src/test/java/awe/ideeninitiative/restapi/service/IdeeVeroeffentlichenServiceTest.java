package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.IdeeBereitsVeroeffentlichtException;
import awe.ideeninitiative.exception.IdeeExistiertNichtException;
import awe.ideeninitiative.exception.KeinFachspezialistVerfuegbarException;
import awe.ideeninitiative.exception.KeineBefugnisFuerIdeeAenderungenException;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;
    @Captor
    private ArgumentCaptor<Handlungsfeld> interneIdeeHandlungsfeldCaptor;
    private Idee idee;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void interneIdeeErfolgreichVeroeffentlicht() throws KeinFachspezialistVerfuegbarException, KeineBefugnisFuerIdeeAenderungenException, IdeeBereitsVeroeffentlichtException, IdeeExistiertNichtException {
        given.eineGespeicherteInterneIdeeMitHandlungsfeldZUKUNFTSFAEHIGKEIT();
        given.einGemocktesIdeeRepositoryMitEinerIdee();
        given.einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuerHandlungsfeldDerIdee();
        when.ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();
    }

    @Test
    public void produktideeErfolgreichVeroeffentlicht() throws IdeeExistiertNichtException, IdeeBereitsVeroeffentlichtException, KeineBefugnisFuerIdeeAenderungenException, KeinFachspezialistVerfuegbarException {
        given.eineGespeicherteProduktideeMitSparteZusatzinformationZielgruppeUndVertriebsweg();
        given.einGemocktesIdeeRepositoryMitEinerIdee();
       /* given.einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuer();
        when.ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();*/
    }

    @Test
    public void produktideeErfolgreichVeroeffentlichtMehrereVertriebswege(){

    }

    @Test
    public void produktideeErfolgreichVeroeffentlichtBeiMehrerenInfragekommendenSpezialisten(){

    }


    private class Given{
        public void eineGespeicherteInterneIdeeMitHandlungsfeldZUKUNFTSFAEHIGKEIT() {
            idee = IdeeBuilder.anIdee().withTitel("Titel").withBeschreibung("Beschreibung")
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT).withErfasser(erfasser).withTyp(Ideentyp.INTERNE_IDEE).build();
            InterneIdeeHandlungsfeld interneIdeeHandlungsfeld = InterneIdeeHandlungsfeldBuilder.anInterneIdeeHandlungsfeld()
                    .withHandlungsfeld(Handlungsfeld.ZUKUNFTSFAEHIGKEIT).build();
            idee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld);
        }

        public void einGemocktesIdeeRepositoryMitEinerIdee() throws IdeeExistiertNichtException {
            List<Idee> ideen = new ArrayList<>();
            ideen.add(idee);
            when(ideeRepositoryMock.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(any(), any(),  any())).thenReturn(ideen);
        }

        public void einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuerHandlungsfeldDerIdee() {
            List<Mitarbeiter> spezialisten = new ArrayList<>();
            fachspezialist.addFachspezialistHandlungsfeld(idee.getInterneIdeeHandlungsfeld().getHandlungsfeld());
            spezialisten.add(fachspezialist);
            when(mitarbeiterRepositoryMock.findAllByIstFachspezialistTrueAndFachspezialistHandlungsfelderHandlungsfeldLike(any())).thenReturn(spezialisten);
        }

        public void eineGespeicherteProduktideeMitSparteZusatzinformationZielgruppeUndVertriebsweg() {
            idee = IdeeBuilder.anIdee()
                    .withTitel("Produktidee")
                    .withErfasser(erfasser)
                    .withBeschreibung("Eine tolle Beschreibung!")
                    .withTyp(Ideentyp.PRODUKTIDEE)
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT)
                    .build();
            ProduktideeVertriebsweg produktideeVertriebsweg = ProduktideeVertriebswegBuilder.aProduktideeVertriebsweg().withVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG).build();
            idee.setProduktideeVertriebsweg(new ArrayList<ProduktideeVertriebsweg>(
                    Arrays.asList(produktideeVertriebsweg)));
            ProduktideeZielgruppe produktideeZielgruppe = ProduktideeZielgruppeBuilder.aProduktideeZielgruppe().withZielgruppe(Zielgruppe.SINGLES).build();
            idee.setProduktideeZielgruppe(new ArrayList<ProduktideeZielgruppe>(
                    Arrays.asList(produktideeZielgruppe)));
            ProduktideeZusatzinformation produktideeZusatzinformation = ProduktideeZusatzinformationBuilder.aProduktideeZusatzinformation().withExistiertBereits(true)
                    .withArtDerUmsetzung("Produkt siehe...").withUnternehmensbezeichnung("Beispielunternehmen").build();
            idee.setProduktideeZusatzinformation(produktideeZusatzinformation);
            ProduktideeSparte produktideeSparte = ProduktideeSparteBuilder.aProduktideeSparte().withSparte(Sparte.HAFTPFLICHT).build();
            idee.setProduktideeSparte(produktideeSparte);
        }
    }

    private class When{

        public void ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird() throws KeinFachspezialistVerfuegbarException, KeineBefugnisFuerIdeeAenderungenException, IdeeBereitsVeroeffentlichtException, IdeeExistiertNichtException {
            ideeService.ideeVeroeffentlichen(idee.getErfasser().getBenutzername(), idee);
        }
    }

    private class Then{
        public void istDieIdeeDemFachspezialistenZugewiesen() {
            verify(ideeRepositoryMock).save(ideeZugewiesenArgumentCaptor.capture());
            assertNotNull(ideeZugewiesenArgumentCaptor.getValue());
            assertEquals(fachspezialist.getBenutzername(), ideeZugewiesenArgumentCaptor.getValue().getFachspezialist().getBenutzername());
        }

        public void dieIdeeBefindetSichImStatusInBearbeitung() {
            verify(ideeRepositoryMock).save(ideeZugewiesenArgumentCaptor.capture());
            assertNotNull(ideeZugewiesenArgumentCaptor.getValue());
            assertEquals(Ideenstatus.IN_BEARBEITUNG, ideeZugewiesenArgumentCaptor.getValue().getBearbeitungsstatus());
        }
    }
}
