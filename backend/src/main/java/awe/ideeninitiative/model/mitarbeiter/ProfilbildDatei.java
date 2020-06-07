package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.idee.Idee;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProfilbildDatei extends AbstractEntity {
    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mitarbeiter_id", referencedColumnName = "id")
    private Mitarbeiter mitarbeiter;

    private String dateiname;

    private String dateityp;

    @Lob
    private byte[] dateiinhalt;

    public byte[] getDateiinhalt() {
        return dateiinhalt;
    }

    public void setDateiinhalt(byte[] dateiinhalt) {
        this.dateiinhalt = dateiinhalt;
    }

    public String getDateityp() {
        return dateityp;
    }

    public void setDateityp(String dateityp) {
        this.dateityp = dateityp;
    }

    public String getDateiname() {
        return dateiname;
    }

    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }
}
