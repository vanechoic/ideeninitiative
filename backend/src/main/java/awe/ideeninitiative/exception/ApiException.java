package awe.ideeninitiative.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends  IdeeninitiativeException{

    private HttpStatus httpStatus;

    public ApiException(String fehlertext, HttpStatus httpStatus) {
        super(fehlertext);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
