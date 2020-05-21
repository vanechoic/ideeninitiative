package awe.ideeninitiative.controller;

import awe.ideeninitiative.api.IdeeApi;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Override
    public ResponseEntity<List<IdeeDTO>> alleIdeenAbrufen() throws Exception {
        logger.error("alle Ideen abrufen");
        ideeService.alleIdeenAbrufen();
        return null;

    }

    @Override
    public ResponseEntity<List<IdeeDTO>> meineIdeen(String authorization) throws Exception {
        logger.error("meine Ideen");
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        ideeService.meineIdeenAbrufen(benutzername);
        //TODO: Mapper Idee->Idee
        return null;
    }
}
