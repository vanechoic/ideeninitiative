package awe.ideeninitiative.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Nachricht, die von allen registrierten und nicht registrierten Benutzern an
 * den Betreiber verfasst werden kann.
 */
@Entity
public class Nachricht extends AbstractEntity{

    @NotNull
    @Pattern(regexp = "[^\\^°=*#~;(){}§$%<>|'`´\\/\\\\]*")
    private String titel;

    @NotNull
    @Pattern(regexp = "[^\\^°=*#~;(){}§$%<>|'`´\\/\\\\]*")
    @Lob
    private String beschreibung;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
