package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Beinhaltet die gemeinsamen Attribute und Funktionen der Produktideen und internen Ideen.
 */
@Entity
public class Idee extends AbstractEntity {

    @NotNull
    @Pattern(regexp = "[^\\^°=*#~;:(){}§$%<>|'`´\\/\\\\]*")
    private String titel;

    @Pattern(regexp = "[^\\^°=*#~;:(){}§$%<>|'`´\\/\\\\]*")
    private String beschreibung;

    @Pattern(regexp = "[^\\^°=*#~;:(){}§$%<>|'`´\\/\\\\]*")
    private String begruendung;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Ideenstatus bearbeitungsstatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Ideentyp typ;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="erfasser_id", referencedColumnName = "id")
    private Mitarbeiter erfasser;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fachspezialist_id", referencedColumnName = "id")
    private Mitarbeiter fachspezialist;

    @OneToOne(mappedBy = "idee", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private InterneIdeeHandlungsfeld interneIdeeHandlungsfeld;

    @OneToOne(mappedBy = "idee", cascade=CascadeType.ALL, orphanRemoval = true)
    private ProduktideeSparte produktideeSparte;

    @OneToMany(mappedBy = "idee", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<ProduktideeVertriebsweg> produktideeVertriebsweg;

    @OneToMany(mappedBy = "idee",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<ProduktideeZielgruppe> produktideeZielgruppe;

    @OneToOne(mappedBy = "idee", cascade=CascadeType.ALL, orphanRemoval = true)
    private ProduktideeZusatzinformation produktideeZusatzinformation;

    @OneToMany(mappedBy = "idee", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Vorteil> vorteile;

    public Idee() {
        produktideeZielgruppe = new ArrayList<>();
        produktideeVertriebsweg = new ArrayList<>();
    }

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
        if(interneIdeeHandlungsfeld != null){
            interneIdeeHandlungsfeld.setIdee(this);
        }
        this.interneIdeeHandlungsfeld = interneIdeeHandlungsfeld;
    }

    public ProduktideeSparte getProduktideeSparte() {
        return produktideeSparte;
    }

    public void setProduktideeSparte(ProduktideeSparte produktideeSparte) {
        if(produktideeSparte != null){
            produktideeSparte.setIdee(this);
        }
        this.produktideeSparte = produktideeSparte;
    }

    public ProduktideeZusatzinformation getProduktideeZusatzinformation() {
        return produktideeZusatzinformation;
    }

    public void setProduktideeZusatzinformation(ProduktideeZusatzinformation produktideeZusatzinformation) {
        if(produktideeZusatzinformation != null){
            produktideeZusatzinformation.setIdee(this);
        }
        this.produktideeZusatzinformation = produktideeZusatzinformation;
    }

    public List<ProduktideeVertriebsweg> getProduktideeVertriebsweg() {
        return produktideeVertriebsweg;
    }

    public List<Vertriebskanal> getProduktideeVertriebswegeWerte(){
        return produktideeVertriebsweg.stream()
                .map( vw-> vw.getVertriebsweg())
                .collect(Collectors.toList());
    }

    public void setProduktideeVertriebsweg(List<ProduktideeVertriebsweg> produktideeVertriebsweg) {
        if(produktideeVertriebsweg != null && !produktideeVertriebsweg.isEmpty()){
            produktideeVertriebsweg.forEach(vw -> vw.setIdee(this));
            this.produktideeVertriebsweg.clear();
            this.produktideeVertriebsweg.addAll(produktideeVertriebsweg);
        }
    }

    public List<ProduktideeZielgruppe> getProduktideeZielgruppe() {
        return produktideeZielgruppe;
    }

    public List<Zielgruppe> getProduktideeZielgruppeWerte(){
        return produktideeZielgruppe.stream()
                .map( zg-> zg.getZielgruppe())
                .collect(Collectors.toList());
    }

    public void setProduktideeZielgruppe(List<ProduktideeZielgruppe> produktideeZielgruppe) {
        if(produktideeZielgruppe != null && !produktideeZielgruppe.isEmpty()){
            produktideeZielgruppe.forEach(zg -> zg.setIdee(this));
            this.produktideeZielgruppe.clear();
            this.produktideeZielgruppe.addAll(produktideeZielgruppe);
        }
    }

    public List<Vorteil> getVorteile() {
        return vorteile;
    }

    public List<String> getVorteileWerte(){
        return vorteile.stream()
                .map(v -> v.getBeschreibung())
                .collect(Collectors.toList());
    }

    public void setVorteile(List<Vorteil> vorteile) {
        this.vorteile = vorteile;
    }
}
