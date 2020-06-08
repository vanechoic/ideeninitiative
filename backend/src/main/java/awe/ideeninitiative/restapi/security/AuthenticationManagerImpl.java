package awe.ideeninitiative.restapi.security;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.repositories.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationManagerImpl implements AuthenticationProvider {
    @Autowired
    private MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @author https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<Mitarbeiter> user = mitarbeiterRepository.findFirstByBenutzername(username);
        if (user == null || user.isEmpty()) {
            throw new BadCredentialsException("1000");
        }
        if (!bCryptPasswordEncoder.matches(password, user.get().getPasswort())) {
            throw new BadCredentialsException("1000");
        }
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
