package awe.ideeninitiative.restapi.security;

import awe.ideeninitiative.exception.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
            try {
                String token = jwtUtil.extrahiereJwtAusAuthorizationHeader(requestTokenHeader);
                String benutzername = jwtUtil.extrahiereBenutzernamenAusToken(token);
                if (benutzername != null && !istAuthentifikationsKontextGesetzt()) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(benutzername);
                    if (jwtUtil.istTokenValide(token, userDetails)) {
                        authentifiziereAktuellenBenutzer(request, userDetails);
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
            } catch(ExpiredJwtException e){
                e.printStackTrace();
            }
        filterChain.doFilter(request, response);
    }

    private boolean istAuthentifikationsKontextGesetzt() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private void authentifiziereAktuellenBenutzer(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
