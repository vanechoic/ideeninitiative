package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.*;
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

    private Mitarbeiter fachspezialist2, fachspezialist3;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    /**
     * Testet ob eine interne Idee veröffentlicht werden kann und ein entschprechender Spezialist existiert.
     * @throws KeinFachspezialistVerfuegbarException
     * @throws KeineBefugnisFuerIdeeAenderungenException
     * @throws IdeeBereitsVeroeffentlichtException
     * @throws IdeeExistiertNichtException
     * @author njuergens
     */
    @Test
    public void interneIdeeErfolgreichVeroeffentlicht() throws KeinFachspezialistVerfuegbarException, KeineBefugnisFuerIdeeAenderungenException, IdeeBereitsVeroeffentlichtException, IdeeExistiertNichtException {
        given.eineGespeicherteInterneIdeeMitHandlungsfeldZUKUNFTSFAEHIGKEIT();
        given.einGemocktesIdeeRepositoryMitEinerIdee();
        given.einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuerHandlungsfeldDerIdee();
        when.ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();
    }

    /**
     * Testet ob eine Produkt-Idee veröffentlicht werden kann und ein entschprechender Spezialist existiert.
     * @throws IdeeExistiertNichtException
     * @throws IdeeBereitsVeroeffentlichtException
     * @throws KeineBefugnisFuerIdeeAenderungenException
     * @throws KeinFachspezialistVerfuegbarException
     * @author njuergens
     */
    @Test
    public void produktideeErfolgreichVeroeffentlicht() throws IdeeExistiertNichtException, IdeeBereitsVeroeffentlichtException, KeineBefugnisFuerIdeeAenderungenException, KeinFachspezialistVerfuegbarException {
        given.eineGespeicherteProduktideeMitSparteZusatzinformationZielgruppeUndVertriebsweg();
        given.einGemocktesIdeeRepositoryMitEinerIdee();
        given.einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuerDieProduktideeVorgaben();
        when.ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();
    }

    /**
     * Testet ob eine Produkt-Idee nur einem Fachspezialisten zugeordnet wird auch wenn mehrere potenzielle Fachspezialisten existieren
     * @throws ApiException
     * @author njuergens
     */
    @Test
    public void produktideeErfolgreichVeroeffentlichtBeiMehrerenInfragekommendenSpezialisten() throws ApiException {
        given.eineGespeicherteProduktideeMitSparteZusatzinformationZielgruppeUndVertriebsweg();
        given.einGemocktesIdeeRepositoryMitEinerIdee();
        given.einGemocktesMitarbeiterRepoMitDreiMoeglichenFachspezialistenFuerDieProduktideeVorgaben();
        when.ideeVeroeffentlichenVomErfasserDerIdeeAufgerufenWird();
        then.istDieIdeeDemFachspezialistenZugewiesen();
        then.dieIdeeBefindetSichImStatusInBearbeitung();
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

        public void einGemocktesMitarbeiterRepoMitEinemFachspezialistenFuerDieProduktideeVorgaben() {
            List<Mitarbeiter> spezialisten = new ArrayList<>();
            einFachSpezialistMitVertriebswegDIREKTVERSICHERUNGZielgruppeSINGLESUndSparteHAFTPFLICHT();
            spezialisten.add(fachspezialist);
            when(mitarbeiterRepositoryMock.findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(any(), any(), any())).thenReturn(spezialisten);
        }

        private void einFachSpezialistMitVertriebswegDIREKTVERSICHERUNGZielgruppeSINGLESUndSparteHAFTPFLICHT(){
            fachspezialist.addFachspezialistVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG);
            fachspezialist.addFachspezialistZielgruppe(Zielgruppe.SINGLES);
            fachspezialist.addFachspezialistSparte(Sparte.HAFTPFLICHT);
        }

        private void einZweiterFachSpezialistMitEinerZugewiesenenIdee(){
            fachspezialist2 = MitarbeiterBuilder.aMitarbeiter().withIstFachspezialist(true)
                    .withVorname("Spezi").withBenutzername("spezi2").withNachname("Alist").withPasswort("ichkanns")
                    .withEmail("fach2@spezi.a").build();
            fachspezialist2.addFachspezialistVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG, Vertriebskanal.KOOPERATION_MIT_KREDITINSTITUTEN);
            fachspezialist2.addFachspezialistZielgruppe(Zielgruppe.SINGLES, Zielgruppe.GEWERBETREIBENDE);
            fachspezialist2.addFachspezialistSparte(Sparte.HAFTPFLICHT, Sparte.HAUSRAT);
            Idee zugewieseneIdee = IdeeBuilder.anIdee().withTyp(Ideentyp.INTERNE_IDEE).withFachspezialist(fachspezialist2).withTitel("Zugewiesene Idee").withBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG).withBeschreibung("Toll").withErfasser(erfasser).build();
            fachspezialist2.setZugewieseneIdeen(Arrays.asList(zugewieseneIdee));
        }

        private void einDritterFachspezialistMitZweiZugewiesenenIdeen(){
            fachspezialist3 = MitarbeiterBuilder.aMitarbeiter().withIstFachspezialist(true)
                    .withVorname("Spezi").withBenutzername("spezi3").withNachname("Alist").withPasswort("ichkanns")
                    .withEmail("fach3@spezi.a").build();
            fachspezialist3.addFachspezialistVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG, Vertriebskanal.STATIONAERER_VERTRIEB);
            fachspezialist3.addFachspezialistZielgruppe(Zielgruppe.SINGLES, Zielgruppe.KINDER_JUGENDLICHE);
            fachspezialist3.addFachspezialistSparte(Sparte.HAFTPFLICHT, Sparte.KFZ);
            Idee zugewieseneIdee = IdeeBuilder.anIdee().withTyp(Ideentyp.INTERNE_IDEE).withFachspezialist(fachspezialist3).withTitel("Zugewiesene Idee1").withBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG).withBeschreibung("Toll").withErfasser(erfasser).build();
            Idee zugewieseneIdee2 = IdeeBuilder.anIdee().withTyp(Ideentyp.INTERNE_IDEE).withFachspezialist(fachspezialist3).withTitel("Zugewiesene Idee2").withBearbeitungsstatus(Ideenstatus.IN_BEARBEITUNG).withBeschreibung("Toll").withErfasser(erfasser).build();
            fachspezialist3.setZugewieseneIdeen(Arrays.asList(zugewieseneIdee, zugewieseneIdee2));
        }

        public void einGemocktesMitarbeiterRepoMitDreiMoeglichenFachspezialistenFuerDieProduktideeVorgaben() {
            List<Mitarbeiter> spezialisten = new ArrayList<>();
            einFachSpezialistMitVertriebswegDIREKTVERSICHERUNGZielgruppeSINGLESUndSparteHAFTPFLICHT();
            spezialisten.add(fachspezialist);
            einZweiterFachSpezialistMitEinerZugewiesenenIdee();
            spezialisten.add(fachspezialist2);
            einDritterFachspezialistMitZweiZugewiesenenIdeen();
            spezialisten.add(fachspezialist3);
            when(mitarbeiterRepositoryMock.findDistinctByIstFachspezialistTrueAndFachspezialistVertriebswegeVertriebswegInOrFachspezialistSpartenSparteInOrFachspezialistZielgruppenZielgruppeIn(any(), any(), any())).thenReturn(spezialisten);
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
