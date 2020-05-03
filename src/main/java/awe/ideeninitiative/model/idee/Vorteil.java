package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vorteil extends AbstractEntity {
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="idee_id", referencedColumnName = "id")
    private Idee idee;

    private String beschreibung;

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}