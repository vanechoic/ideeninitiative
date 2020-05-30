package awe.ideeninitiative.exception;

import io.swagger.models.apideclaration.Api;
import org.springframework.http.HttpStatus;

public class MitarbeiterExistiertNichtException extends ApiException {
    public MitarbeiterExistiertNichtException(String benutzername) {
        super(erstelleFehlertext(benutzername), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(String benutzername){
        return String.format("Registrierter Mitarbeiter mit Benutzernamen %s existiert nicht.",
                benutzername);
    }
}
