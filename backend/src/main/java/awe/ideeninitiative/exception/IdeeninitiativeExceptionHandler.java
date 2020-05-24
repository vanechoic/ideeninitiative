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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class IdeeninitiativeExceptionHandler{

    static final Logger logger = LoggerFactory.getLogger(IdeeninitiativeExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, BadCredentialsException.class})
    public ResponseEntity<ApiFehler> handleException(Exception e){
        logger.error("ICH BIN DER EXCEPTION HANDLERR!!");
        return erzeugeApiFehler(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiFehler> DataIntegrityViolationException(DataIntegrityViolationException e){
        boolean istEindeutigerBenutzernameConstraint = e.getMessage().contains("eindeutigerBenutzername");
        String fehlertext = String.format("%s existiert bereits. Bitte wählen Sie eine%s", istEindeutigerBenutzernameConstraint ? "Der Benutzername" : "Die E-Mail-Adresse", istEindeutigerBenutzernameConstraint ? "n anderen Benutzernamen." : " andere E-Mail-Adresse.");
        return erzeugeApiFehler("DataIntegrityViolationException", fehlertext, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ApiFehler> handlePersistenceException(TransactionSystemException e){
        logger.error("TransactionSystemException");
        logger.error(e.getMessage(), e.getMostSpecificCause().getMessage(), e.getCause().getMessage());
        return erzeugeApiFehler("TransactionSystemException", e.getCause().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiFehler> handleException(ConstraintViolationException e)  {
        logger.warn("constraint validation exception!");
        return erzeugeApiFehler("ConstraintViolation", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiFehler> handleException(AuthenticationException e)  {
        logger.warn("AccessDeniedException");
        return erzeugeApiFehler("AuthenticationException", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiFehler> handleException(InsufficientAuthenticationException e)  {
        logger.warn("InsufficientAuthenticationException");
        return erzeugeApiFehler("InsufficientAuthenticationException", e.getMessage(), HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<ApiFehler> erzeugeApiFehler(String fehlertyp, String fehlertext, HttpStatus httpStatus){
        ApiFehler apiFehler = new ApiFehler();
        apiFehler.setFehlertext(fehlertext);
        apiFehler.setFehlertyp(fehlertyp);
        apiFehler.setHttpStatusCode(httpStatus.value());
        return new ResponseEntity<>(apiFehler, httpStatus);
    }
}
