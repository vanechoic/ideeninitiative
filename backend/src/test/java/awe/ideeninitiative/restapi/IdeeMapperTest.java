package awe.ideeninitiative.restapi;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.ApiException;
import awe.ideeninitiative.exception.InterneIdeeOhneHandlungsfeldException;
import awe.ideeninitiative.exception.MaximaleAnzahlVorteileUeberschrittenException;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import awe.ideeninitiative.util.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

//TODO: WARUM FUNKTIONIERT DAS HIER NICHT?! @ExtendWith(MockitoExtension.class) Stattdessen unten: initMocks
public class IdeeMapperTest {

    private Given given = new Given();
    private When when = new When();
    private Then then = new Then();
    private IdeeDTO ideeDTO;
    private Idee idee;
    private InterneIdeeHandlungsfeld interneIdeeHandlungsfeld;
    private ProduktideeZusatzinformation produktideeZusatzinformation;
    private List<ProduktideeZielgruppe> produktideeZielgruppen;
    private List<ProduktideeVertriebsweg> produktideeVertriebswege;
    private List<Vorteil> vorteile;
    private ProduktideeSparte produktideeSparte;
    private Mitarbeiter erfasser, fachspezialist;

    @InjectMocks
    private IdeeMapper ideeMapper;

    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ideeDTO = null;
        idee = null;
        given.einMitarbeiterAlsErfasser();
        given.einMitarbeiterAlsFachspezialist();
        given.einGemocktesMitarbeiterRepository();
    }

    @Test
    public void mappeIdeeDTOZuInternerIdee() throws ApiException {
        given.vollstaendigeEingabenFuerEineInterneIdee();
        when.derMapperIdeeDTOZuIdeeAufgerufenWird();
        then.wirdEineIdeeMitDenWertenDerInternenIdeeZurueckgegeben();
    }

    @Test
    public void mappeIdeeDTOZuProduktidee() throws ApiException {
        given.vollstaendigeEingabenFuerEineProduktidee();
        when.derMapperIdeeDTOZuIdeeAufgerufenWird();
        then.wirdEineIdeeMitDenWertenDerProduktideeZurueckgegeben();
    }

    @Test
    public void mappeIdeeDTOOhneTyp() throws ApiException {
        given.eingabenOhneIdeentyp();
        when.derMapperIdeeDTOZuIdeeAufgerufenWird();
        then.wirdKeineIdeeZurueckGegeben();
    }
    
    @Test
    public void mappeInterneIdeeZuIdeeDTO() throws MaximaleAnzahlVorteileUeberschrittenException {
        given.eineInterneIdeeMitHandlungsfeld();
        when.derMapperIdeeZuIdeeDTOAufgerufenWird();
        then.wirdEinIdeeDTOMitDenWertenDerInternenIdeeZurueckgegeben();
        then.enthaeltDasIdeeDTOEinHandlungsfeld();
        then.enthaeltDasIdeeDTODreiVorteile();
    }

    @Test
    public void mappeProduktdeeZuIdeeDTO(){
        given.eineBereitsExistierendeProduktideeMitSparteVertriebswegenUndZielgruppen();
        when.derMapperIdeeZuIdeeDTOAufgerufenWird();
        then.wirdEinIdeeDTOMitDenWertenDerProduktideeZurueckgegeben();
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

        public void einMitarbeiterAlsErfasser() {
            erfasser = MitarbeiterBuilder.aMitarbeiter()
                    .withBenutzername("dieBoeseKoenigin")
                    .withVorname("Böse")
                    .withNachname("Königin")
                    .withPasswort("IchBinDieSchönsteImGanzenLand")
                    .withEmail("die.schoenste@koenigin.com")
                    .build();
        }

        public void einMitarbeiterAlsFachspezialist() {
            fachspezialist = MitarbeiterBuilder.aMitarbeiter()
                    .withBenutzername("maleficent")
                    .withVorname("Mal")
                    .withNachname("Eficent")
                    .withEmail("mal.eficent@evil.com")
                    .withPasswort("IamSoEvil")
                    .withIstFachspezialist(true)
                    .build();
        }

        public void einGemocktesMitarbeiterRepository() {
            when(mitarbeiterRepositoryMock.findFirstByBenutzername(erfasser.getBenutzername())).thenReturn(Optional.ofNullable(erfasser));
            when(mitarbeiterRepositoryMock.findFirstByBenutzername(fachspezialist.getBenutzername())).thenReturn(Optional.ofNullable(fachspezialist));
        }

        public void vollstaendigeEingabenFuerEineProduktidee() {
            ideeDTO = IdeeDTOBuilder.anIdeeDTO()//
                    .withTitel("Videospiel mit niedlichen Tieren, die in einer Stadt wohnen")//
                    .withBeschreibung("Eine Ablenkung von dem realen Leben.")
                    .withFachspezialist(fachspezialist.getBenutzername())
                    .withTyp(Ideentyp.PRODUKTIDEE.toString())
                    .withErfasser(erfasser.getBenutzername())
                    .withBegruendung("Macht keinen Sinn.")
                    .withBearbeitungsstatus(Ideenstatus.ABGELEHNT.toString())
                    .withVorteile(Arrays.asList(new String[]{"Ablenkung", "Mitarbeiter leben länger", "Mitarbeiter sind glücklicher"}))
                    .withArtDerUmsetzung("Animal Crossing")
                    .withExistiertBereits(true)
                    .withUnternehmensbezeichnung("Nintendo")
                    .withSparten(Sparte.HAUSRAT.toString())
                    .withVertriebsweg(Arrays.asList(new String[]{Vertriebskanal.STATIONAERER_VERTRIEB.toString(), Vertriebskanal.VERSICHERUNGSMAKLER.toString()}))
                    .withZielgruppe(Arrays.asList(new String[]{Zielgruppe.KINDER_JUGENDLICHE.toString(), Zielgruppe.FAMILIEN.toString()}))
                    .build();
        }

        public void eingabenOhneIdeentyp() {
            vollstaendigeEingabenFuerEineInterneIdee();
            ideeDTO.setTyp(null);
        }

        public void eineInterneIdeeMitHandlungsfeld() throws MaximaleAnzahlVorteileUeberschrittenException {
            idee = IdeeBuilder.anIdee()
                    .withTitel("Eine Katze pro Mitarbeiter")
                    .withBeschreibung("Alternativ ein Katzenplüschtier bei Tierhaarallergie oder ein Fisch bei Phobien.")
                    .withTyp(Ideentyp.INTERNE_IDEE)
                    .withErfasser(erfasser)
                    .withFachspezialist(fachspezialist)
                    .withBegruendung("Es wurde an alles gedacht!")
                    .withBearbeitungsstatus(Ideenstatus.AKZEPTIERT)
                    .build();
            idee.addVorteil("Vorteil1");
            idee.addVorteil("Vorteil2");
            idee.addVorteil("Vorteil3");
            interneIdeeHandlungsfeld = InterneIdeeHandlungsfeldBuilder.anInterneIdeeHandlungsfeld()
                    .withHandlungsfeld(Handlungsfeld.ZUKUNFTSFAEHIGKEIT).withIdee(idee).build();
            idee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld); //TODO: Brauchen wir hier einen Setter?
        }

        public void eineBereitsExistierendeProduktideeMitSparteVertriebswegenUndZielgruppen() {
            idee = IdeeBuilder.anIdee()
                    .withTitel("Teleportationsmaschine")
                    .withBeschreibung("Für den schnellen Abflug nach dem Feierabend.")
                    .withTyp(Ideentyp.PRODUKTIDEE)
                    .withErfasser(erfasser)
                    .withFachspezialist(fachspezialist)
                    .withBegruendung("Unmöglich...")
                    .withBearbeitungsstatus(Ideenstatus.ABGELEHNT)
                    .build();
            eineSparteZurProduktidee();
            zusatzinformationZurProduktidee();
            zweiZielgruppenZurProduktidee();
            zweiVertriebswegeZurProduktidee();

        }

        private void eineSparteZurProduktidee() {
            produktideeSparte = ProduktideeSparteBuilder.aProduktideeSparte().withSparte(Sparte.UNFALL).build();
            idee.setProduktideeSparte(produktideeSparte);
        }

        private void zusatzinformationZurProduktidee() {
            produktideeZusatzinformation = ProduktideeZusatzinformationBuilder.aProduktideeZusatzinformation()
                    .withExistiertBereits(true)
                    .withUnternehmensbezeichnung("BBC")
                    .withArtDerUmsetzung("Tardis")
                    .build();
            idee.setProduktideeZusatzinformation(produktideeZusatzinformation);
        }

        private void zweiVertriebswegeZurProduktidee() {
            produktideeVertriebswege = new ArrayList<>();
            ProduktideeVertriebsweg produktideeVertriebsweg1 = ProduktideeVertriebswegBuilder.aProduktideeVertriebsweg().withIdee(idee).withVertriebsweg(Vertriebskanal.DIREKTVERSICHERUNG).build();
            produktideeVertriebswege.add(produktideeVertriebsweg1);
            ProduktideeVertriebsweg produktideeVertriebsweg2 = ProduktideeVertriebswegBuilder.aProduktideeVertriebsweg().withIdee(idee).withVertriebsweg(Vertriebskanal.KOOPERATION_MIT_KREDITINSTITUTEN).build();
            produktideeVertriebswege.add(produktideeVertriebsweg2);
            idee.setProduktideeVertriebsweg(produktideeVertriebswege);
        }

        private void zweiZielgruppenZurProduktidee() {
            produktideeZielgruppen = new ArrayList<>();
            ProduktideeZielgruppe produktideeZielgruppe1 = ProduktideeZielgruppeBuilder.aProduktideeZielgruppe().withIdee(idee).withZielgruppe(Zielgruppe.SINGLES).build();
            produktideeZielgruppen.add(produktideeZielgruppe1);
            ProduktideeZielgruppe produktideeZielgruppe2 = ProduktideeZielgruppeBuilder.aProduktideeZielgruppe().withIdee(idee).withZielgruppe(Zielgruppe.FAMILIEN).build();
            produktideeZielgruppen.add(produktideeZielgruppe2);
            idee.setProduktideeZielgruppe(produktideeZielgruppen);
        }
    }

    private class When{

        public void derMapperIdeeDTOZuIdeeAufgerufenWird() throws ApiException {
            idee = ideeMapper.mappeIdeeDTOZuIdee(ideeDTO);
        }

        public void derMapperIdeeZuIdeeDTOAufgerufenWird() {
            ideeDTO = ideeMapper.mappeIdeeZuIdeeDTO(idee);
        }
    }

    private class Then{

        public void wirdEineIdeeMitDenWertenDerInternenIdeeZurueckgegeben() {
            dieAllgemeinenWerteDerIdeeStimmenUeberein();
            assertEquals(ideeDTO.getHandlungsfeld(), idee.getInterneIdeeHandlungsfeld().getHandlungsfeld().toString());

        }

        public void wirdEineIdeeMitDenWertenDerProduktideeZurueckgegeben() {
            dieAllgemeinenWerteDerIdeeStimmenUeberein();
            assertEquals(ideeDTO.getSparten(), idee.getProduktideeSparte().getSparte().toString());
            dieProduktideeVertriebswegeStimmenUeberein();
            dieProduktideeZielgruppenStimmenUeberein();
        }

        private void dieProduktideeZielgruppenStimmenUeberein() {
            assertNotNull(idee);
            assertNotNull(ideeDTO);
            assertEquals(ideeDTO.getZielgruppe().size(), idee.getProduktideeZielgruppe().size());
            for (String zielgruppe: ideeDTO.getZielgruppe()) {
                assertTrue(idee.getProduktideeZielgruppe().stream().anyMatch(vw -> vw.getZielgruppe().toString().equals(zielgruppe)));
            }
        }

        private void dieProduktideeVertriebswegeStimmenUeberein() {
            assertNotNull(idee);
            assertNotNull(ideeDTO);
            assertEquals(ideeDTO.getVertriebsweg().size(), idee.getProduktideeVertriebsweg().size());
            for (String vertriebsweg: ideeDTO.getVertriebsweg()) {
                assertTrue(idee.getProduktideeVertriebsweg().stream().anyMatch(vw -> vw.getVertriebsweg().toString().equals(vertriebsweg)));
            }
        }

        private void dieAllgemeinenWerteDerIdeeStimmenUeberein(){
            assertNotNull(idee);
            assertEquals(ideeDTO.getTitel(), idee.getTitel());
            assertEquals(ideeDTO.getBeschreibung(), idee.getBeschreibung());
            assertEquals(ideeDTO.getBearbeitungsstatus(), idee.getBearbeitungsstatus().toString());
            assertEquals(ideeDTO.getBegruendung(), idee.getBegruendung());
            assertEquals(ideeDTO.getErfasser(), idee.getErfasser().getBenutzername());
            assertEquals(ideeDTO.getFachspezialist(), idee.getFachspezialist().getBenutzername());
            assertEquals(ideeDTO.getTyp(), idee.getTyp().toString());
            assertEquals(ideeDTO.getErstellzeitpunkt(), DatumUtil.formeDatumZuStringUm(idee.getErstellzeitpunkt()));
        }

        public void wirdKeineIdeeZurueckGegeben() {
            assertNull(idee);
        }

        public void wirdEinIdeeDTOMitDenWertenDerInternenIdeeZurueckgegeben() {
            assertNotNull(ideeDTO);
            dieAllgemeinenWerteDerIdeeStimmenUeberein();
        }

        public void enthaeltDasIdeeDTOEinHandlungsfeld() {
            assertNotNull(idee.getInterneIdeeHandlungsfeld());
            assertNotNull(ideeDTO.getHandlungsfeld());
            assertEquals(idee.getInterneIdeeHandlungsfeld().getHandlungsfeld().toString(), ideeDTO.getHandlungsfeld().toUpperCase());

        }

        public void wirdEinIdeeDTOMitDenWertenDerProduktideeZurueckgegeben() {
            assertNotNull(ideeDTO);
            dieAllgemeinenWerteDerIdeeStimmenUeberein();
            assertEquals(produktideeSparte.getSparte().toString(), ideeDTO.getSparten());
            dieProduktideeVertriebswegeStimmenUeberein();
            dieProduktideeZielgruppenStimmenUeberein();
            dieProduktideeZusatzinformationenStimmenUeberein();
        }

        private void dieProduktideeZusatzinformationenStimmenUeberein() {
            assertNotNull(idee);
            assertNotNull(idee.getProduktideeZusatzinformation());
            assertNotNull(ideeDTO);
            assertEquals(ideeDTO.getExistiertBereits(), idee.getProduktideeZusatzinformation().isExistiertBereits());
            assertEquals(ideeDTO.getArtDerUmsetzung(), idee.getProduktideeZusatzinformation().getArtDerUmsetzung());
            assertEquals(ideeDTO.getUnternehmensbezeichnung(), idee.getProduktideeZusatzinformation().getUnternehmensbezeichnung());
        }

        public void enthaeltDasIdeeDTODreiVorteile() {
            assertNotNull(ideeDTO.getVorteile());
            assertEquals(3, ideeDTO.getVorteile().size());
            for (String vorteil: ideeDTO.getVorteile()) {
                assertTrue(idee.getVorteile().stream().anyMatch(v -> v.getBeschreibung().equals(vorteil)));
            }
        }
    }
}
