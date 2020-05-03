package awe.ideeninitiative.model.mitarbeiter;

import awe.ideeninitiative.model.AbstractEntity;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.enums.Zielgruppe;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Set;

@Entity
public class Mitarbeiter extends AbstractEntity {

    @NotNull
    private String benutzername;

    private String vorname;

    private String nachname;

    @NotNull
    private String email;

    @NotNull
    private String passwort;

    private File profilbild;

    private boolean istFachspezialist;

    /**
     * Speichert die Fachkenntnisse über Vertriebskanäle.
     */
    @ElementCollection(targetClass = Vertriebskanal.class)
    @CollectionTable(name = "spezialist_vertriebsweg",
            joinColumns = @JoinColumn(name = "mitarbeiter_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "vertriebsweg")
    private Set<Vertriebskanal> vertriebswege;

    /** Speichert die Fachkenntnisse über Sparten.
     */
    @ElementCollection(targetClass = Sparte.class)
    @CollectionTable(name = "spezialist_sparte",
            joinColumns = @JoinColumn(name = "mitarbeiter_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "sparte")
    private Set<Sparte> sparten;

    /** Speichert die Fachkenntnisse über Zielgruppen.
     */
    @ElementCollection(targetClass = Zielgruppe.class)
    @CollectionTable(name = "spezialist_zielgruppe",
            joinColumns = @JoinColumn(name = "mitarbeiter_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "zielgruppe")
    private Set<Handlungsfeld> zielgruppen;

    /** Speichert die Fachkenntnisse über Handlungsfelder.
     */
    @ElementCollection(targetClass = Handlungsfeld.class)
    @CollectionTable(name = "spezialist_handlungsfeld",
            joinColumns = @JoinColumn(name = "mitarbeiter_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "handlungsfeld")
    private Set<Handlungsfeld> handlungsfelder;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public File getProfilbild() {
        return profilbild;
    }

    public void setProfilbild(File profilbild) {
        this.profilbild = profilbild;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Set<Vertriebskanal> getVertriebswege() {
        return vertriebswege;
    }

    public void setVertriebswege(Set<Vertriebskanal> vertriebswege) {
        this.vertriebswege = vertriebswege;
    }

    public boolean istFachspezialist() {
        return istFachspezialist;
    }

    public void setIstFachspezialist(boolean istFachspezialist) {
        this.istFachspezialist = istFachspezialist;
    }

    public Set<Sparte> getSparten() {
        return sparten;
    }

    public void setSparten(Set<Sparte> sparten) {
        this.sparten = sparten;
    }

    public Set<Handlungsfeld> getZielgruppen() {
        return zielgruppen;
    }

    public void setZielgruppen(Set<Handlungsfeld> zielgruppen) {
        this.zielgruppen = zielgruppen;
    }

    public Set<Handlungsfeld> getHandlungsfelder() {
        return handlungsfelder;
    }

    public void setHandlungsfelder(Set<Handlungsfeld> handlungsfelder) {
        this.handlungsfelder = handlungsfelder;
    }
}
