package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.model.builder.BenutzerDTOBuilder;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.AbstrakterApiTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

//TODO: Mockito Annotation?
public class BenutzerMapperTest extends AbstrakterApiTest {

    private Given given = new Given();
    private When when  = new When();
    private Then then = new Then();

    @InjectMocks
    private BenutzerMapper benutzerMapper;
    @Mock
    private MitarbeiterRepository mitarbeiterRepositoryMock;

    private BenutzerDTO benutzerDTO;
    private Mitarbeiter mitarbeiter;
    private List<Sparte> sparten;
    private List<Vertriebskanal> vertriebswege;
    private List<Zielgruppe> zielgruppen;
    private List<Handlungsfeld> handlungsfelder;

    @Before
    public void setup(){
        //MockitoAnnotations.initMocks(this);
        sparten = Arrays.asList(Sparte.KFZ, Sparte.HAFTPFLICHT);
        vertriebswege = Arrays.asList(Vertriebskanal.STATIONAERER_VERTRIEB, Vertriebskanal.DIREKTVERSICHERUNG, Vertriebskanal.VERSICHERUNGSMAKLER);
        zielgruppen = Arrays.asList(Zielgruppe.SINGLES, Zielgruppe.PERSONEN_50PLUS);
        handlungsfelder = Arrays.asList(Handlungsfeld.ERTRAGSSTEIGERUNG, Handlungsfeld.ZUKUNFTSFAEHIGKEIT);
    }

    @Test
    public void mappeBenutzerDTOZuMitarbeiterMax(){
        given.einBenutzerDTOMax();
        when.mappeBenutzerDTOZuMitarbeiterAufgerufenWird();
        then.wirdEinMitarbeiterMitDenWertenDesBenutzerDTOZurueckgegeben();
    }

    @Test
    public void mappeMitarbeiterZuBenutzerDTOMax(){
        //TODO: Impl
        given.einMitarbeiterMax();
        when.mappeMitarbeiterZuBenutzerDTOAufgerufenWird();
        then.wirdEinBenutzerDTOMitDenWertenDesMitarbeitersZurueckgegeben();
    }

    private class Given{

        public void einBenutzerDTOMax() {
            benutzerDTO = BenutzerDTOBuilder.aBenutzerDTO().withBenutzername("gryffindorsGranger")
                    .withVorname("Hermione").withNachname("Granger")
                    .withEmail("hermione@granger.com").withPasswort("wingardiumLeviosa")
                    .withIstAdmin(true)
                    .withIstFachspezialist(true)
                    .withFachspezialistHandlungsfelder(handlungsfelder.stream().map(Handlungsfeld::toString).collect(Collectors.toList()))
                    .withFachspezialistSparten(sparten.stream().map(Sparte::toString).collect(Collectors.toList()))
                    .withFachspezialistVertriebswege(vertriebswege.stream().map(Vertriebskanal::toString).collect(Collectors.toList()))
                    .withFachspezialistZielgruppen(zielgruppen.stream().map(Zielgruppe::toString).collect(Collectors.toList())).build();
        }

        public void einMitarbeiterMax() {
            mitarbeiter = MitarbeiterBuilder.aMitarbeiter().withBenutzername("theSerpent")
                    .withVorname("Draco").withNachname("Malfoy")
                    .withEmail("draco@malfoy.com").withPasswort("slytheringForever")
                    .withIstAdmin(true)
                    .withIstFachspezialist(true)
                    .withId(1l)
                    .withErstellzeitpunkt(LocalDateTime.now())
                    .build();
            mitarbeiter.addFachspezialistSparte(sparten.toArray(new Sparte[sparten.size()]));
            mitarbeiter.addFachspezialistZielgruppe(zielgruppen.toArray(new Zielgruppe[zielgruppen.size()]));
            mitarbeiter.addFachspezialistVertriebsweg(vertriebswege.toArray(new Vertriebskanal[vertriebswege.size()]));
            mitarbeiter.addFachspezialistHandlungsfeld(handlungsfelder.toArray(new Handlungsfeld[handlungsfelder.size()]));
        }
    }

    private class When{

        public void mappeBenutzerDTOZuMitarbeiterAufgerufenWird() {
            mitarbeiter = benutzerMapper.mappeBenutzerDTOZuMitarbeiter(benutzerDTO);
        }

