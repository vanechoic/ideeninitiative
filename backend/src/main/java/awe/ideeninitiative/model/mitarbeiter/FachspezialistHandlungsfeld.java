package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Sparte;

import javax.persistence.*;

@Entity
public class FachspezialistHandlungsfeld extends AbstractEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="mitarbeiter_id", referencedColumnName = "id")
    private Mitarbeiter mitarbeiter;

    @Enumerated(EnumType.STRING)
    private Handlungsfeld handlungsfeld;

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Handlungsfeld getHandlungsfeld() {
        return handlungsfeld;
    }

    public void setHandlungsfeld(Handlungsfeld handlungsfeld) {
        this.handlungsfeld = handlungsfeld;
    }
}
