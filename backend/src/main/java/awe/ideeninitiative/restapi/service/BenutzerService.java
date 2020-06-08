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


/**
 * Der Benutzer-Service enthält die Business-Logik für die Verwaltung der Benutzer.
 * @author njuergens
 */
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

    /**
     * mitarbeiterRegistrieren speichert einen Mitarbeiter in der Datenbank wenn dieser noch nicht existiert.
     * @param mitarbeiter
     * @return
     * @throws MitarbeiterExistiertBereitsException
     * @author njuergens
     */
    public Mitarbeiter mitarbeiterRegistrieren(Mitarbeiter mitarbeiter) throws MitarbeiterExistiertBereitsException {
        pruefeObMitarbeiterExistiert(mitarbeiter);
        mitarbeiter.setPasswort(bCryptPasswordEncoder.encode(mitarbeiter.getPasswort()));
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    /**
     * prüft ob mitarbeiter schon existiert
     * @param mitarbeiter
     * @throws MitarbeiterExistiertBereitsException
     * @author njuergens
     */
    private void pruefeObMitarbeiterExistiert(Mitarbeiter mitarbeiter) throws MitarbeiterExistiertBereitsException {
        Optional<Mitarbeiter> bereitsVorhandenerMitarbeiter = mitarbeiterRepository.findFirstByEmailOrBenutzername(mitarbeiter.getEmail(), mitarbeiter.getBenutzername());
        if(bereitsVorhandenerMitarbeiter.isPresent()){
            boolean gleicherBenutzername = mitarbeiter.getBenutzername().equalsIgnoreCase(bereitsVorhandenerMitarbeiter.get().getBenutzername());
            boolean gleicheEmail = mitarbeiter.getEmail().equalsIgnoreCase(bereitsVorhandenerMitarbeiter.get().getEmail());
            throw new MitarbeiterExistiertBereitsException(gleicherBenutzername, gleicheEmail);
        }
    }

    /**
     * meldet einen Benutzer in System an, wenn die richtige Kombination aus Passwort und Benutzername übergeben wurde.
     * @param benutzername
     * @param passwort
     * @return
     * @throws Exception
     * @author njuergens
     */
    public String mitarbeiterAnmelden(String benutzername, String passwort) throws Exception {
        pruefeBenutzernamenUndPasswort(benutzername, passwort);
        final UserDetails anmeldedaten = userDetailsService.loadUserByUsername(benutzername);
       return jwtUtil.generiereToken(anmeldedaten);
    }


    /**
     * prüft die Kombination aus Benutzername und Passwort
     * @param benutzername
     * @param passwort
     * @throws Exception
     * @author njuergens
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

    /**
     * Lädt eine liste aller Mitarbeiter wenn Benutzer die Rolle Admin hat.
     * @param benutzername
     * @return
     * @throws FehlendeRolleAdminException
     * @author njuergens
     */
    public List<Mitarbeiter> alleBenutzerLaden(String benutzername) throws FehlendeRolleAdminException {
        pruefeDassDerBenutzerEinAdminIst(benutzername);
        return mitarbeiterRepository.findAll();
    }

    /**
     * prüft ob der der Benutzer ein Admin ist
     * @param benutzername
     * @throws FehlendeRolleAdminException
     * @author njuergens
     */
    private void pruefeDassDerBenutzerEinAdminIst(String benutzername) throws FehlendeRolleAdminException {
        mitarbeiterRepository.findFirstByBenutzernameAndIstAdminTrue(benutzername).orElseThrow(() -> new FehlendeRolleAdminException(benutzername));
    }

    /**
     * aktualisiert die Rolle von Mitarbeitern im System wenn Benutzer admin ist
     * @param benutzername
     * @param mitarbeiter
     * @throws FehlendeRolleAdminException
     * @throws MitarbeiterExistiertNichtException
     * @author njuergens
     */
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

    /**
     * liefert EINEN Mitarbeiter aus der Datenbank mit passenden Benutzername
     * @param benutzername
     * @return
     * @throws MitarbeiterExistiertNichtException
     * @author njuergens
     */
    protected Mitarbeiter ladeMitarbeiterAusDatenbank(String benutzername) throws MitarbeiterExistiertNichtException {
        return mitarbeiterRepository.findFirstByBenutzername(benutzername).orElseThrow(() -> new MitarbeiterExistiertNichtException(benutzername));
    }

    /**
     * ändert das Profilbild des Nutzers in der Datenbank
     * @param benutzername
     * @param profilbildDatei
     * @throws IOException
     * @throws MitarbeiterExistiertNichtException
     * @author Vanessa Haubrok
     */
    public void profilbildAktualisieren(String benutzername, MultipartFile profilbildDatei) throws IOException, MitarbeiterExistiertNichtException {
        logger.error("Profilbild-Datei:" + profilbildDatei);

        Mitarbeiter mitarbeiter = ladeMitarbeiterAusDatenbank(benutzername);

        ProfilbildDatei profilbild = ProfilbildDateiBuilder.aProfilbildDatei().withMitarbeiter(mitarbeiter)
                .withDateiname(profilbildDatei.getOriginalFilename())
                .withDateityp(profilbildDatei.getContentType())
        .withDateiinhalt(profilbildDatei.getBytes()).build();
        mitarbeiter.setProfilbildDatei(profilbild);
        mitarbeiterRepository.save(mitarbeiter);

    }

    /**
     * lädt Profilbild  des Nutzers aus der Datenbank.
     * @param benutzername
     * @return
     * @author Vanessa Haubrok
     */
    public DateiDTO profilbildLaden(String benutzername) {
        ProfilbildDatei profilbild = profilbildDateiRepository.findByMitarbeiterBenutzername(benutzername).orElse(null);
        if(profilbild != null){
            return DateiDTOBuilder.aDateiDTO().withDateiinhalt(Base64.getEncoder().encodeToString(profilbild.getDateiinhalt()))
                    .withDateityp(profilbild.getDateityp()).build();
        }
        return null;
    }
}
