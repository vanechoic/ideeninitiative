package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.File;

@Entity
public class Dokument extends AbstractEntity {

    @NotNull
    private String dateiname;

    @NotNull
    private File datei;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="idee_id", referencedColumnName = "id")
    private Idee idee;

    public String getDateiname() {
        return dateiname;
    }

    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public File getDatei() {
        return datei;
    }

    public void setDatei(File datei) {
        this.datei = datei;
    }

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }
}
