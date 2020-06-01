package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class IdeeExistiertBereitsException extends ApiException {
    public IdeeExistiertBereitsException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.UNAUTHORIZED);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Idee '%s' von Benutzer %s existiert bereits. Bitte wählen Sie einen anderen Titel.",
                idee.getTitel(), idee.getErfasser().getBenutzername());
    }
}
