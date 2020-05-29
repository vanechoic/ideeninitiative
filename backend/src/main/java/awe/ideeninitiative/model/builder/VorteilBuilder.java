package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.Vorteil;

import java.time.LocalDateTime;

public final class VorteilBuilder {
    private Idee idee;
    private String beschreibung;
    private LocalDateTime erstellzeitpunkt;

    private VorteilBuilder() {
    }

    public static VorteilBuilder aVorteil() {
        return new VorteilBuilder();
    }

    public VorteilBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public VorteilBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public VorteilBuilder withErstellzeitpunkt(LocalDateTime erstellzeitpunkt) {
        this.erstellzeitpunkt = erstellzeitpunkt;
        return this;
    }

    public Vorteil build() {
        Vorteil vorteil = new Vorteil();
        vorteil.setIdee(idee);
        vorteil.setBeschreibung(beschreibung);
        vorteil.setErstellzeitpunkt(erstellzeitpunkt);
        return vorteil;
    }
}
