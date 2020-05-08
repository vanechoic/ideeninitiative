package awe.ideeninitiative.controller;

import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenutzerService {

    @Autowired private MitarbeiterRepository mitarbeiterRepository;

    public Mitarbeiter mitarbeiterAnlegen(Mitarbeiter mitarbeiter){
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }
}
