package awe.ideeninitiative.exception;

public class IdeeninitiativeException extends Exception {
    private String fehlertext;

    public IdeeninitiativeException(String fehlertext){
        this.fehlertext = fehlertext;
    }

    @Override
    public String getMessage() {
        return fehlertext;
    }

    public void setFehlertext(String fehlertext) {
        this.fehlertext = fehlertext;
    }
}
