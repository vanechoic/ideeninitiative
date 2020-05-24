package awe.ideeninitiative.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Nachricht, die von allen registrierten und nicht registrierten Benutzern an
 * den Betreiber verfasst werden kann.
 */
@Entity
public class Nachricht extends AbstractEntity{

    @NotNull
    @Pattern(regexp = "[^\\^°=*#~;:(){}§$%<>|'`´\\/\\\\]*")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
