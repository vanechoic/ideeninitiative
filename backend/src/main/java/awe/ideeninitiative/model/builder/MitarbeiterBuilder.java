package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.enums.Handlungsfeld;

import java.io.File;
import java.util.Set;

public final class MitarbeiterBuilder {
    private String benutzername;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private File profilbild;
    private boolean istFachspezialist;
    private Set<Vertriebskanal> vertriebswege;
    private Set<Sparte> sparten;
    private Set<Handlungsfeld> zielgruppen;
    private Set<Handlungsfeld> handlungsfelder;

    private MitarbeiterBuilder() {
    }

    public static MitarbeiterBuilder aMitarbeiter() {
        return new MitarbeiterBuilder();
    }

    public MitarbeiterBuilder withBenutzername(String benutzername) {
        this.benutzername = benutzername;
        return this;
    }

    public MitarbeiterBuilder withVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public MitarbeiterBuilder withNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public MitarbeiterBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public MitarbeiterBuilder withPasswort(String passwort) {
        this.passwort = passwort;
        return this;
    }

    public MitarbeiterBuilder withProfilbild(File profilbild) {
        this.profilbild = profilbild;
        return this;
    }

    public MitarbeiterBuilder withIstFachspezialist(boolean istFachspezialist) {
        this.istFachspezialist = istFachspezialist;
        return this;
    }

    public MitarbeiterBuilder withVertriebswege(Set<Vertriebskanal> vertriebswege) {
        this.vertriebswege = vertriebswege;
        return this;
    }

    public MitarbeiterBuilder withSparten(Set<Sparte> sparten) {
        this.sparten = sparten;
        return this;
    }

    public MitarbeiterBuilder withZielgruppen(Set<Handlungsfeld> zielgruppen) {
        this.zielgruppen = zielgruppen;
        return this;
    }

    public MitarbeiterBuilder withHandlungsfelder(Set<Handlungsfeld> handlungsfelder) {
        this.handlungsfelder = handlungsfelder;
        return this;
    }

    public Mitarbeiter build() {
        Mitarbeiter mitarbeiter = new Mitarbeiter();
        mitarbeiter.setBenutzername(benutzername);
        mitarbeiter.setVorname(vorname);
        mitarbeiter.setNachname(nachname);
        mitarbeiter.setEmail(email);
        mitarbeiter.setPasswort(passwort);
        mitarbeiter.setProfilbild(profilbild);
        mitarbeiter.setIstFachspezialist(istFachspezialist);
        mitarbeiter.setVertriebswege(vertriebswege);
        mitarbeiter.setSparten(sparten);
        mitarbeiter.setZielgruppen(zielgruppen);
        mitarbeiter.setHandlungsfelder(handlungsfelder);
        return mitarbeiter;
    }
}
