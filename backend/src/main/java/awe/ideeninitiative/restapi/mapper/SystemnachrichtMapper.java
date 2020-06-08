package awe.ideeninitiative.restapi.mapper;

import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.api.model.SystemnachrichtDTO;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.model.builder.NachrichtBuilder;
import awe.ideeninitiative.model.builder.SystemnachrichtDTOBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jakob
 * Mapper Datei für Nachricht-Objekt <-> Data Transfer Objekt
 * und Liste der Systemnachrichten
 */
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

    public List<SystemnachrichtDTO> mappeNachrichtzuSystemnachrichtDTO(List<Nachricht> nachrichten)
    {
        if(nachrichten != null && !nachrichten.isEmpty()){
            List<SystemnachrichtDTO> systemnachrichtDTOS = new ArrayList<>();
            nachrichten.stream().forEach(sn -> systemnachrichtDTOS.add(mappeNachrichtzuSystemnachrichtDTO(sn)));
            return systemnachrichtDTOS;
        }
        return null;
    }
}
