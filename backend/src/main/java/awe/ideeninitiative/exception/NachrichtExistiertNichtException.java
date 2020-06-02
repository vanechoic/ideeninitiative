package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.Nachricht;
import org.springframework.http.HttpStatus;

public class NachrichtExistiertNichtException extends ApiException {
    public NachrichtExistiertNichtException(Nachricht nachricht) {
        super(erstelleFehlertext(nachricht), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Nachricht nachricht){
        return String.format("Nachricht mit Titel '%s' existiert nicht.",
                nachricht.getTitel());
    }
}
