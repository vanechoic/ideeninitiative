package awe.ideeninitiative.controller;

import awe.ideeninitiative.api.IdeeApi;
import awe.ideeninitiative.api.model.Idee;
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

    @Override
    public ResponseEntity<List<Idee>> alleIdeenAbrufen() throws Exception {
        logger.error("alle Ideen abrufen");
        ideeService.alleIdeenAbrufen();
        return null;

    }

    @Override
    public ResponseEntity<List<Idee>> meineIdeen() throws Exception {
        logger.error("meine Ideen");
        String benutzername = "tbd"; //TODO: Benutzernamen aus Token ziehen
        ideeService.meineIdeenAbrufen(benutzername);
        return null;
    }
}
