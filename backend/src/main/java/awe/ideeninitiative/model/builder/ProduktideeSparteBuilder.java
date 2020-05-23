package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Sparte;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.ProduktideeSparte;

public final class ProduktideeSparteBuilder {
    private Idee idee;
    private Sparte sparte;

    private ProduktideeSparteBuilder() {
    }

    public static ProduktideeSparteBuilder aProduktideeSparte() {
        return new ProduktideeSparteBuilder();
    }

    public ProduktideeSparteBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public ProduktideeSparteBuilder withSparte(Sparte sparte) {
        this.sparte = sparte;
        return this;
    }

    public ProduktideeSparte build() {
        ProduktideeSparte produktideeSparte = new ProduktideeSparte();
        produktideeSparte.setIdee(idee);
        produktideeSparte.setSparte(sparte);
        return produktideeSparte;
    }
}
