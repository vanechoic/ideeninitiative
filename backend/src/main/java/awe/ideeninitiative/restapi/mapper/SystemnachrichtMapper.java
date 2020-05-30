package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.SystemnachrichtDTO;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.model.builder.NachrichtBuilder;
import awe.ideeninitiative.model.builder.SystemnachrichtDTOBuilder;
import org.springframework.stereotype.Component;

@Component
public class SystemnachrichtMapper {

    public Nachricht mappeSystemnachrichtDTOzuNachricht(SystemnachrichtDTO systemnachrichtDTO){
        if (systemnachrichtDTO != null) {
            return NachrichtBuilder.aNachricht().withTitel(systemnachrichtDTO.getTitel()).withBeschreibung(systemnachrichtDTO.getBeschreibung()).build();
        }
        return null;
    }

    public SystemnachrichtDTO mappeNachrichtzuSystemnachrichtDTO(Nachricht nachricht)
    {
        if (nachricht != null)
        {
            return SystemnachrichtDTOBuilder.aSystemnachrichtDTO().withTitel(nachricht.getTitel()).withBeschreibung(nachricht.getBeschreibung()).build();
        }
        return null;
    }
}
