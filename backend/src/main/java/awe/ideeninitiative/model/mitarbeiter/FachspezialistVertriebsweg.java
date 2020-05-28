package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;

import javax.persistence.*;

@Entity
public class FachspezialistVertriebsweg extends AbstractEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="mitarbeiter_id", referencedColumnName = "id")
    private Mitarbeiter mitarbeiter;

    @Enumerated(EnumType.STRING)
    private Vertriebskanal vertriebsweg;

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Vertriebskanal getVertriebsweg() {
        return vertriebsweg;
    }

    public void setVertriebsweg(Vertriebskanal vertriebsweg) {
        this.vertriebsweg = vertriebsweg;
    }
}
