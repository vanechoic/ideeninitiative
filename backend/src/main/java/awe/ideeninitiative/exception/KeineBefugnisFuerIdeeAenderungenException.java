package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class KeineBefugnisFuerIdeeAenderungenException extends ApiException{
    public KeineBefugnisFuerIdeeAenderungenException(Idee idee, String benutzername) {
        super(erstelleFehlertext(idee, benutzername), HttpStatus.UNAUTHORIZED);
    }

    private static String erstelleFehlertext(Idee idee, String benutzername){
        return String.format("Modifizierung der Idee %s durch Benutzer %s verweigert. Es können nur eigene Ideen gelöscht oder veröffentlicht werden.",
                idee.getTitel(), benutzername);
    }
}
