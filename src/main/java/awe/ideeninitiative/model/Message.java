package awe.ideeninitiative.model;

import javax.persistence.Entity;

/**
 * Nachricht, die von allen registrierten und nicht registrierten Benutzern an
 * den Betreiber verfasst werden kann.
 */
@Entity
public class Message extends AbstractEntity{

    private String messageText;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
