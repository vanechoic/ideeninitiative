package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.model.builder.*;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IdeeMapper {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public IdeeDTO mappeIdeeZuIdeeDTO(Idee idee){
        return IdeeDTOBuilder.anIdeeDTO().withBearbeitungsstatus(idee.getBearbeitungsstatus().toString())//
                .withBegruendung(idee.getBegruendung())
                .withBeschreibung(idee.getBeschreibung())
                .withTitel(idee.getTitel())
                .withErfasser(idee.getErfasser().getBenutzername())
                .withFachspezialist(idee.getFachspezialist() != null ? idee.getFachspezialist().getBenutzername() : null)
                .withTyp(idee.getTyp().toString())
                .build();
    }

    public List<IdeeDTO> mappeIdeeZuIdeeDTO(List<Idee> ideen){
        if(ideen != null && !ideen.isEmpty()){
            List<IdeeDTO> ideenDTOs = new ArrayList<>();
            ideen.stream().forEach(idee -> ideenDTOs.add(mappeIdeeZuIdeeDTO(idee)));
            return ideenDTOs;
        }
        return null;
    }

    public Idee mappeIdeeDTOZuIdee(IdeeDTO ideeDTO){
        if(ideeDTO.getTyp() == null || StringUtils.isEmpty(ideeDTO.getTyp())){
            return null;
        }
        Ideentyp ideentyp = Ideentyp.valueOf(ideeDTO.getTyp().toUpperCase());
        Idee idee = IdeeBuilder.anIdee().withBearbeitungsstatus(Ideenstatus.valueOf(ideeDTO.getBearbeitungsstatus().toUpperCase()))//
                .withBegruendung(ideeDTO.getBegruendung())
                .withBeschreibung(ideeDTO.getBeschreibung())
                .withTitel(ideeDTO.getTitel())
                .withErfasser(ermittleMitarbeiterZuBenutzernamen(ideeDTO.getErfasser()))
                .withFachspezialist(ermittleMitarbeiterZuBenutzernamen(ideeDTO.getFachspezialist()))
                .withTyp(ideentyp)
        .build();

        switch (ideentyp){
            case INTERNE_IDEE:
                mappeIdeeDTOZuInterneIdeeHandlungsfeld(ideeDTO, idee);
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

    private void mappeIdeeDTOZuInterneIdeeHandlungsfeld(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getHandlungsfeld() != null && !StringUtils.isEmpty(ideeDTO.getHandlungsfeld())){
            InterneIdeeHandlungsfeld interneIdeeHandlungsfeld = InterneIdeeHandlungsfeldBuilder.anInterneIdeeHandlungsfeld()//
                    .withIdee(idee)
                    .withHandlungsfeld(Handlungsfeld.valueOf(ideeDTO.getHandlungsfeld().toUpperCase())).build();
            idee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld);
        }
    }

    private void mappeIdeeDTOZuProduktideeSparte(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getSparten() != null && !StringUtils.isEmpty(ideeDTO.getSparten())){
            ProduktideeSparte produktideeSparte = ProduktideeSparteBuilder.aProduktideeSparte()//
            .withIdee(idee).withSparte(Sparte.valueOf(ideeDTO.getSparten().toUpperCase())).build();
            idee.setProduktideeSparte(produktideeSparte);
        }
    }

    private void mappeIdeeDTOZuProduktideeVertriebswege(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getVertriebsweg() != null){
            idee.setProduktideeVertriebsweg(mappeStringListeZuProduktideeVertriebsweg(idee, ideeDTO.getVertriebsweg()));
        }
    }

    private void mappeIdeeDTOZuProduktideeZielgruppen(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getZielgruppe() != null){
            idee.setProduktideeZielgruppe(mappeStringListeZuProduktideeZielgruppe(idee, ideeDTO.getZielgruppe()));
        }
    }

    private void mappeIdeeDTOZuProduktideeZusatzinformation(IdeeDTO ideeDTO, Idee idee) {
        if(ideeDTO.getExistiertBereits()){
            ProduktideeZusatzinformation produktideeZusatzinformation = ProduktideeZusatzinformationBuilder.aProduktideeZusatzinformation()//
                    .withIdee(idee)//
                    .withArtDerUmsetzung(ideeDTO.getArtDerUmsetzung())//
                    .withUnternehmensbezeichnung(ideeDTO.getUnternehmensbezeichnung())//
                    .withExistiertBereits(ideeDTO.getExistiertBereits()).build();
            idee.setProduktideeZusatzinformation(produktideeZusatzinformation);
        }
    }

    private Mitarbeiter ermittleMitarbeiterZuBenutzernamen(String benutzername) {
        Optional<Mitarbeiter> mitarbeiterOptional = mitarbeiterRepository.findFirstByBenutzername(benutzername);
        return mitarbeiterOptional.isPresent() ? mitarbeiterOptional.get() : null;
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