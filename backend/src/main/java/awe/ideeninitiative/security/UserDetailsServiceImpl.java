package awe.ideeninitiative.security;

import awe.ideeninitiative.controller.BenutzerService;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired private MitarbeiterRepository mitarbeiterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.error("UserDetailsService - username: " + username);
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findFirstByBenutzername(username);
        if(mitarbeiter == null || mitarbeiter.isEmpty()){
            throw new UsernameNotFoundException("Für den Benutzernamen "+username+" existiert kein Konto.");
        }
        return benutzerDatenAlsUserDetailsVerpacken(mitarbeiter.get());
    }

    private UserDetails benutzerDatenAlsUserDetailsVerpacken(Mitarbeiter mitarbeiter) {
        return User.withUsername(mitarbeiter.getBenutzername())
                .password(mitarbeiter.getPasswort()) //TODO: Passwort entschlüsseln!
                .roles(ermittleBenutzerrollenAlsString(mitarbeiter)).build();
    }

    private String ermittleBenutzerrollenAlsString(Mitarbeiter mitarbeiter) {
        List<String> rollen = new ArrayList<String>();
        rollen.add(BenutzerRollen.ROLE_MITARBEITER.toString()); //TODO: Sauber: Wie wird der Admin gekennzeichnet?
        if(mitarbeiter.istFachspezialist()){
            rollen.add(BenutzerRollen.ROLE_ADMIN.toString());
        }
        return rollen.toString();
    }
}
