package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;
import awe.ideeninitiative.model.mitarbeiter.ProfilbildDatei;

import java.time.LocalDateTime;

public final class ProfilbildDateiBuilder {
    private Mitarbeiter mitarbeiter;
    private String dateiname;
    private String dateityp;
    private byte[] dateiinhalt;
    private Long id;
    private LocalDateTime erstellzeitpunkt;

    private ProfilbildDateiBuilder() {
    }

    public static ProfilbildDateiBuilder aProfilbildDatei() {
        return new ProfilbildDateiBuilder();
    }

    public ProfilbildDateiBuilder withMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
        return this;
    }

    public ProfilbildDateiBuilder withDateiname(String dateiname) {
        this.dateiname = dateiname;
        return this;
    }

    public ProfilbildDateiBuilder withDateityp(String dateityp) {
        this.dateityp = dateityp;
        return this;
    }

    public ProfilbildDateiBuilder withDateiinhalt(byte[] dateiinhalt) {
        this.dateiinhalt = dateiinhalt;
        return this;
    }

    public ProfilbildDateiBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProfilbildDateiBuilder withErstellzeitpunkt(LocalDateTime erstellzeitpunkt) {
        this.erstellzeitpunkt = erstellzeitpunkt;
        return this;
    }

    public ProfilbildDatei build() {
        ProfilbildDatei profilbildDatei = new ProfilbildDatei();
        profilbildDatei.setMitarbeiter(mitarbeiter);
        profilbildDatei.setDateiname(dateiname);
        profilbildDatei.setDateityp(dateityp);
        profilbildDatei.setDateiinhalt(dateiinhalt);
        profilbildDatei.setId(id);
        profilbildDatei.setErstellzeitpunkt(erstellzeitpunkt);
        return profilbildDatei;
    }
}
