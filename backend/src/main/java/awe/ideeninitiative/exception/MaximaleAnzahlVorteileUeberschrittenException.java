package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class MaximaleAnzahlVorteileUeberschrittenException extends ApiException {
    public MaximaleAnzahlVorteileUeberschrittenException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Es können nur bis zu drei Vorteile für Idee %s gespeichert werden.",
                idee.getTitel());
    }
}
