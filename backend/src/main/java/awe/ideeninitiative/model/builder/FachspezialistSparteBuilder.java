package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.mitarbeiter.FachspezialistSparte;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

public final class FachspezialistSparteBuilder {
    private Mitarbeiter mitarbeiter;
    private Sparte sparte;

    private FachspezialistSparteBuilder() {
    }

    public static FachspezialistSparteBuilder aFachspezialistSparte() {
        return new FachspezialistSparteBuilder();
    }

    public FachspezialistSparteBuilder withMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
        return this;
    }

    public FachspezialistSparteBuilder withSparte(Sparte sparte) {
        this.sparte = sparte;
        return this;
    }

    public FachspezialistSparte build() {
        FachspezialistSparte fachspezialistSparte = new FachspezialistSparte();
        fachspezialistSparte.setMitarbeiter(mitarbeiter);
        fachspezialistSparte.setSparte(sparte);
        return fachspezialistSparte;
    }
}
