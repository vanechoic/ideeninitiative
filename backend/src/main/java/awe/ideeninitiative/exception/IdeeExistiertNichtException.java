package awe.ideeninitiative.exception;

import org.springframework.http.HttpStatus;

public class IdeeExistiertNichtException extends ApiException{

    private static final String FEHLERTEXT_KEINE_IDEE_FORMAT = "Es konnte keine Idee %s von Benutzer %s gefunden werden.";
    private static final String FEHLERTEXT_NICHT_EINDEUTIGE_IDEE_FORMAT = "Die Idee %s von Benutzer %s konnte nicht eindeutig identifiziert werden. Es wurden %s zutreffende Einträge gefunden.";

    public IdeeExistiertNichtException(String titel, String erfasser, int anzahlZutreffenderIdeen) {
        super(erstelleFehlertext(titel, erfasser, anzahlZutreffenderIdeen), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(String titel, String erfasser, int anzahlZutreffenderIdeen){
        if(anzahlZutreffenderIdeen == 0){
            return String.format(FEHLERTEXT_KEINE_IDEE_FORMAT, titel, erfasser);
        }
        return String.format(FEHLERTEXT_NICHT_EINDEUTIGE_IDEE_FORMAT, titel, erfasser, anzahlZutreffenderIdeen);
    }
}
