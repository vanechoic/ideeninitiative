package awe.ideeninitiative.controller;

import awe.ideeninitiative.model.idee.Idee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeeService {

    static final Logger logger = LoggerFactory.getLogger(IdeeService.class);

    public List<Idee> meineIdeenAbrufen(String benutzername) {
        logger.warn("IdeeService.meineIdeenAbrufen()");
        return null;
    }

    public void alleIdeenAbrufen() {
        logger.warn("IdeeService.alleIdeenAbrufen()");
    }
}
