package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.exception.*;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.util.DatumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jakob
 * Mapper Datei für Idee-Objekt <-> Data Transfer Objekt
 * Übersetzt Frontend Dateiformat in Backend Format
 * Mappt außerdem relevante Informationen wie
 * Zielgruppen, Sparte und Vorteile
 */
@Component
public class IdeeMapper {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public IdeeDTO mappeIdeeZuIdeeDTO(Idee idee){
        IdeeDTO ideeDTO = IdeeDTOBuilder.anIdeeDTO().withBearbeitungsstatus(idee.getBearbeitungsstatus().toString())//
                .withBegruendung(idee.getBegruendung())
                .withBeschreibung(idee.getBeschreibung())
                .withTitel(idee.getTitel())
                .withErfasser(idee.getErfasser().getBenutzername())
                .withFachspezialist(idee.getFachspezialist() != null ? idee.getFachspezialist().getBenutzername() : null)
                .withTyp(idee.getTyp().toString())
                .withHandlungsfeld(idee.getInterneIdeeHandlungsfeld() != null ? idee.getInterneIdeeHandlungsfeld().getHandlungsfeld().toString() : null)
                .withSparten(idee.getProduktideeSparte() != null ? idee.getProduktideeSparte().getSparte().toString() : null)
                .withErstellzeitpunkt(DatumUtil.formeDatumZuStringUm(idee.getErstellzeitpunkt()))
                .build();
        if(idee.getProduktideeVertriebsweg() != null && !idee.getProduktideeVertriebsweg().isEmpty()){
            ideeDTO.setVertriebsweg(mappeProduktideeVertriebswegeZuStringListe(idee.getProduktideeVertriebsweg()));
        }
        if(idee.getProduktideeZielgruppe() != null && !idee.getProduktideeZielgruppe().isEmpty()){
            ideeDTO.setZielgruppe(mappeProduktideeZielgruppenZuStringListe(idee.getProduktideeZielgruppe()));
        }
        if(idee.getProduktideeZusatzinformation() != null){
            ideeDTO.setExistiertBereits(idee.getProduktideeZusatzinformation().isExistiertBereits());
            ideeDTO.setArtDerUmsetzung(idee.getProduktideeZusatzinformation().getArtDerUmsetzung());
            ideeDTO.setUnternehmensbezeichnung(idee.getProduktideeZusatzinformation().getUnternehmensbezeichnung());
        }
        if(idee.getVorteile() != null && !idee.getVorteile().isEmpty()){
            ideeDTO.setVorteile(idee.getVorteileWerte());
        }
        return ideeDTO;
    }

    private List<String> mappeProduktideeZielgruppenZuStringListe(List<ProduktideeZielgruppe> zielgruppen) {
        if(zielgruppen == null || zielgruppen.isEmpty()){
            return null;
        }
        return zielgruppen.stream()
                .map(zg -> zg.getZielgruppe().toString())
                .collect(Collectors.toList());
    }

    private List<String> mappeProduktideeVertriebswegeZuStringListe(List<ProduktideeVertriebsweg> vertriebswege) {
        if(vertriebswege == null || vertriebswege.isEmpty()){
            return null;
        }
        return vertriebswege.stream()
                .map(vw -> vw.getVertriebsweg().toString())
                .collect(Collectors.toList());
    }

    public List<IdeeDTO> mappeIdeeZuIdeeDTO(List<Idee> ideen){
        if(ideen != null && !ideen.isEmpty()){
            List<IdeeDTO> ideenDTOs = new ArrayList<>();
            ideen.stream().forEach(idee -> ideenDTOs.add(mappeIdeeZuIdeeDTO(idee)));
            return ideenDTOs;
        }
        return null;
    }

    public Idee mappeIdeeDTOZuIdee(IdeeDTO ideeDTO) throws ApiException {
        if(ideeDTO.getTyp() == null || StringUtils.isEmpty(ideeDTO.getTyp())){
            return null;
        }
        Ideentyp ideentyp = Ideentyp.valueOf(ideeDTO.getTyp().toUpperCase());
        Idee idee = IdeeBuilder.anIdee().withBearbeitungsstatus(Ideenstatus.valueOf(ideeDTO.getBearbeitungsstatus().toUpperCase()))//
                .withBegruendung(ideeDTO.getBegruendung())
                .withBeschreibung(ideeDTO.getBeschreibung())
                .withTitel(ideeDTO.getTitel())
                .withErfasser(ermittleMitarbeiterZuBenutzernamen(ideeDTO.getErfasser()))
                .withErstellzeitpunkt(DatumUtil.formeStringZuDatumUm(ideeDTO.getErstellzeitpunkt()))
                .withFachspezialist(ideeDTO.getFachspezialist() != null ? ermittleMitarbeiterZuBenutzernamen(ideeDTO.getFachspezialist()) : null)
                .withTyp(ideentyp)
        .build();
        mappeIdeeDTOVorteileZuIdeeVorteile(ideeDTO, idee);

        switch (ideentyp){
            case INTERNE_IDEE:
                mappeIdeeDTOHandlungsfeldZuInterneIdeeHandlungsfeld(ideeDTO, idee);
                break;
            case PRODUKTIDEE:
                mappeIdeeDTOZuProduktideeSparte(ideeDTO, idee);
                mappeIdeeDTOZuProduktideeVertriebswege(ideeDTO, idee);
                mappeIdeeDTOZuProduktideeZielgruppen(ideeDTO, idee);
                mappeIdeeDTOZuProduktideeZusatzinformation(ideeDTO, idee);
                break;
            default:
                return null;
        }
        return idee;
    }

