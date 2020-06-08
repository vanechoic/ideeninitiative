package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.api.SystemnachrichtApi;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.api.model.SystemnachrichtDTO;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import awe.ideeninitiative.restapi.mapper.SystemnachrichtMapper;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.service.SystemnachrichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @author Jakob
 * Controller für das Laden, Anlegen und Löschen von Ideen
 */
@RestController
public class SystemnachrichtController implements SystemnachrichtApi {

    @Autowired
    private SystemnachrichtMapper systemnachrichtMapper;
    @Autowired
    private SystemnachrichtService systemnachrichtService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<List<SystemnachrichtDTO>> alleSystemnachrichtenLaden(String authorization) throws Exception {
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        List<SystemnachrichtDTO> systemnachrichtDTOS = systemnachrichtMapper.mappeNachrichtzuSystemnachrichtDTO(systemnachrichtService.alleSystemnachrichtenLaden(benutzername));
        return ResponseEntity.ok(systemnachrichtDTOS);
    }

    @Override
    public ResponseEntity<String> systemnachrichtAnlegen(SystemnachrichtDTO systemnachrichtDTO) throws Exception {
        Nachricht nachricht = systemnachrichtMapper.mappeSystemnachrichtDTOzuNachricht(systemnachrichtDTO);
        systemnachrichtService.systemnachrichtAnlegen(nachricht);
        return ResponseEntity.ok(String.format("Nachricht '%s' wurde erfolgreich gespeichert.", systemnachrichtDTO.getTitel()));
    }

    @Override
    public ResponseEntity<String> systemnachrichtLoeschen(String authorization, SystemnachrichtDTO systemnachrichtDTO) throws Exception {
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        Nachricht gemappteNachricht = systemnachrichtMapper.mappeSystemnachrichtDTOzuNachricht(systemnachrichtDTO);
        systemnachrichtService.systemnachrichtLoeschen(benutzername, gemappteNachricht);
        return ResponseEntity.ok(String.format("Nachricht '%s' wurde erfolgreich gelöscht.", systemnachrichtDTO.getTitel()));
    }


}
