package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class ProduktideeOhneZielgruppeException extends ApiException {
    public ProduktideeOhneZielgruppeException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Fehlende Zielgruppe für Produktidee %s.",
                idee.getTitel());
    }
}
