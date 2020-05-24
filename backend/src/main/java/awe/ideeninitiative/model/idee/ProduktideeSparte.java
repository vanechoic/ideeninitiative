package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Sparte;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProduktideeSparte extends AbstractEntity {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idee_id", referencedColumnName = "id")
    private Idee idee;

    @Enumerated(EnumType.STRING)
    private Sparte sparte;

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }

    public Sparte getSparte() {
        return sparte;
    }

    public void setSparte(Sparte sparte) {
        this.sparte = sparte;
    }
}
