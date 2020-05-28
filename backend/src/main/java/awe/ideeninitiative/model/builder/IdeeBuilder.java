package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.*;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

import java.time.LocalDateTime;
import java.util.List;

public final class IdeeBuilder {
    private String titel;
    private String beschreibung;
    private String begruendung;
    private Ideenstatus bearbeitungsstatus;
    private Ideentyp typ;
    private Mitarbeiter erfasser;
    private Mitarbeiter fachspezialist;
    private InterneIdeeHandlungsfeld interneIdeeHandlungsfeld;
    private ProduktideeSparte produktideeSparte;
    private List<ProduktideeVertriebsweg> produktideeVertriebsweg;
    private List<ProduktideeZielgruppe> produktideeZielgruppe;
    private ProduktideeZusatzinformation produktideeZusatzinformation;
    private LocalDateTime erstellzeitpunkt;

    private IdeeBuilder() {
    }

    public static IdeeBuilder anIdee() {
        return new IdeeBuilder();
    }

    public IdeeBuilder withTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public IdeeBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public IdeeBuilder withBegruendung(String begruendung) {
        this.begruendung = begruendung;
        return this;
    }

    public IdeeBuilder withBearbeitungsstatus(Ideenstatus bearbeitungsstatus) {
        this.bearbeitungsstatus = bearbeitungsstatus;
        return this;
    }

    public IdeeBuilder withTyp(Ideentyp typ) {
        this.typ = typ;
        return this;
    }

    public IdeeBuilder withErfasser(Mitarbeiter erfasser) {
        this.erfasser = erfasser;
        return this;
    }

    public IdeeBuilder withFachspezialist(Mitarbeiter fachspezialist) {
        this.fachspezialist = fachspezialist;
        return this;
    }

    public IdeeBuilder withInterneIdeeHandlungsfeld(InterneIdeeHandlungsfeld interneIdeeHandlungsfeld) {
        this.interneIdeeHandlungsfeld = interneIdeeHandlungsfeld;
        return this;
    }

    public IdeeBuilder withProduktideeSparte(ProduktideeSparte produktideeSparte) {
        this.produktideeSparte = produktideeSparte;
        return this;
    }

    public IdeeBuilder withProduktideeVertriebsweg(List<ProduktideeVertriebsweg> produktideeVertriebsweg) {
        this.produktideeVertriebsweg = produktideeVertriebsweg;
        return this;
    }

    public IdeeBuilder withProduktideeZielgruppe(List<ProduktideeZielgruppe> produktideeZielgruppe) {
        this.produktideeZielgruppe = produktideeZielgruppe;
        return this;
    }

    public IdeeBuilder withProduktideeZusatzinformation(ProduktideeZusatzinformation produktideeZusatzinformation) {
        this.produktideeZusatzinformation = produktideeZusatzinformation;
        return this;
    }

    public IdeeBuilder withErstellzeitpunkt(LocalDateTime erstellzeitpunkt) {
        this.erstellzeitpunkt = erstellzeitpunkt;
        return this;
    }

    public Idee build() {
        Idee idee = new Idee();
        idee.setTitel(titel);
        idee.setBeschreibung(beschreibung);
        idee.setBegruendung(begruendung);
        idee.setBearbeitungsstatus(bearbeitungsstatus);
        idee.setTyp(typ);
        idee.setErfasser(erfasser);
        idee.setFachspezialist(fachspezialist);
        idee.setInterneIdeeHandlungsfeld(interneIdeeHandlungsfeld);
        idee.setProduktideeSparte(produktideeSparte);
        idee.setProduktideeVertriebsweg(produktideeVertriebsweg);
        idee.setProduktideeZielgruppe(produktideeZielgruppe);
        idee.setProduktideeZusatzinformation(produktideeZusatzinformation);
        idee.setErstellzeitpunkt(erstellzeitpunkt);
        return idee;
    }
}
