package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.api.model.IdeeDTO;

public final class IdeeDTOBuilder {
    private String bearbeitungsstatus;
    private String begruendung;
    private String beschreibung;
    private String bezeichnung;
    private String typ;
    private String erfasser;
    private String fachspezialist;

    private IdeeDTOBuilder() {
    }

    public static IdeeDTOBuilder anIdeeDTO() {
        return new IdeeDTOBuilder();
    }

    public IdeeDTOBuilder withBearbeitungsstatus(String bearbeitungsstatus) {
        this.bearbeitungsstatus = bearbeitungsstatus;
        return this;
    }

    public IdeeDTOBuilder withBegruendung(String begruendung) {
        this.begruendung = begruendung;
        return this;
    }

    public IdeeDTOBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public IdeeDTOBuilder withBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public IdeeDTOBuilder withTyp(String typ) {
        this.typ = typ;
        return this;
    }

    public IdeeDTOBuilder withErfasser(String erfasser) {
        this.erfasser = erfasser;
        return this;
    }

    public IdeeDTOBuilder withFachspezialist(String fachspezialist) {
        this.fachspezialist = fachspezialist;
        return this;
    }

    public IdeeDTO build() {
        IdeeDTO ideeDTO = new IdeeDTO();
        ideeDTO.setBearbeitungsstatus(bearbeitungsstatus);
        ideeDTO.setBegruendung(begruendung);
        ideeDTO.setBeschreibung(beschreibung);
        ideeDTO.setBezeichnung(bezeichnung);
        ideeDTO.setTyp(typ);
        ideeDTO.setErfasser(erfasser);
        ideeDTO.setFachspezialist(fachspezialist);
        return ideeDTO;
    }
}
