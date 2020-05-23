package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Handlungsfeld;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.InterneIdeeHandlungsfeld;

public final class InterneIdeeHandlungsfeldBuilder {
    private Idee idee;
    private Handlungsfeld handlungsfeld;

    private InterneIdeeHandlungsfeldBuilder() {
    }

    public static InterneIdeeHandlungsfeldBuilder anInterneIdeeHandlungsfeld() {
        return new InterneIdeeHandlungsfeldBuilder();
    }

    public InterneIdeeHandlungsfeldBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public InterneIdeeHandlungsfeldBuilder withHandlungsfeld(Handlungsfeld handlungsfeld) {
        this.handlungsfeld = handlungsfeld;
        return this;
    }

    public InterneIdeeHandlungsfeld build() {
        InterneIdeeHandlungsfeld interneIdeeHandlungsfeld = new InterneIdeeHandlungsfeld();
        interneIdeeHandlungsfeld.setIdee(idee);
        interneIdeeHandlungsfeld.setHandlungsfeld(handlungsfeld);
        return interneIdeeHandlungsfeld;
    }
}
