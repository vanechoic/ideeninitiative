package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Zielgruppe;

import javax.persistence.*;

@Entity
public class FachspezialistZielgruppe extends AbstractEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="mitarbeiter_id", referencedColumnName = "id")
    private Mitarbeiter mitarbeiter;

    @Enumerated(EnumType.STRING)
    private Zielgruppe zielgruppe;

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Zielgruppe getZielgruppe() {
        return zielgruppe;
    }

    public void setZielgruppe(Zielgruppe zielgruppe) {
        this.zielgruppe = zielgruppe;
    }
}
