package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@Entity
public class Idee extends AbstractEntity {

    @NotNull
    @Pattern(regexp = "\\w+[\\s\\w]*")
    private String titel;

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

    @OneToOne(mappedBy = "idee")
    private InterneIdeeHandlungsfeld interneIdeeHandlungsfeld;

    @OneToOne(mappedBy = "idee")
    private ProduktideeSparte produktideeSparte;

    @OneToMany(mappedBy = "idee")
    private List<ProduktideeVertriebsweg> produktideeVertriebsweg;

    @OneToMany(mappedBy = "idee")
    private List<ProduktideeZielgruppe> produktideeZielgruppe;

    @OneToOne(mappedBy = "idee")
    private ProduktideeZusatzinformation produktideeZusatzinformation;

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
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

    public InterneIdeeHandlungsfeld getInterneIdeeHandlungsfeld() {
        return interneIdeeHandlungsfeld;
    }

    public void setInterneIdeeHandlungsfeld(InterneIdeeHandlungsfeld interneIdeeHandlungsfeld) {
        this.interneIdeeHandlungsfeld = interneIdeeHandlungsfeld;
    }

    public ProduktideeSparte getProduktideeSparte() {
        return produktideeSparte;
    }

    public void setProduktideeSparte(ProduktideeSparte produktideeSparte) {
        this.produktideeSparte = produktideeSparte;
    }

    public ProduktideeZusatzinformation getProduktideeZusatzinformation() {
        return produktideeZusatzinformation;
    }

    public void setProduktideeZusatzinformation(ProduktideeZusatzinformation produktideeZusatzinformation) {
        this.produktideeZusatzinformation = produktideeZusatzinformation;
    }

    public List<ProduktideeVertriebsweg> getProduktideeVertriebsweg() {
        return produktideeVertriebsweg;
    }

    public void setProduktideeVertriebsweg(List<ProduktideeVertriebsweg> produktideeVertriebsweg) {
        this.produktideeVertriebsweg = produktideeVertriebsweg;
    }

    public List<ProduktideeZielgruppe> getProduktideeZielgruppe() {
        return produktideeZielgruppe;
    }

    public void setProduktideeZielgruppe(List<ProduktideeZielgruppe> produktideeZielgruppe) {
        this.produktideeZielgruppe = produktideeZielgruppe;
    }
}
