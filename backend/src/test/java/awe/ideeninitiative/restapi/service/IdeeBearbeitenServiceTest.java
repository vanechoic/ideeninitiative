package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.ApiException;
import awe.ideeninitiative.exception.IdeeExistiertNichtException;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.repositories.IdeeRepository;
import awe.ideeninitiative.restapi.AbstrakterApiTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IdeeBearbeitenServiceTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();

    @InjectMocks
    private IdeeService ideeService;
    @Mock
    private IdeeRepository ideeRepositoryMock;
    @Captor
    private ArgumentCaptor<Idee> ideeSpeichernArgumentCaptor;
    private Idee zuAktualisierendeIdee, benutzerEingabenIdee;

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    /**
     * Testet ob die Bearbeitung einer internen Idee korrekt funktioniert
     * @throws ApiException
     * @author njuergens
     */
    @Test
    public void aktualisiereInterneIdee() throws ApiException {
        given.eineGespeicherteInterneIdee();
        given.benutzereingabenZurBearbeitungDerInternenIdee();
        given.einGemocktesIdeeRepository();
        when.ideeBearbeitenMitDerIdeeAufgerufenWird();
        then.dieIdeeWurdeEntsprechendDerEingabenAktualisiert();
    }

    /**
     * Testet ob die Bearbeitung einer  Produkt-Idee korrekt funktioniert
     * @throws ApiException
     * @author njuergens
     */
    @Test
    public void aktualisiereProduktidee() throws ApiException {
        given.eineGespeicherteProduktidee();
        given.benutzereingabenZurBearbeitungDerProduktdee();
        given.einGemocktesIdeeRepository();
        when.ideeBearbeitenMitDerIdeeAufgerufenWird();
        then.dieIdeeWurdeEntsprechendDerEingabenAktualisiert();
    }

    /**
     * Testet ob eine interne Idee in eine Produktidee umgewandelt werden kann
     * @throws ApiException
     * @author njuergens
     */
    @Test
    public void formeInterneZuProduktideeUm() throws ApiException {
        given.eineGespeicherteInterneIdee();
        given.benutzereingabenZurBearbeitungDerProduktdee();
        given.einGemocktesIdeeRepository();
        when.ideeBearbeitenMitDerIdeeAufgerufenWird();
        then.dieIdeeWurdeEntsprechendDerEingabenAktualisiert();
    }

    /**
     * Testet das Bearbeiten nur möglich ist, wenn Ideen existieren
     * @throws ApiException
     * @author njuergens
     */
    @Test(expected = IdeeExistiertNichtException.class)
    public void ideeExistiertNicht() throws ApiException {
        given.benutzereingabenZurBearbeitungEinerNichtExistentenIdee();
        when.ideeBearbeitenMitDerIdeeAufgerufenWird();

    }

    private class Given{

        public void eineGespeicherteInterneIdee() {
            zuAktualisierendeIdee = IdeeBuilder.anIdee().withTitel("Interne Idee")
                    .withBeschreibung("Eine ganz tolle interne Idee!")
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT)
                    .withErfasser(erfasser)
                    .withTyp(Ideentyp.INTERNE_IDEE)
                    .withErstellzeitpunkt(LocalDateTime.now())
                    .build();
        }

        public void benutzereingabenZurBearbeitungDerInternenIdee() {
            benutzerEingabenIdee = IdeeBuilder.anIdee()
                    .withTitel(zuAktualisierendeIdee.getTitel())
                    .withErfasser(zuAktualisierendeIdee.getErfasser())
                    .withErstellzeitpunkt(zuAktualisierendeIdee.getErstellzeitpunkt())
                    .withBeschreibung("Eine bearbeitete Beschreibung!")
                    .withTyp(Ideentyp.INTERNE_IDEE)
                    .withBearbeitungsstatus(Ideenstatus.AKZEPTIERT)
                    .withBegruendung("Eine super tolle Idee!")
                    .withFachspezialist(fachspezialist)
                    .build();
            InterneIdeeHandlungsfeld interneIdeeHandlungsfeld = InterneIdeeHandlungsfeldBuilder.anInterneIdeeHandlungsfeld()
                    .withHandlungsfeld(Handlungsfeld.ZUKUNFTSFAEHIGKEIT).build();
            benutzerEingabenIdee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld);
        }

        public void einGemocktesIdeeRepository() {
            when(ideeRepositoryMock.findAllByTitelAndErstellzeitpunktAndErfasserBenutzername(zuAktualisierendeIdee.getTitel(), zuAktualisierendeIdee.getErstellzeitpunkt(), zuAktualisierendeIdee.getErfasser().getBenutzername())).thenReturn(new ArrayList<Idee>(
                    Arrays.asList(zuAktualisierendeIdee)));
        }

        public void eineGespeicherteProduktidee() {
            zuAktualisierendeIdee = IdeeBuilder.anIdee().withTitel("Produktidee")
                    .withBeschreibung("Eine ganz tolle Produktidee!")
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT)
                    .withErfasser(erfasser)
                    .withTyp(Ideentyp.PRODUKTIDEE)
                    .build();
        }

        public void benutzereingabenZurBearbeitungDerProduktdee() {
            benutzerEingabenIdee = IdeeBuilder.anIdee()
                    .withTitel(zuAktualisierendeIdee.getTitel())
                    .withErfasser(zuAktualisierendeIdee.getErfasser())
                    .withErstellzeitpunkt(zuAktualisierendeIdee.getErstellzeitpunkt())
                    .withBeschreibung("Eine bearbeitete Beschreibung!")
                    .withTyp(Ideentyp.PRODUKTIDEE)
                    .withBearbeitungsstatus(Ideenstatus.ABGELEHNT)
                    .withBegruendung("Eine super schlechte Idee!")
                    .withFachspezialist(fachspezialist)
                    .build();
            ProduktideeVertriebsweg produktideeVertriebsweg = ProduktideeVertriebswegBuilder.aProduktideeVertriebsweg().withVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG).build();
            benutzerEingabenIdee.setProduktideeVertriebsweg(new ArrayList<ProduktideeVertriebsweg>(
                    Arrays.asList(produktideeVertriebsweg)));
            ProduktideeZielgruppe produktideeZielgruppe = ProduktideeZielgruppeBuilder.aProduktideeZielgruppe().withZielgruppe(Zielgruppe.SINGLES).build();
            benutzerEingabenIdee.setProduktideeZielgruppe(new ArrayList<ProduktideeZielgruppe>(
                    Arrays.asList(produktideeZielgruppe)));
            ProduktideeZusatzinformation produktideeZusatzinformation = ProduktideeZusatzinformationBuilder.aProduktideeZusatzinformation().withExistiertBereits(true)
                    .withArtDerUmsetzung("Produkt siehe...").withUnternehmensbezeichnung("Beispielunternehmen").build();
            benutzerEingabenIdee.setProduktideeZusatzinformation(produktideeZusatzinformation);
            ProduktideeSparte produktideeSparte = ProduktideeSparteBuilder.aProduktideeSparte().withSparte(Sparte.HAFTPFLICHT).build();
            benutzerEingabenIdee.setProduktideeSparte(produktideeSparte);
        }

        public void benutzereingabenZurBearbeitungEinerNichtExistentenIdee() {
            benutzerEingabenIdee = IdeeBuilder.anIdee()
                    .withTitel("Ich existiere noch nicht!")
                    .withErfasser(erfasser)
                    .withBeschreibung("Eine bearbeitete Beschreibung!")
                    .withTyp(Ideentyp.INTERNE_IDEE)
                    .withBearbeitungsstatus(Ideenstatus.ANGELEGT)
                    .build();
        }
    }

    private class When{

        public void ideeBearbeitenMitDerIdeeAufgerufenWird() throws ApiException {
            ideeService.ideeBearbeiten(benutzerEingabenIdee);
        }
    }

    private  class Then{

        public void dieIdeeWurdeEntsprechendDerEingabenAktualisiert() {
            verify(ideeRepositoryMock).save(ideeSpeichernArgumentCaptor.capture());
            assertNotNull(ideeSpeichernArgumentCaptor.getValue());
            Idee gespeicherteIdee = ideeSpeichernArgumentCaptor.getValue();
            assertEquals(benutzerEingabenIdee.getTitel(), gespeicherteIdee.getTitel());
            assertEquals(benutzerEingabenIdee.getBeschreibung(), gespeicherteIdee.getBeschreibung());
            assertEquals(benutzerEingabenIdee.getErstellzeitpunkt(), gespeicherteIdee.getErstellzeitpunkt());
            assertEquals(benutzerEingabenIdee.getErfasser(), gespeicherteIdee.getErfasser());
            assertEquals(benutzerEingabenIdee.getInterneIdeeHandlungsfeld(), gespeicherteIdee.getInterneIdeeHandlungsfeld());
            assertEquals(benutzerEingabenIdee.getProduktideeZielgruppe(), gespeicherteIdee.getProduktideeZielgruppe());
            assertEquals(benutzerEingabenIdee.getProduktideeVertriebsweg(), gespeicherteIdee.getProduktideeVertriebsweg());
            assertEquals(benutzerEingabenIdee.getProduktideeSparte(), gespeicherteIdee.getProduktideeSparte());
            assertEquals(benutzerEingabenIdee.getProduktideeZusatzinformation(), gespeicherteIdee.getProduktideeZusatzinformation());
            assertEquals(benutzerEingabenIdee.getBearbeitungsstatus(), gespeicherteIdee.getBearbeitungsstatus());
            assertEquals(benutzerEingabenIdee.getBegruendung(), gespeicherteIdee.getBegruendung());
            assertEquals(benutzerEingabenIdee.getFachspezialist(), gespeicherteIdee.getFachspezialist());
            assertEquals(benutzerEingabenIdee.getVorteile(), gespeicherteIdee.getVorteile());
            assertEquals(zuAktualisierendeIdee.getErstellzeitpunkt(), gespeicherteIdee.getErstellzeitpunkt());
        }
    }
}
