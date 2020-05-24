package awe.ideeninitiative.restapi.service;

import awe.ideeninitiative.exception.MitarbeiterExistiertBereitsException;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.restapi.security.JwtUtil;
import awe.ideeninitiative.restapi.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
