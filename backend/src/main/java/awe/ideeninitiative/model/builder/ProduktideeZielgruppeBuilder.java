package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Zielgruppe;
import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.ProduktideeZielgruppe;

public final class ProduktideeZielgruppeBuilder {
    private Idee idee;
    private Zielgruppe zielgruppe;

    private ProduktideeZielgruppeBuilder() {
    }

    public static ProduktideeZielgruppeBuilder aProduktideeZielgruppe() {
        return new ProduktideeZielgruppeBuilder();
    }

    public ProduktideeZielgruppeBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public ProduktideeZielgruppeBuilder withZielgruppe(Zielgruppe zielgruppe) {
        this.zielgruppe = zielgruppe;
        return this;
    }

    public ProduktideeZielgruppe build() {
        ProduktideeZielgruppe produktideeZielgruppe = new ProduktideeZielgruppe();
        produktideeZielgruppe.setIdee(idee);
        produktideeZielgruppe.setZielgruppe(zielgruppe);
        return produktideeZielgruppe;
    }
}
