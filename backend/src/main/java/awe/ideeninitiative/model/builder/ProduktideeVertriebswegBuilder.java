package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.ProduktideeVertriebsweg;

public final class ProduktideeVertriebswegBuilder {
    private Idee idee;
    private Vertriebskanal vertriebsweg;

    private ProduktideeVertriebswegBuilder() {
    }

    public static ProduktideeVertriebswegBuilder aProduktideeVertriebsweg() {
        return new ProduktideeVertriebswegBuilder();
    }

    public ProduktideeVertriebswegBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public ProduktideeVertriebswegBuilder withVertriebsweg(Vertriebskanal vertriebsweg) {
        this.vertriebsweg = vertriebsweg;
        return this;
    }

    public ProduktideeVertriebsweg build() {
        ProduktideeVertriebsweg produktideeVertriebsweg = new ProduktideeVertriebsweg();
        produktideeVertriebsweg.setIdee(idee);
        produktideeVertriebsweg.setVertriebsweg(vertriebsweg);
        return produktideeVertriebsweg;
    }
}
