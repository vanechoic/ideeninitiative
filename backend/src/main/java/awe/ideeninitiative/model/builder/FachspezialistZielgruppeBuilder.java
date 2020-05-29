package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.mitarbeiter.FachspezialistZielgruppe;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

public final class FachspezialistZielgruppeBuilder {
    private Mitarbeiter mitarbeiter;
    private Zielgruppe zielgruppe;

    private FachspezialistZielgruppeBuilder() {
    }

    public static FachspezialistZielgruppeBuilder aFachspezialistZielgruppe() {
        return new FachspezialistZielgruppeBuilder();
    }

    public FachspezialistZielgruppeBuilder withMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
        return this;
    }

    public FachspezialistZielgruppeBuilder withZielgruppe(Zielgruppe zielgruppe) {
        this.zielgruppe = zielgruppe;
        return this;
    }

    public FachspezialistZielgruppe build() {
        FachspezialistZielgruppe fachspezialistZielgruppe = new FachspezialistZielgruppe();
        fachspezialistZielgruppe.setMitarbeiter(mitarbeiter);
        fachspezialistZielgruppe.setZielgruppe(zielgruppe);
        return fachspezialistZielgruppe;
    }
}
