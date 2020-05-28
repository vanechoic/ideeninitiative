package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.mitarbeiter.FachspezialistHandlungsfeld;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

public final class FachspezialistHandlungsfeldBuilder {
    private Mitarbeiter mitarbeiter;
    private Handlungsfeld handlungsfeld;
    private Long id;

    private FachspezialistHandlungsfeldBuilder() {
    }

    public static FachspezialistHandlungsfeldBuilder aFachspezialistHandlungsfeld() {
        return new FachspezialistHandlungsfeldBuilder();
    }

    public FachspezialistHandlungsfeldBuilder withMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
        return this;
    }

    public FachspezialistHandlungsfeldBuilder withHandlungsfeld(Handlungsfeld handlungsfeld) {
        this.handlungsfeld = handlungsfeld;
        return this;
    }

    public FachspezialistHandlungsfeldBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public FachspezialistHandlungsfeld build() {
        FachspezialistHandlungsfeld fachspezialistHandlungsfeld = new FachspezialistHandlungsfeld();
        fachspezialistHandlungsfeld.setMitarbeiter(mitarbeiter);
        fachspezialistHandlungsfeld.setHandlungsfeld(handlungsfeld);
        fachspezialistHandlungsfeld.setId(id);
        return fachspezialistHandlungsfeld;
    }
}
