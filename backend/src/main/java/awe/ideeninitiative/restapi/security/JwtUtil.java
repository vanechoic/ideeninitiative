package awe.ideeninitiative.restapi.security;

import awe.ideeninitiative.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Hilfsklasse für die Erstellung und Prüfung von JWTs. Diese Klasse wurde
 * nach dem Beispiel eines DZone-Artikels von Rida Shaikh geschrieben (vgl. author). Zur Vereinfachung des Codes wurden
 * Methoden übersetzt, zusammengefasst oder umstrukturiert.
 * @author Rida Shaikh, https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world, Vanessa Haubrok
 */
@Component
public class JwtUtil implements Serializable {
    public static final long JWT_ABLAUFDAUER = 360*5;

    @Value("${jwt.secret}")
    private String secret;


    public String extrahiereBenutzernamenAusToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date extrahiereAblaufzeitpunktAusToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    /**
     * Prüft, ob der Ablaufzeitpunkts des Token vor dem jetzigen Zeitpunkt liegt.
     * @param token
     * @return Wahrheitswert
     */
    private Boolean istTokenAbgelaufen(String token) {
        final Date ablaufzeitpunkt = extrahiereAblaufzeitpunktAusToken(token);
        return ablaufzeitpunkt.before(new Date());
    }


    /**
     * Generiert einen neuen JWT aus den gegebenen Benutzerinformationen.
     * @param userDetails
     * @return Generierter Token
     */
    public String generiereToken(UserDetails userDetails) {
        List<String> rollen = gibRollenAlsStringListeZurueck(userDetails);
        return generiereEinzelnenToken(rollen, userDetails.getUsername());
    }

    /**
     * Formt die Liste der GrantedAuthorities der UserDetails in eine String-Liste um.
     * @param userDetails
     * @return Liste der Benutzerrollen als String
     */
    private List<String> gibRollenAlsStringListeZurueck(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    /**
     *  Generiert einen neuen JWT für den gegebenen Benutzer mit seinen Rollen. Dabei verwendet wird der definierte Verschlüsselungsalgorithmus,
     *  sowie der konfigurierte secret key. Gesetzt werden das Subject, das Ablaufdatum, das Erstelldatum und die Rollen.
     * @param rollen
     * @param benutzername
     * @return Generierter JWT
     */
    public String generiereEinzelnenToken(List<String> rollen, String benutzername) {
        return generiereEinzelnenTokenMitAblaufzeitpunkt(rollen, benutzername, new Date(System.currentTimeMillis() + JWT_ABLAUFDAUER * 1000));
    }

    /**
     *  Generiert einen neuen JWT für den gegebenen Benutzer mit seinen Rollen. Dabei verwendet wird der definierte Verschlüsselungsalgorithmus,
     *  sowie der konfigurierte secret key. Gesetzt werden das Subject, das Ablaufdatum, das Erstelldatum und die Rollen.
     * @param rollen
     * @param benutzername
     * @param ablaufzeitpunkt
     * @return Generierter JWT
     */
    protected String generiereEinzelnenTokenMitAblaufzeitpunkt(List<String> rollen, String benutzername, Date ablaufzeitpunkt) {
        return Jwts.builder()
                .setSubject(benutzername)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(ablaufzeitpunkt)
                .claim("rollen", rollen)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Prüft, dass der Benutzername aus dem Token mit dem durch die DB ermittelten übereinstimmt und noch nicht abgelaufen ist.
     * @param token
     * @param userDetails
     * @return Wahrheitswert
     */
    public Boolean istTokenValide(String token, UserDetails userDetails) {
        final String username = extrahiereBenutzernamenAusToken(token);
        return (username.equals(userDetails.getUsername()) && !istTokenAbgelaufen(token));
    }

    /**
     * Entfernt das "Bearer " aus dem Wert des AuthorizationHeaders und gibt nur den Token zurück.
     * @param authorizationHeader im Format "Bearer jwt"
     * @return jwt
     * @throws ApiException
     */
    public String extrahiereJwtAusAuthorizationHeader(String authorizationHeader) throws ApiException {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new ApiException("Fehlender AuthorizationHeader.", HttpStatus.UNAUTHORIZED);

    }

    /**
     * Liest das Subject, hier den Benutzernamen, aus dem AuthorizationHeader aus.
     * @param authorizationHeader im Format "Bearer jwt"
     * @return benutzername (sub)
     */
    public String extrahiereBenutzernamenAusAuthorizationHeader(String authorizationHeader) throws ApiException {
        return extrahiereBenutzernamenAusToken(extrahiereJwtAusAuthorizationHeader(authorizationHeader));
    }


}
