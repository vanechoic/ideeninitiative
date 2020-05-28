package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Bildet die Internen Ideen ab.
 */
@Entity
public class InterneIdeeHandlungsfeld extends AbstractEntity {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idee_id", referencedColumnName = "id")
    private Idee idee;

    @Enumerated(EnumType.STRING)
    private Handlungsfeld handlungsfeld;

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }

    public Handlungsfeld getHandlungsfeld() {
        return handlungsfeld;
    }

    public void setHandlungsfeld(Handlungsfeld handlungsfeld) {
        this.handlungsfeld = handlungsfeld;
    }
}
