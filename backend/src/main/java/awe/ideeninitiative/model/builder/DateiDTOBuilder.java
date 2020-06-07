package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.api.model.DateiDTO;

public final class DateiDTOBuilder {
    private String dateiinhalt;
    private String dateityp;

    private DateiDTOBuilder() {
    }

    public static DateiDTOBuilder aDateiDTO() {
        return new DateiDTOBuilder();
    }

    public DateiDTOBuilder withDateiinhalt(String dateiinhalt) {
        this.dateiinhalt = dateiinhalt;
        return this;
    }

    public DateiDTOBuilder withDateityp(String dateityp) {
        this.dateityp = dateityp;
        return this;
    }

    public DateiDTO build() {
        DateiDTO dateiDTO = new DateiDTO();
        dateiDTO.setDateiinhalt(dateiinhalt);
        dateiDTO.setDateityp(dateityp);
        return dateiDTO;
    }
}
