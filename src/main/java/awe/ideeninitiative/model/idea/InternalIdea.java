package awe.ideeninitiative.model.idea;

import awe.ideeninitiative.model.enums.Fields;

import javax.persistence.Entity;

/**
 * Bildet die Internen Ideen ab.
 */
@Entity
public class InternalIdea extends AbstractIdea{
    private Fields field;

    public Fields getField() {
        return field;
    }

    public void setField(Fields field) {
        this.field = field;
    }
}
