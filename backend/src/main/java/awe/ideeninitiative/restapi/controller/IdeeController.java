package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.api.IdeeApi;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.service.IdeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class IdeeController implements IdeeApi {

    static final Logger logger = LoggerFactory.getLogger(IdeeController.class);

    @Autowired
    private IdeeService ideeService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private IdeeMapper ideeMapper;

    @Override
    public ResponseEntity<List<IdeeDTO>> alleIdeenAbrufen() throws Exception {
        List<IdeeDTO> geladeneIdeeDTOs = ideeMapper.mappeIdeeZuIdeeDTO(ideeService.alleIdeenAbrufen());
        return ResponseEntity.ok(geladeneIdeeDTOs);
    }

    @Override
    public ResponseEntity<String> ideeBearbeiten(IdeeDTO ideeDTO) throws Exception {
        Idee idee = ideeMapper.mappeIdeeDTOZuIdee(ideeDTO);
        ideeService.ideeBearbeiten(idee);
        return ResponseEntity.ok("Idee erfolgreich bearbeitet.");
    }

    @Override
    public ResponseEntity<String> ideeLoeschen(String titel, String erfasser, String erstelldatum) throws Exception {
        ideeService.ideeLoeschen(titel, erfasser, erstelldatum);
        return ResponseEntity.ok(String.format("Die Idee %s von %s wurde erfolgreich gelöscht.", titel, erfasser));
    }

    @Override
    public ResponseEntity<List<IdeeDTO>> meineIdeen(String authorization) throws Exception {
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        List<IdeeDTO> geladeneIdeeDTOs = ideeMapper.mappeIdeeZuIdeeDTO(ideeService.meineIdeenAbrufen(benutzername));
        return ResponseEntity.ok(geladeneIdeeDTOs);
    }

    @Override
    public ResponseEntity<String> neueIdeeAnlegen(IdeeDTO ideeDTO) throws Exception {
        Idee idee = ideeMapper.mappeIdeeDTOZuIdee(ideeDTO);
        ideeService.neueIdeeAnlegen(idee);
        return ResponseEntity.ok("Idee wurde angelegt");
    }
}
