package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.Nachricht;

import java.time.LocalDateTime;

public final class NachrichtBuilder {
    private String titel;
    private Long id;
    private LocalDateTime erstellzeitpunkt;
    private String beschreibung;

    private NachrichtBuilder() {
    }

    public static NachrichtBuilder aNachricht() {
        return new NachrichtBuilder();
    }

    public NachrichtBuilder withTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public NachrichtBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public NachrichtBuilder withErstellzeitpunkt(LocalDateTime erstellzeitpunkt) {
        this.erstellzeitpunkt = erstellzeitpunkt;
        return this;
    }

    public NachrichtBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public Nachricht build() {
        Nachricht nachricht = new Nachricht();
        nachricht.setTitel(titel);
        nachricht.setId(id);
        nachricht.setErstellzeitpunkt(erstellzeitpunkt);
        nachricht.setBeschreibung(beschreibung);
        return nachricht;
    }
}
