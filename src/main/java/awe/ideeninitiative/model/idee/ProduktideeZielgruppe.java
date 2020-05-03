package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Zielgruppe;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProduktideeZielgruppe extends AbstractEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="idee_id", referencedColumnName = "id")
    private Idee idee;

    private Zielgruppe zielgruppe;

    public Zielgruppe getZielgruppe() {
        return zielgruppe;
    }

    public void setZielgruppe(Zielgruppe zielgruppe) {
        this.zielgruppe = zielgruppe;
    }

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }
}
