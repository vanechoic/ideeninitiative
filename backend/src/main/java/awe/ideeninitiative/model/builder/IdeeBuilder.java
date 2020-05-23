package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Ideenstatus;
import awe.ideeninitiative.model.enums.Ideentyp;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

public final class IdeeBuilder {
    private String titel;
    private String beschreibung;
    private String begruendung;
    private Ideenstatus bearbeitungsstatus;
    private Ideentyp typ;
    private Mitarbeiter erfasser;
    private Mitarbeiter fachspezialist;

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

    public Idee build() {
        Idee idee = new Idee();
        idee.setTitel(titel);
        idee.setBeschreibung(beschreibung);
        idee.setBegruendung(begruendung);
        idee.setBearbeitungsstatus(bearbeitungsstatus);
        idee.setTyp(typ);
        idee.setErfasser(erfasser);
        idee.setFachspezialist(fachspezialist);
        return idee;
    }
}
