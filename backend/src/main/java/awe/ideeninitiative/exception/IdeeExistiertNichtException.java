package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class IdeeExistiertNichtException extends ApiException{

    private static final String FEHLERTEXT_KEINE_IDEE_FORMAT = "Es konnte keine Idee %s von Benutzer %s gefunden werden.";
    private static final String FEHLERTEXT_NICHT_EINDEUTIGE_IDEE_FORMAT = "Die Idee %s von Benutzer %s konnte nicht eindeutig identifiziert werden. Es wurden %s zutreffende Einträge gefunden.";

    public IdeeExistiertNichtException(Idee idee, int anzahlZutreffenderIdeen) {
        super(erstelleFehlertext(idee, anzahlZutreffenderIdeen), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(Idee idee, int anzahlZutreffenderIdeen){
        if(anzahlZutreffenderIdeen == 0){
            return String.format(FEHLERTEXT_KEINE_IDEE_FORMAT, idee.getTitel(), idee.getErfasser().getBenutzername());
        }
        return String.format(FEHLERTEXT_NICHT_EINDEUTIGE_IDEE_FORMAT, idee.getTitel(), idee.getErfasser().getBenutzername(), anzahlZutreffenderIdeen);
    }
}
