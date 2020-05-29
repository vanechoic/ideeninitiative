package awe.ideeninitiative.exception;

import awe.ideeninitiative.api.model.ApiFehler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.List;


@ControllerAdvice
public class IdeeninitiativeExceptionHandler{

    static final Logger logger = LoggerFactory.getLogger(IdeeninitiativeExceptionHandler.class);

    /** Über die API übergebene Werte stimmen nicht mit dem Pattern überein. Sie openapi3-spec.yml
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiFehler> handleException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String fehlertext = "";
        //Für jedes fehlerhafte Feld, erweitere den Fehlertext:
        for (FieldError fehler: fieldErrors) {
            //Ermittle das erwartete Regex-Muster aus den gegebenen Argumenten:
            Object[] argumente = fehler.getArguments();
            String erwartetesMuster = (argumente != null && argumente.length >= 3) ? argumente[2].toString() : "<unbekannt>";
            //Füge den neuen Satz hinzu:
            fehlertext += String.format("Der Wert %s entspricht nicht dem erwarteten Muster %s für das Feld %s. ", fehler.getRejectedValue().toString(), erwartetesMuster, fehler.getField());
        }
        return erzeugeApiFehler("MethodArgumentNotValid", fehlertext, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({KeineBefugnisFuerIdeeAenderungenException.class})
    public ResponseEntity<ApiFehler> handleException(KeineBefugnisFuerIdeeAenderungenException e){
        return erzeugeApiFehler(e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus());
    }


    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiFehler> handleException(BadCredentialsException e){
        return erzeugeApiFehler("BadCredentials", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** Datenbank-Constraints werden aktiv, z.B. nicht eindeutiger Benutzername oder E-Mail. Sie Entity-Klassen.
     * @param e
     * @return
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiFehler> handleException(DataIntegrityViolationException e){
        boolean istEindeutigerBenutzernameConstraint = e.getMessage().contains("eindeutigerBenutzername");
        String fehlertext = String.format("%s existiert bereits. Bitte wählen Sie eine%s", istEindeutigerBenutzernameConstraint ? "Der Benutzername" : "Die E-Mail-Adresse", istEindeutigerBenutzernameConstraint ? "n anderen Benutzernamen." : " andere E-Mail-Adresse.");
        return erzeugeApiFehler("DataIntegrityViolationException", fehlertext, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ApiFehler> handlePersistenceException(TransactionSystemException e){
        return erzeugeApiFehler("TransactionSystemException", e.getCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiFehler> handleException(ConstraintViolationException e)  {
        return erzeugeApiFehler("ConstraintViolation", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiFehler> handleException(AuthenticationException e)  {
        return erzeugeApiFehler("AuthenticationException", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiFehler> handleException(InsufficientAuthenticationException e)  {
        return erzeugeApiFehler("InsufficientAuthenticationException", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiFehler> handlePersistenceException(ApiException e){
        return erzeugeApiFehler(e.getClass().getSimpleName(), e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<ApiFehler> handleException(DateTimeParseException e) {
        return erzeugeApiFehler("DatumFormatierung", e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ApiFehler> erzeugeApiFehler(String fehlertyp, String fehlertext, HttpStatus httpStatus){
        ApiFehler apiFehler = new ApiFehler();
        apiFehler.setFehlertext(fehlertext);
        apiFehler.setFehlertyp(fehlertyp);
        apiFehler.setHttpStatusCode(httpStatus.value());
        return new ResponseEntity<>(apiFehler, httpStatus);
    }
}
