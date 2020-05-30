package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class InterneIdeeOhneHandlungsfeldException extends ApiException {
    public InterneIdeeOhneHandlungsfeldException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Fehlendes Handlungsfeld für interne Idee %s.",
                idee.getTitel());
    }
}
