package awe.ideeninitiative.model;

import javax.persistence.Entity;

/**
 * Nachricht, die von allen registrierten und nicht registrierten Benutzern an
 * den Betreiber verfasst werden kann.
 */
@Entity
public class Nachricht extends AbstractEntity{

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
