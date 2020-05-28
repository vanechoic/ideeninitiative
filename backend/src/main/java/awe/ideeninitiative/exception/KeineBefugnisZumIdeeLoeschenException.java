package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class KeineBefugnisZumIdeeLoeschenException extends ApiException{
    public KeineBefugnisZumIdeeLoeschenException(Idee idee, String benutzername) {
        super(erstelleFehlertext(idee, benutzername), HttpStatus.UNAUTHORIZED);
    }

    private static String erstelleFehlertext(Idee idee, String benutzername){
        return String.format("Löschen der Idee %s durch Benutzer %s verweigert. Es können nur eigene Ideen gelöscht werden.",
                idee.getTitel(), benutzername);
    }
}
