package awe.ideeninitiative.exception;

import awe.ideeninitiative.api.model.ApiFehler;
import awe.ideeninitiative.api.model.ApiFehlerBuilder;
import awe.ideeninitiative.controller.BenutzerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class IdeeninitiativeExceptionHandler{

    static final Logger logger = LoggerFactory.getLogger(IdeeninitiativeExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, DataIntegrityViolationException.class, BadCredentialsException.class})
    public ResponseEntity<ApiFehler> handleException(Exception e){
        logger.error("ICH BIN DER EXCEPTION HANDLERR!!");
        return erzeugeApiFehler(e.getClass().getName(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ApiFehler> handlePersistenceException(TransactionSystemException e){
        logger.error("ICH BIN DER EXCEPTION HANDLERR!!");
        return erzeugeApiFehler("ConstraintViolation", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiFehler> handleException(ConstraintViolationException e)  {
        logger.error("ICH BIN DER EXCEPTION HANDLERR!!");
        logger.warn("constraint validation exception!");
        return erzeugeApiFehler("ConstraintViolation", e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiFehler> erzeugeApiFehler(String fehlertyp, String fehlertext, HttpStatus httpStatus){
        ApiFehler apiFehler = new ApiFehler();
        apiFehler.setFehlertext(fehlertext);
        apiFehler.setFehlertyp(fehlertyp);
        apiFehler.setHttpStatusCode(httpStatus.value());
        return new ResponseEntity<>(apiFehler, httpStatus);
    }
}
