package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class FehlendeRolleFachspezialistException extends ApiException {
    public FehlendeRolleFachspezialistException(String benutzername) {
        super(erstelleFehlertext(benutzername), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(String benutzername){
        return String.format("Fehlende Berechtigung 'Fachspezialist' für Benutzer %s",
                benutzername);
    }
}
