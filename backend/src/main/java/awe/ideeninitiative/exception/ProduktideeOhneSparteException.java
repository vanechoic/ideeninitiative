package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class ProduktideeOhneSparteException extends ApiException {
    public ProduktideeOhneSparteException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Fehlende Sparte für Produktidee %s.",
                idee.getTitel());
    }
}
