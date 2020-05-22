package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.api.model.IdeeDTO;

import java.util.List;

public final class IdeeDTOBuilder {
    private String bearbeitungsstatus;
    private String begruendung;
    private String beschreibung;
    private String titel;
    private String typ;
    private String erfasser;
    private String fachspezialist;
    private Boolean existiertBereits;
    private String unternehmensbezeichnung = null;
    private String artDerUmsetzung = null;
    private String sparten;
    private List<String> vertriebsweg = null;
    private List<String> zielgruppe = null;
    private List<String> vorteile = null;
    private String handlungsfeld;

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

    public IdeeDTOBuilder withTitel(String titel) {
        this.titel = titel;
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

    public IdeeDTOBuilder withExistiertBereits(Boolean existiertBereits) {
        this.existiertBereits = existiertBereits;
        return this;
    }

    public IdeeDTOBuilder withUnternehmensbezeichnung(String unternehmensbezeichnung) {
        this.unternehmensbezeichnung = unternehmensbezeichnung;
        return this;
    }

    public IdeeDTOBuilder withArtDerUmsetzung(String artDerUmsetzung) {
        this.artDerUmsetzung = artDerUmsetzung;
        return this;
    }

    public IdeeDTOBuilder withSparten(String sparten) {
        this.sparten = sparten;
        return this;
    }

    public IdeeDTOBuilder withVertriebsweg(List<String> vertriebsweg) {
        this.vertriebsweg = vertriebsweg;
        return this;
    }

    public IdeeDTOBuilder withZielgruppe(List<String> zielgruppe) {
        this.zielgruppe = zielgruppe;
        return this;
    }

    public IdeeDTOBuilder withVorteile(List<String> vorteile) {
        this.vorteile = vorteile;
        return this;
    }

    public IdeeDTOBuilder withHandlungsfeld(String handlungsfeld) {
        this.handlungsfeld = handlungsfeld;
        return this;
    }

    public IdeeDTO build() {
        IdeeDTO ideeDTO = new IdeeDTO();
        ideeDTO.setBearbeitungsstatus(bearbeitungsstatus);
        ideeDTO.setBegruendung(begruendung);
        ideeDTO.setBeschreibung(beschreibung);
        ideeDTO.setTitel(titel);
        ideeDTO.setTyp(typ);
        ideeDTO.setErfasser(erfasser);
        ideeDTO.setFachspezialist(fachspezialist);
        ideeDTO.setExistiertBereits(existiertBereits);
        ideeDTO.setUnternehmensbezeichnung(unternehmensbezeichnung);
        ideeDTO.setArtDerUmsetzung(artDerUmsetzung);
        ideeDTO.setSparten(sparten);
        ideeDTO.setVertriebsweg(vertriebsweg);
        ideeDTO.setZielgruppe(zielgruppe);
        ideeDTO.setVorteile(vorteile);
        ideeDTO.setHandlungsfeld(handlungsfeld);
        return ideeDTO;
    }
}
