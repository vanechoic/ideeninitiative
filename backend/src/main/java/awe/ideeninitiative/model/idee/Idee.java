package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@Entity
public class Idee extends AbstractEntity {

    @NotNull
    private String bezeichnung;

    private String beschreibung;

    private String begruendung;

    @NotNull
    private Ideenstatus bearbeitungsstatus;

    @NotNull
    private Ideentyp typ;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="erfasser_id", referencedColumnName = "id")
    private Mitarbeiter erfasser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fachspezialist_id", referencedColumnName = "id")
    private Mitarbeiter fachspezialist;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Mitarbeiter getErfasser() {
        return erfasser;
    }

    public Ideenstatus getBearbeitungsstatus() {
        return bearbeitungsstatus;
    }

    public void setBearbeitungsstatus(Ideenstatus bearbeitungsstatus) {
        this.bearbeitungsstatus = bearbeitungsstatus;
    }

    public String getBegruendung() {
        return begruendung;
    }

    public void setBegruendung(String begruendung) {
        this.begruendung = begruendung;
    }

    public void setErfasser(Mitarbeiter erfasser) {
        this.erfasser = erfasser;
    }

    public Mitarbeiter getFachspezialist() {
        return fachspezialist;
    }

    public void setFachspezialist(Mitarbeiter fachspezialist) {
        this.fachspezialist = fachspezialist;
    }

    public Ideentyp getTyp() {
        return typ;
    }

    public void setTyp(Ideentyp typ) {
        this.typ = typ;
    }
}
