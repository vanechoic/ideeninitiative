package awe.ideeninitiative.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IdeeService {

    static final Logger logger = LoggerFactory.getLogger(IdeeService.class);

    public void meineIdeenAbrufen(String benutzername) {
        logger.warn("IdeeService.meineIdeenAbrufen()");
    }

    public void alleIdeenAbrufen() {
        logger.warn("IdeeService.alleIdeenAbrufen()");
    }
}
