package awe.ideeninitiative.restapi.controller;

import awe.ideeninitiative.api.BenutzerApi;
import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.api.model.IdeeDTO;
import awe.ideeninitiative.api.model.InlineObject;
import awe.ideeninitiative.exception.MitarbeiterExistiertBereitsException;
import awe.ideeninitiative.model.builder.MitarbeiterBuilder;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.restapi.mapper.BenutzerMapper;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.service.BenutzerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class BenutzerController implements BenutzerApi {

    static final Logger logger = LoggerFactory.getLogger(BenutzerController.class);

    @Autowired
    private BenutzerService benutzerService;
    @Autowired
    private BenutzerMapper benutzerMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<List<BenutzerDTO>> alleBenutzerLaden(String authorization) throws Exception {
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        List<BenutzerDTO> geladeneBenutzer = benutzerMapper.mappeMitarbeiterZuBenutzerDTO(benutzerService.alleBenutzerLaden(benutzername));
        return ResponseEntity.ok(geladeneBenutzer);
    }

    @Override
    public ResponseEntity<Void> benutzerAbmelden() {
        logger.error("funzt");
        //TODO: Impl und Verwendung
        return new ResponseEntity<Void>(HttpStatus.I_AM_A_TEAPOT);
    }

    @Override
    public ResponseEntity<String> benutzerRegistrieren(BenutzerDTO benutzer) throws MitarbeiterExistiertBereitsException {
        Mitarbeiter neuerMitarbeiter = MitarbeiterBuilder.aMitarbeiter()//
                .withBenutzername(benutzer.getBenutzername())//
                .withVorname(benutzer.getVorname())//
                .withNachname(benutzer.getNachname())//
                .withEmail(benutzer.getEmail())//
                .withPasswort(benutzer.getPasswort()).build(); //TODO: Passwort verschlüsseln!
        benutzerService.mitarbeiterRegistrieren(neuerMitarbeiter);
        return ResponseEntity.ok(neuerMitarbeiter.toString());
    }

    @Override
    public ResponseEntity<String> mitarbeiterAktualisieren(String authorization, BenutzerDTO benutzerDTO) throws Exception {
        String benutzername = jwtUtil.extrahiereBenutzernamenAusAuthorizationHeader(authorization);
        Mitarbeiter mitarbeiter = benutzerMapper.mappeBenutzerDTOZuMitarbeiter(benutzerDTO);
        benutzerService.mitarbeiterAktualisieren(benutzername, mitarbeiter);
        return ResponseEntity.ok(String.format("Benutzer %s erfolgreich aktualisiert.",  benutzerDTO.getBenutzername()));
    }

    @Override
    public ResponseEntity<String> benutzerAnmelden(InlineObject anmeldedaten) throws Exception {
        final String token;
        try {
            token = benutzerService.mitarbeiterAnmelden(anmeldedaten.getBenutzername(), anmeldedaten.getPasswort());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(token); //TODO: new JwtResponse?
    }
}