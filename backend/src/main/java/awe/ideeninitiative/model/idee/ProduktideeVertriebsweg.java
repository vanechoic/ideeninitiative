package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Vertriebskanal;

import javax.persistence.*;

@Entity
public class ProduktideeVertriebsweg extends AbstractEntity {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idee_id", referencedColumnName = "id")
    private Idee idee;

    @Enumerated(EnumType.STRING)
    private Vertriebskanal vertriebsweg;

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }

    public Vertriebskanal getVertriebsweg() {
        return vertriebsweg;
    }

    public void setVertriebsweg(Vertriebskanal vertriebsweg) {
        this.vertriebsweg = vertriebsweg;
    }
}
