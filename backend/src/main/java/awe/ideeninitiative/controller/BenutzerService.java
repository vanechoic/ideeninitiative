package awe.ideeninitiative.controller;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import awe.ideeninitiative.security.JwtUtil;
import awe.ideeninitiative.security.UserDetailsServiceImpl;
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

import java.time.Instant;
import java.util.Date;

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

    public Mitarbeiter mitarbeiterRegistrieren(Mitarbeiter mitarbeiter){
        pruefeObMitarbeiterExistiert(mitarbeiter);
        mitarbeiter.setPasswort(bCryptPasswordEncoder.encode(mitarbeiter.getPasswort()));
        mitarbeiterRepository.save(mitarbeiter);
        return mitarbeiter;
    }

    private void pruefeObMitarbeiterExistiert(Mitarbeiter mitarbeiter) {
        mitarbeiterRepository.findFirstByEmailOrBenutzername(mitarbeiter.getEmail(), mitarbeiter.getBenutzername());
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
