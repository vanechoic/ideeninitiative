package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public final class MitarbeiterBuilder {
    private String benutzername;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private File profilbild;
    private boolean istFachspezialist;
    private boolean istAdmin;
    private List<Idee> erstellteIdeen;
    private List<Idee> zugewieseneIdeen;
    private List<FachspezialistVertriebsweg> fachspezialistVertriebswege;
    private List<FachspezialistSparte> fachspezialistSparten;
    private List<FachspezialistZielgruppe> fachspezialistZielgruppen;
    private List<FachspezialistHandlungsfeld> fachspezialistHandlungsfelder;
    private Long id;
    private LocalDateTime erstellzeitpunkt;

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

    public MitarbeiterBuilder withIstAdmin(boolean istAdmin) {
        this.istAdmin = istAdmin;
        return this;
    }

    public MitarbeiterBuilder withErstellteIdeen(List<Idee> erstellteIdeen) {
        this.erstellteIdeen = erstellteIdeen;
        return this;
    }

    public MitarbeiterBuilder withZugewieseneIdeen(List<Idee> zugewieseneIdeen) {
        this.zugewieseneIdeen = zugewieseneIdeen;
        return this;
    }

    public MitarbeiterBuilder withFachspezialistVertriebswege(List<FachspezialistVertriebsweg> fachspezialistVertriebswege) {
        this.fachspezialistVertriebswege = fachspezialistVertriebswege;
        return this;
    }

    public MitarbeiterBuilder withFachspezialistSparten(List<FachspezialistSparte> fachspezialistSparten) {
        this.fachspezialistSparten = fachspezialistSparten;
        return this;
    }

    public MitarbeiterBuilder withFachspezialistZielgruppen(List<FachspezialistZielgruppe> fachspezialistZielgruppen) {
        this.fachspezialistZielgruppen = fachspezialistZielgruppen;
        return this;
    }

    public MitarbeiterBuilder withFachspezialistHandlungsfelder(List<FachspezialistHandlungsfeld> fachspezialistHandlungsfelder) {
        this.fachspezialistHandlungsfelder = fachspezialistHandlungsfelder;
        return this;
    }

    public MitarbeiterBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MitarbeiterBuilder withErstellzeitpunkt(LocalDateTime erstellzeitpunkt) {
        this.erstellzeitpunkt = erstellzeitpunkt;
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
        mitarbeiter.setIstAdmin(istAdmin);
        mitarbeiter.setErstellteIdeen(erstellteIdeen);
        mitarbeiter.setZugewieseneIdeen(zugewieseneIdeen);
        mitarbeiter.setFachspezialistVertriebswege(fachspezialistVertriebswege);
        mitarbeiter.setFachspezialistSparten(fachspezialistSparten);
        mitarbeiter.setFachspezialistZielgruppen(fachspezialistZielgruppen);
        mitarbeiter.setFachspezialistHandlungsfelder(fachspezialistHandlungsfelder);
        mitarbeiter.setId(id);
        mitarbeiter.setErstellzeitpunkt(erstellzeitpunkt);
        return mitarbeiter;
    }
}
