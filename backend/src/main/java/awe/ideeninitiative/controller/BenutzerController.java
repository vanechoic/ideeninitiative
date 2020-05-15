package awe.ideeninitiative.controller;

import awe.ideeninitiative.api.model.Benutzer;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BenutzerController implements awe.ideeninitiative.api.BenutzerApi {

    static final Logger logger = LoggerFactory.getLogger(BenutzerController.class);

    @Autowired private BenutzerService benutzerService;

    @Override
    public ResponseEntity<Void> benutzerAbmelden() {
        logger.error("funzt");
        return new ResponseEntity<Void>(HttpStatus.I_AM_A_TEAPOT);
    }

    @Override
    public ResponseEntity<String> benutzerAnlegen(Benutzer benutzer){
        logger.error(benutzer.getVorname());
        Mitarbeiter neuerMitarbeiter = MitarbeiterBuilder.aMitarbeiter()//
                .withBenutzername(benutzer.getBenutzername())//
                .withVorname(benutzer.getVorname())//
                .withNachname(benutzer.getNachname())//
                .withEmail(benutzer.getEmail())//
                .withPasswort(benutzer.getPasswort()).build(); //TODO: Passwort verschlüsseln!
        benutzerService.mitarbeiterAnlegen(neuerMitarbeiter);
        return ResponseEntity.ok(neuerMitarbeiter.toString());
    }

    @Override
    public ResponseEntity<String> benutzerAnmelden(String benutzername, String passwort) {
        logger.error(benutzername + " mit "+ passwort);
        final String token;
        try {
            token = benutzerService.mitarbeiterAnmelden(benutzername, passwort);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(token); //TODO: new JwtResponse?
    }

    @Override
    public ResponseEntity<Void> benutzerdatenAktualisieren(String benutzername, Benutzer body) {
        logger.error("funzt");
        return new ResponseEntity<Void>(HttpStatus.I_AM_A_TEAPOT);
    }

    @Override
    public ResponseEntity<Void> benutzerdatenLoeschen(String benutzername) {
        logger.error("funzt");
        return new ResponseEntity<Void>(HttpStatus.I_AM_A_TEAPOT);
    }
}