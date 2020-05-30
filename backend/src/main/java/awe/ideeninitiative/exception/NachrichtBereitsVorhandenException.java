package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.Nachricht;
import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class NachrichtBereitsVorhandenException extends ApiException {
    public NachrichtBereitsVorhandenException(Nachricht nachricht) {
        super(erstelleFehlertext(nachricht), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Nachricht nachricht){
        return String.format("Nachricht mit diesem Titel ist bereits vorhanden %s.",
                nachricht.getTitel());
    }
}
