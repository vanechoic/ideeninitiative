package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.enums.Vertriebskanal;
import awe.ideeninitiative.model.mitarbeiter.FachspezialistVertriebsweg;
import awe.ideeninitiative.model.mitarbeiter.Mitarbeiter;

public final class FachspezialistVertriebswegBuilder {
    private Mitarbeiter mitarbeiter;
    private Vertriebskanal vertriebsweg;

    private FachspezialistVertriebswegBuilder() {
    }

    public static FachspezialistVertriebswegBuilder aFachspezialistVertriebsweg() {
        return new FachspezialistVertriebswegBuilder();
    }

    public FachspezialistVertriebswegBuilder withMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
        return this;
    }

    public FachspezialistVertriebswegBuilder withVertriebsweg(Vertriebskanal vertriebsweg) {
        this.vertriebsweg = vertriebsweg;
        return this;
    }

    public FachspezialistVertriebsweg build() {
        FachspezialistVertriebsweg fachspezialistVertriebsweg = new FachspezialistVertriebsweg();
        fachspezialistVertriebsweg.setMitarbeiter(mitarbeiter);
        fachspezialistVertriebsweg.setVertriebsweg(vertriebsweg);
        return fachspezialistVertriebsweg;
    }
}
