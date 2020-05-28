package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class KeinFachspezialistVerfuegbarException extends ApiException{

    public KeinFachspezialistVerfuegbarException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Derzeit steht kein Fachspezialist für die Bewertung der Idee %s zur Verfügung.",
                idee.getTitel());
    }
}
