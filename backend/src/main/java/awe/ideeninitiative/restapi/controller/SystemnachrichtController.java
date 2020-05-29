package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.api.SystemnachrichtApi;
import awe.ideeninitiative.api.model.SystemnachrichtDTO;
import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.restapi.mapper.IdeeMapper;
import awe.ideeninitiative.restapi.mapper.SystemnachrichtMapper;
import awe.ideeninitiative.restapi.service.SystemnachrichtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class SystemnachrichtController implements SystemnachrichtApi {

    @Autowired
    private SystemnachrichtMapper systemnachrichtMapper;
    @Autowired
    private SystemnachrichtService systemnachrichtService;

    @Override
    public ResponseEntity<String> systemnachrichtAnlegen(SystemnachrichtDTO systemnachrichtDTO) throws Exception {
        Nachricht nachricht = systemnachrichtMapper.mappeSystemnachrichtDTOzuNachricht(systemnachrichtDTO);
        systemnachrichtService.systemnachrichtAnlegen(nachricht);
        return ResponseEntity.ok("Nachricht wurde erfolgreich gespeichert");
    }


}
