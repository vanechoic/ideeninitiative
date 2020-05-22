package awe.ideeninitiative.restapi;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.model.builder.IdeeDTOBuilder;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.MitarbeiterBuilder;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
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
    private Mitarbeiter erfasser, fachspezialist;

    @InjectMocks
    private IdeeMapper ideeMapper;

    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        given.einMitarbeiterAlsErfasser();
        given.einMitarbeiterAlsFachspezialist();
        given.einGemocktesMitarbeiterRepository();
    }

    @Test
    public void mappeIdeeDTOZuInternerIdee(){
        given.vollstaendigeEingabenFuerEineInterneIdee();
        when.derMapperMitDenEingabenAufgerufenWird();
        then.wirdEineIdeeMitDenWertenDerInternenIdeeZurueckgegeben();
    }

    @Test
    public void mappeIdeeDTOZuProduktidee(){
        given.vollstaendigeEingabenFuerEineProduktidee();
        when.derMapperMitDenEingabenAufgerufenWird();
        then.wirdEineIdeeMitDenWertenDerProduktideeZurueckgegeben();
    }

    @Test
    public void mappeIdeeDTOOhneTyp(){
        given.eingabenOhneIdeentyp();
        when.derMapperMitDenEingabenAufgerufenWird();
        then.wirdKeineIdeeZurueckGegeben();

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
    }

    private class When{

        public void derMapperMitDenEingabenAufgerufenWird() {
            idee = ideeMapper.mappeIdeeDTOZuIdee(ideeDTO);
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
        }

        public void wirdKeineIdeeZurueckGegeben() {
            assertNull(idee);
        }
    }
}