        public void mappeMitarbeiterZuBenutzerDTOAufgerufenWird() {
            benutzerDTO = benutzerMapper.mappeMitarbeiterZuBenutzerDTO(mitarbeiter);
        }
    }

    private class Then{

        public void wirdEinMitarbeiterMitDenWertenDesBenutzerDTOZurueckgegeben() {
            dieAllgemeinenInformationenStimmenUeberein();
            assertEquals(benutzerDTO.getPasswort(), mitarbeiter.getPasswort());
            //pruefe Spezialisierungen
            dieVertriebswegeStimmenUeberein();
            dieZielgruppenStimmenUeberein();
            dieSpartenStimmenUeberein();
            dieHandlungsfelderStimmenUeberein();
        }

        private void dieAllgemeinenInformationenStimmenUeberein() {
            assertNotNull(benutzerDTO);
            assertNotNull(mitarbeiter);
            assertEquals(benutzerDTO.getBenutzername(), mitarbeiter.getBenutzername());
            assertEquals(benutzerDTO.getVorname(), mitarbeiter.getVorname());
            assertEquals(benutzerDTO.getNachname(), mitarbeiter.getNachname());
            assertEquals(benutzerDTO.getEmail(), mitarbeiter.getEmail());
            assertEquals(benutzerDTO.getIstAdmin(), mitarbeiter.istAdmin());
            assertEquals(benutzerDTO.getIstFachspezialist(), mitarbeiter.istFachspezialist());
        }

        private void dieHandlungsfelderStimmenUeberein() {
            assertNotNull(mitarbeiter.getFachspezialistHandlungsfelder());
            assertNotNull(benutzerDTO.getFachspezialistHandlungsfelder());
            assertEquals(benutzerDTO.getFachspezialistHandlungsfelder().size(), mitarbeiter.getFachspezialistHandlungsfelder().size());
            for (String handlungsfeld: benutzerDTO.getFachspezialistHandlungsfelder()) {
                assertTrue(mitarbeiter.getFachspezialistHandlungsfelder().stream().anyMatch(hf -> hf.getHandlungsfeld().toString().equals(handlungsfeld)));
            }
        }

        private void dieSpartenStimmenUeberein() {
            assertNotNull(mitarbeiter.getFachspezialistSparten());
            assertNotNull(benutzerDTO.getFachspezialistSparten());
            assertEquals(benutzerDTO.getFachspezialistSparten().size(), mitarbeiter.getFachspezialistSparten().size());
            for (String sparte: benutzerDTO.getFachspezialistSparten()) {
                assertTrue(mitarbeiter.getFachspezialistSparten().stream().anyMatch(sp -> sp.getSparte().toString().equals(sparte)));
            }
        }

        private void dieVertriebswegeStimmenUeberein() {
            assertNotNull(mitarbeiter.getFachspezialistVertriebswege());
            assertNotNull(benutzerDTO.getFachspezialistVertriebswege());
            assertEquals(benutzerDTO.getFachspezialistVertriebswege().size(), mitarbeiter.getFachspezialistVertriebswege().size());
            for (String vertriebsweg: benutzerDTO.getFachspezialistVertriebswege()) {
                assertTrue(mitarbeiter.getFachspezialistVertriebswege().stream().anyMatch(vw -> vw.getVertriebsweg().toString().equals(vertriebsweg)));
            }
        }

        private void dieZielgruppenStimmenUeberein() {
            assertNotNull(mitarbeiter.getFachspezialistZielgruppen());
            assertNotNull(benutzerDTO.getFachspezialistZielgruppen());
            assertEquals(benutzerDTO.getFachspezialistZielgruppen().size(), mitarbeiter.getFachspezialistZielgruppen().size());
            for (String zielgruppe: benutzerDTO.getFachspezialistZielgruppen()) {
                assertTrue(mitarbeiter.getFachspezialistZielgruppen().stream().anyMatch(vw -> vw.getZielgruppe().toString().equals(zielgruppe)));
            }
        }

        public void wirdEinBenutzerDTOMitDenWertenDesMitarbeitersZurueckgegeben() {
            dieAllgemeinenInformationenStimmenUeberein();
            dieVertriebswegeStimmenUeberein();
            dieZielgruppenStimmenUeberein();
            dieSpartenStimmenUeberein();
            dieHandlungsfelderStimmenUeberein();
            assertNull(benutzerDTO.getPasswort());
        }
    }
}
