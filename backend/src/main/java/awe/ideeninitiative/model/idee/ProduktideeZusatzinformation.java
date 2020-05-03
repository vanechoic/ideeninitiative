package awe.ideeninitiative.model.idee;

import awe.ideeninitiative.model.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ProduktideeZusatzinformation extends AbstractEntity {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idee_id", referencedColumnName = "id")
    private Idee idee;

    private boolean existiertBereits;

    private String unternehmensbezeichnung;

    private String artDerUmsetzung;

    public boolean isExistiertBereits() {
        return existiertBereits;
    }

    public void setExistiertBereits(boolean existiertBereits) {
        this.existiertBereits = existiertBereits;
    }

    public String getUnternehmensbezeichnung() {
        return unternehmensbezeichnung;
    }

    public void setUnternehmensbezeichnung(String unternehmensbezeichnung) {
        this.unternehmensbezeichnung = unternehmensbezeichnung;
    }

    public String getArtDerUmsetzung() {
        return artDerUmsetzung;
    }

    public Idee getIdee() {
        return idee;
    }

    public void setIdee(Idee idee) {
        this.idee = idee;
    }

    public void setArtDerUmsetzung(String artDerUmsetzung) {
        this.artDerUmsetzung = artDerUmsetzung;
    }
}
