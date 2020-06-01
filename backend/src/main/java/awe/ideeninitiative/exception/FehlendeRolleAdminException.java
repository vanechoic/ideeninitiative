package awe.ideeninitiative.exception;

import org.springframework.http.HttpStatus;

public class FehlendeRolleAdminException extends ApiException {
    public FehlendeRolleAdminException(String benutzername) {
        super(erstelleFehlertext(benutzername), HttpStatus.UNAUTHORIZED);
    }

    private static String erstelleFehlertext(String benutzername){
        return String.format("Fehlende Berechtigung 'Admin' für Benutzer %s",
                benutzername);
    }
}
