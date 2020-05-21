package awe.ideeninitiative.security;

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
        Optional<Mitarbeiter> mitarbeiter = mitarbeiterRepository.findFirstByBenutzername(username);
        pruefeObRegistrierterMitarbeiterExistiert(username, mitarbeiter);
        return formeMitarbeiterZuUserDetailsUm(mitarbeiter.get());
    }

    /**
     * Stellt sicher, dass ein Mitarbeiter zum gegebenen Benutzernamen gefunden wurde. Wurde kein Mitarbeiter gefunden,
     * wird eine UsernameNotFoundException geworfen.
     * @param benutzername
     * @param mitarbeiter
     */
    private void pruefeObRegistrierterMitarbeiterExistiert(String benutzername, Optional<Mitarbeiter> mitarbeiter) throws UsernameNotFoundException{
        if(mitarbeiter == null || mitarbeiter.isEmpty()){
            throw new UsernameNotFoundException(String.format("Für den Benutzernamen {0} existiert kein Konto.", benutzername));
        }
    }

    /**
     * Erstellt UserDetails aus den Mitarbeiterdaten.
     * @param mitarbeiter
     * @return Mitarbeiter als UserDetails
     */
    private UserDetails formeMitarbeiterZuUserDetailsUm(Mitarbeiter mitarbeiter) {
        return User.withUsername(mitarbeiter.getBenutzername())
                .password(mitarbeiter.getPasswort())
                .roles(mitarbeiter.ermittleBenutzerrollenAlsString()).build();
    }

}
