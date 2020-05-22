package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.idee.Idee;
import awe.ideeninitiative.model.idee.ProduktideeZusatzinformation;

public final class ProduktideeZusatzinformationBuilder {
    private Idee idee;
    private boolean existiertBereits;
    private String unternehmensbezeichnung;
    private String artDerUmsetzung;

    private ProduktideeZusatzinformationBuilder() {
    }

    public static ProduktideeZusatzinformationBuilder aProduktideeZusatzinformation() {
        return new ProduktideeZusatzinformationBuilder();
    }

    public ProduktideeZusatzinformationBuilder withIdee(Idee idee) {
        this.idee = idee;
        return this;
    }

    public ProduktideeZusatzinformationBuilder withExistiertBereits(boolean existiertBereits) {
        this.existiertBereits = existiertBereits;
        return this;
    }

    public ProduktideeZusatzinformationBuilder withUnternehmensbezeichnung(String unternehmensbezeichnung) {
        this.unternehmensbezeichnung = unternehmensbezeichnung;
        return this;
    }

    public ProduktideeZusatzinformationBuilder withArtDerUmsetzung(String artDerUmsetzung) {
        this.artDerUmsetzung = artDerUmsetzung;
        return this;
    }

    public ProduktideeZusatzinformation build() {
        ProduktideeZusatzinformation produktideeZusatzinformation = new ProduktideeZusatzinformation();
        produktideeZusatzinformation.setIdee(idee);
        produktideeZusatzinformation.setExistiertBereits(existiertBereits);
        produktideeZusatzinformation.setUnternehmensbezeichnung(unternehmensbezeichnung);
        produktideeZusatzinformation.setArtDerUmsetzung(artDerUmsetzung);
        return produktideeZusatzinformation;
    }
}
