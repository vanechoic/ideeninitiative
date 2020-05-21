package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.model.builder.IdeeBuilder;
import awe.ideeninitiative.model.builder.IdeeDTOBuilder;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IdeeMapper {

    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    public IdeeDTO mappeIdeeZuIdeeDTO(Idee idee){
        //TODO: Nullsafe
        return IdeeDTOBuilder.anIdeeDTO().withBearbeitungsstatus(idee.getBearbeitungsstatus().toString())//
                .withBegruendung(idee.getBegruendung())
                .withBeschreibung(idee.getBeschreibung())
                .withBezeichnung(idee.getBezeichnung())
                .withErfasser(idee.getErfasser().getBenutzername())
                .withFachspezialist(idee.getFachspezialist().getBenutzername())
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
        return IdeeBuilder.anIdee().withBearbeitungsstatus(Ideenstatus.valueOf(ideeDTO.getBearbeitungsstatus().toUpperCase()))//
                .withBegruendung(ideeDTO.getBegruendung())
                .withBeschreibung(ideeDTO.getBeschreibung())
                .withBezeichnung(ideeDTO.getBezeichnung())
                .withErfasser(ermittleMitarbeiterZuBenutzernamen(ideeDTO.getErfasser()))
        .build();
    }

    private Mitarbeiter ermittleMitarbeiterZuBenutzernamen(String benutzername) {
        Optional<Mitarbeiter> mitarbeiterOptional = mitarbeiterRepository.findFirstByBenutzername(benutzername);
        return mitarbeiterOptional.isPresent() ? mitarbeiterOptional.get() : null;
    }
}