    private void mappeIdeeDTOVorteileZuIdeeVorteile(IdeeDTO ideeDTO, Idee idee) throws MaximaleAnzahlVorteileUeberschrittenException {
        if(ideeDTO.getVorteile() != null && !ideeDTO.getVorteile().isEmpty()){
            idee.setVorteile(mappeStringListeZuVorteile(idee, ideeDTO.getVorteile()));
        }
    }

    private List<Vorteil> mappeStringListeZuVorteile(Idee idee, List<String> vorteileStringListe) {
        if(idee != null && vorteileStringListe != null && !vorteileStringListe.isEmpty()){
            List<Vorteil> vorteile = new ArrayList<>();
            vorteileStringListe.stream().forEach(vorteilString -> vorteile.add(VorteilBuilder.aVorteil().withIdee(idee).withBeschreibung(vorteilString).build()));
            return vorteile;
        }
        return null;
    }

    private void mappeIdeeDTOHandlungsfeldZuInterneIdeeHandlungsfeld(IdeeDTO ideeDTO, Idee idee) throws InterneIdeeOhneHandlungsfeldException {
        if(ideeDTO.getHandlungsfeld() != null && !StringUtils.isEmpty(ideeDTO.getHandlungsfeld())){
            InterneIdeeHandlungsfeld interneIdeeHandlungsfeld = InterneIdeeHandlungsfeldBuilder.anInterneIdeeHandlungsfeld()//
                    .withIdee(idee)
                    .withHandlungsfeld(Handlungsfeld.valueOf(ideeDTO.getHandlungsfeld().toUpperCase())).build();
            idee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld);
        } else {
            throw new InterneIdeeOhneHandlungsfeldException(idee);
        }
    }

    private void mappeIdeeDTOZuProduktideeSparte(IdeeDTO ideeDTO, Idee idee) throws ProduktideeOhneSparteException {
        if(ideeDTO.getSparten() != null && !StringUtils.isEmpty(ideeDTO.getSparten())){
            ProduktideeSparte produktideeSparte = ProduktideeSparteBuilder.aProduktideeSparte()//
            .withIdee(idee).withSparte(Sparte.valueOf(ideeDTO.getSparten().toUpperCase())).build();
            idee.setProduktideeSparte(produktideeSparte);
        } else{
            throw new ProduktideeOhneSparteException(idee);
        }
    }

    private void mappeIdeeDTOZuProduktideeVertriebswege(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getVertriebsweg() != null){
            idee.setProduktideeVertriebsweg(mappeStringListeZuProduktideeVertriebsweg(idee, ideeDTO.getVertriebsweg()));
        }
    }

    private void mappeIdeeDTOZuProduktideeZielgruppen(IdeeDTO ideeDTO, Idee idee) throws ProduktideeOhneZielgruppeException {
        if(ideeDTO.getZielgruppe() != null && !ideeDTO.getZielgruppe().isEmpty()){
            idee.setProduktideeZielgruppe(mappeStringListeZuProduktideeZielgruppe(idee, ideeDTO.getZielgruppe()));
        } else{
            throw new ProduktideeOhneZielgruppeException(idee);
        }
    }

    private void mappeIdeeDTOZuProduktideeZusatzinformation(IdeeDTO ideeDTO, Idee idee) {
            ProduktideeZusatzinformation produktideeZusatzinformation = ProduktideeZusatzinformationBuilder.aProduktideeZusatzinformation()//
                    .withIdee(idee)//
                    .withArtDerUmsetzung(ideeDTO.getArtDerUmsetzung())//
                    .withUnternehmensbezeichnung(ideeDTO.getUnternehmensbezeichnung())//
                    .withExistiertBereits(ideeDTO.getExistiertBereits() == null ? false : ideeDTO.getExistiertBereits()).build();
            idee.setProduktideeZusatzinformation(produktideeZusatzinformation);
    }

    private Mitarbeiter ermittleMitarbeiterZuBenutzernamen(String benutzername) throws MitarbeiterExistiertNichtException {
        return mitarbeiterRepository.findFirstByBenutzername(benutzername).orElseThrow(() -> new MitarbeiterExistiertNichtException(benutzername));
    }

    private List<ProduktideeVertriebsweg> mappeStringListeZuProduktideeVertriebsweg(Idee idee, List<String> stringListe){
        if(idee != null && stringListe != null && !stringListe.isEmpty()){
            List<ProduktideeVertriebsweg> vertriebswege = new ArrayList<>();
            stringListe.stream().forEach(vertriebswegString -> vertriebswege.add(ProduktideeVertriebswegBuilder.aProduktideeVertriebsweg()//
                    .withIdee(idee).withVertriebsweg(Vertriebskanal.valueOf(vertriebswegString.toUpperCase()))
                    .build()));
            return vertriebswege;
        }
        return null;
    }

    private List<ProduktideeZielgruppe> mappeStringListeZuProduktideeZielgruppe(Idee idee, List<String> stringListe){
        if(idee != null && stringListe != null && !stringListe.isEmpty()){
            List<ProduktideeZielgruppe> zielgruppen = new ArrayList<>();
            stringListe.stream().forEach(zielgruppeString -> zielgruppen.add(ProduktideeZielgruppeBuilder.aProduktideeZielgruppe()//
                    .withIdee(idee).withZielgruppe(Zielgruppe.valueOf(zielgruppeString.toUpperCase()))
                    .build()));
            return zielgruppen;
        }
        return null;
    }
}
