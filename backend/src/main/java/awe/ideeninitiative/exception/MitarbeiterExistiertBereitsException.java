package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import org.springframework.http.HttpStatus;

public class MitarbeiterExistiertBereitsException extends ApiException{

    private static final String FEHLERTEXT_GLEICHER_BENUTZERNAME = "Der Benutzername ist bereits vergeben.";
    private static final String FEHLERTEXT_GLEICHE_EMAIL= "Die E-Mail-Adresse ist bereits vergeben.";
    private static final String FEHLERTEXT_BEIDES_GLEICH = "Der Benutzername und die E-Mail-Adresse sind bereits vergeben.";

    public MitarbeiterExistiertBereitsException(boolean gleicherBenutzername, boolean gleicheEmail) {
        super(erstelleFehlertext(gleicherBenutzername, gleicheEmail), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(boolean gleicherBenutzername, boolean gleicheEmail){
        if(gleicherBenutzername && !gleicheEmail){
            return FEHLERTEXT_GLEICHER_BENUTZERNAME;
        } else if (!gleicherBenutzername && gleicheEmail){
            return FEHLERTEXT_GLEICHE_EMAIL;
        } else if (gleicherBenutzername && gleicheEmail){
           return FEHLERTEXT_BEIDES_GLEICH;
        }
        return "Upps.";
    }
}
