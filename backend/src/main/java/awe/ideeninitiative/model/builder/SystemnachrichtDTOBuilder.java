package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.api.model.SystemnachrichtDTO;

public final class SystemnachrichtDTOBuilder {
    private String titel;
    private String beschreibung;

    private SystemnachrichtDTOBuilder() {
    }

    public static SystemnachrichtDTOBuilder aSystemnachrichtDTO() {
        return new SystemnachrichtDTOBuilder();
    }

    public SystemnachrichtDTOBuilder withTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public SystemnachrichtDTOBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public SystemnachrichtDTO build() {
        SystemnachrichtDTO systemnachrichtDTO = new SystemnachrichtDTO();
        systemnachrichtDTO.setTitel(titel);
        systemnachrichtDTO.setBeschreibung(beschreibung);
        return systemnachrichtDTO;
    }
}
