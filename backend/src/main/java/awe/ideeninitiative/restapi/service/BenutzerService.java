package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.api.model.BenutzerDTO;
import awe.ideeninitiative.api.model.DateiDTO;
import awe.ideeninitiative.exception.*;
import awe.ideeninitiative.model.builder.DateiDTOBuilder;
import awe.ideeninitiative.model.builder.ProfilbildDateiBuilder;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.ProfilbildDatei;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.model.repositories.ProfilbildDateiRepository;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.security.UserDetailsServiceImpl;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class BenutzerService {
    static final Logger logger = LoggerFactory.getLogger(BenutzerService.class);

    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired private JwtUtil jwtUtil;

    @Autowired private MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    private ProfilbildDateiRepository profilbildDateiRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Mitarbeiter mitarbeiterRegistrieren(Mitarbeiter mitarbeiter) throws MitarbeiterExistiertBereitsException {
        pruefeObMitarbeiterExistiert(mitarbeiter);
        mitarbeiter.setPasswort(bCryptPasswordEncoder.encode(mitarbeiter.getPasswort()));
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    private void pruefeObMitarbeiterExistiert(Mitarbeiter mitarbeiter) throws MitarbeiterExistiertBereitsException {
        Optional<Mitarbeiter> bereitsVorhandenerMitarbeiter = mitarbeiterRepository.findFirstByEmailOrBenutzername(mitarbeiter.getEmail(), mitarbeiter.getBenutzername());
        if(bereitsVorhandenerMitarbeiter.isPresent()){
            boolean gleicherBenutzername = mitarbeiter.getBenutzername().equalsIgnoreCase(bereitsVorhandenerMitarbeiter.get().getBenutzername());
            boolean gleicheEmail = mitarbeiter.getEmail().equalsIgnoreCase(bereitsVorhandenerMitarbeiter.get().getEmail());
            throw new MitarbeiterExistiertBereitsException(gleicherBenutzername, gleicheEmail);
        }
    }

    public String mitarbeiterAnmelden(String benutzername, String passwort) throws Exception {
        pruefeBenutzernamenUndPasswort(benutzername, passwort);
        final UserDetails anmeldedaten = userDetailsService.loadUserByUsername(benutzername);
       return jwtUtil.generiereToken(anmeldedaten);
    }

    /** authenticate!
     * @param benutzername
     * @param passwort
     * @throws Exception
     */
    private void pruefeBenutzernamenUndPasswort(String benutzername, String passwort) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(benutzername, passwort));
        } catch (DisabledException e) {
            throw new Exception(String.format("Der Benutzer {0} ist deaktiviert.", benutzername), e);
        } catch (BadCredentialsException e) {
            throw new Exception("Die Kombination aus Benutzername und Passwort stimmt nicht überein.", e);
        }
    }

    public List<Mitarbeiter> alleBenutzerLaden(String benutzername) throws FehlendeRolleAdminException {
        pruefeDassDerBenutzerEinAdminIst(benutzername);
        return mitarbeiterRepository.findAll();
    }
    private void pruefeDassDerBenutzerEinAdminIst(String benutzername) throws FehlendeRolleAdminException {
        mitarbeiterRepository.findFirstByBenutzernameAndIstAdminTrue(benutzername).orElseThrow(() -> new FehlendeRolleAdminException(benutzername));
    }

    public void mitarbeiterAktualisieren(String benutzername, Mitarbeiter mitarbeiter) throws FehlendeRolleAdminException, MitarbeiterExistiertNichtException {
        pruefeDassDerBenutzerEinAdminIst(benutzername);
        Mitarbeiter zuAktualisierenderMitarbeiter = ladeMitarbeiterAusDatenbank(mitarbeiter.getBenutzername());
        //Benutzername, ID, Erstelldatum können nicht modifiziert werden. Zugewiesene und angelegte Ideen bleiben unverändert.
        zuAktualisierenderMitarbeiter.setVorname(mitarbeiter.getVorname());
        zuAktualisierenderMitarbeiter.setNachname(mitarbeiter.getNachname());
        zuAktualisierenderMitarbeiter.setEmail(mitarbeiter.getEmail());
        zuAktualisierenderMitarbeiter.setProfilbildDatei(mitarbeiter.getProfilbildDatei());
        //Spezialisierungen
        zuAktualisierenderMitarbeiter.setFachspezialistZielgruppen(mitarbeiter.getFachspezialistZielgruppen());
        zuAktualisierenderMitarbeiter.setFachspezialistVertriebswege(mitarbeiter.getFachspezialistVertriebswege());
        zuAktualisierenderMitarbeiter.setFachspezialistSparten(mitarbeiter.getFachspezialistSparten());
        zuAktualisierenderMitarbeiter.setFachspezialistHandlungsfelder(mitarbeiter.getFachspezialistHandlungsfelder());
        //Rollen
        zuAktualisierenderMitarbeiter.setIstAdmin(mitarbeiter.istAdmin());
        zuAktualisierenderMitarbeiter.setIstFachspezialist(mitarbeiter.istFachspezialist());
        mitarbeiterRepository.save(zuAktualisierenderMitarbeiter);
    }

    protected Mitarbeiter ladeMitarbeiterAusDatenbank(String benutzername) throws MitarbeiterExistiertNichtException {
        return mitarbeiterRepository.findFirstByBenutzername(benutzername).orElseThrow(() -> new MitarbeiterExistiertNichtException(benutzername));
    }

    public void profilbildAktualisieren(String benutzername, MultipartFile profilbildDatei) throws IOException, MitarbeiterExistiertNichtException {
        logger.error("Profilbild-Datei:" + profilbildDatei);
        //Benutzername stimmt überein?
        Mitarbeiter mitarbeiter = ladeMitarbeiterAusDatenbank(benutzername);
        //MultipartFile -> File
        ProfilbildDatei profilbild = ProfilbildDateiBuilder.aProfilbildDatei().withMitarbeiter(mitarbeiter)
                .withDateiname(profilbildDatei.getOriginalFilename())
                .withDateityp(profilbildDatei.getContentType())
        .withDateiinhalt(profilbildDatei.getBytes()).build();
        mitarbeiter.setProfilbildDatei(profilbild);
        mitarbeiterRepository.save(mitarbeiter);

        //Gucken, ob bereits ein Profilbild vorliegt
        //Wenn Bild vorliegt: aktualisieren
        //Sonst: Neu anlegen -> wird schon automatisch gemacht?

    }

    public DateiDTO profilbildLaden(String benutzername) {
        ProfilbildDatei profilbild = profilbildDateiRepository.findByMitarbeiterBenutzername(benutzername).orElse(null);
        if(profilbild != null){
            return DateiDTOBuilder.aDateiDTO().withDateiinhalt(Base64.getEncoder().encodeToString(profilbild.getDateiinhalt()))
                    .withDateityp(profilbild.getDateityp()).build();
        }
        return null;
    }
}
