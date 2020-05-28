package awe.ideeninitiative.exception;

import awe.ideeninitiative.model.idee.Idee;
import org.springframework.http.HttpStatus;

public class IdeeBereitsVeroeffentlichtException extends ApiException{
    public IdeeBereitsVeroeffentlichtException(Idee idee) {
        super(erstelleFehlertext(idee), HttpStatus.BAD_REQUEST);
    }

    private static String erstelleFehlertext(Idee idee){
        return String.format("Die Idee %s von Benutzer %s wurde bereits veröffentlicht.",
                idee.getTitel(), idee.getErfasser().getBenutzername());
    }
}
