package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.api.model.BenutzerDTO;

import java.util.List;

public final class BenutzerDTOBuilder {
    private String benutzername;
    private String vorname;
    private String nachname;
    private String email;
    private String passwort;
    private List<String> fachspezialistVertriebswege = null;
    private List<String> fachspezialistHandlungsfelder = null;
    private List<String> fachspezialistSparten = null;
    private List<String> fachspezialistZielgruppen = null;
    private Boolean istFachspezialist;
    private Boolean istAdmin;

    private BenutzerDTOBuilder() {
    }

    public static BenutzerDTOBuilder aBenutzerDTO() {
        return new BenutzerDTOBuilder();
    }

    public BenutzerDTOBuilder withBenutzername(String benutzername) {
        this.benutzername = benutzername;
        return this;
    }

    public BenutzerDTOBuilder withVorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public BenutzerDTOBuilder withNachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public BenutzerDTOBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public BenutzerDTOBuilder withPasswort(String passwort) {
        this.passwort = passwort;
        return this;
    }

    public BenutzerDTOBuilder withFachspezialistVertriebswege(List<String> fachspezialistVertriebswege) {
        this.fachspezialistVertriebswege = fachspezialistVertriebswege;
        return this;
    }

    public BenutzerDTOBuilder withFachspezialistHandlungsfelder(List<String> fachspezialistHandlungsfelder) {
        this.fachspezialistHandlungsfelder = fachspezialistHandlungsfelder;
        return this;
    }

    public BenutzerDTOBuilder withFachspezialistSparten(List<String> fachspezialistSparten) {
        this.fachspezialistSparten = fachspezialistSparten;
        return this;
    }

    public BenutzerDTOBuilder withFachspezialistZielgruppen(List<String> fachspezialistZielgruppen) {
        this.fachspezialistZielgruppen = fachspezialistZielgruppen;
        return this;
    }

    public BenutzerDTOBuilder withIstFachspezialist(Boolean istFachspezialist) {
        this.istFachspezialist = istFachspezialist;
        return this;
    }

    public BenutzerDTOBuilder withIstAdmin(Boolean istAdmin) {
        this.istAdmin = istAdmin;
        return this;
    }

    public BenutzerDTO build() {
        BenutzerDTO benutzerDTO = new BenutzerDTO();
        benutzerDTO.setBenutzername(benutzername);
        benutzerDTO.setVorname(vorname);
        benutzerDTO.setNachname(nachname);
        benutzerDTO.setEmail(email);
        benutzerDTO.setPasswort(passwort);
        benutzerDTO.setFachspezialistVertriebswege(fachspezialistVertriebswege);
        benutzerDTO.setFachspezialistHandlungsfelder(fachspezialistHandlungsfelder);
        benutzerDTO.setFachspezialistSparten(fachspezialistSparten);
        benutzerDTO.setFachspezialistZielgruppen(fachspezialistZielgruppen);
        benutzerDTO.setIstFachspezialist(istFachspezialist);
        benutzerDTO.setIstAdmin(istAdmin);
        return benutzerDTO;
    }
}
