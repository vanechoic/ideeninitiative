export class Idee {
    
    titel: string
    beschreibung: string
    verfasser: string
    erstellt: string
    vorteile: [{}]
    existiert: boolean
    ideenTyp: string
    sparte: string
    vertriebsweg: [{ value: string }]
    zielgruppe: [{ value: string }]
    handlungsfeld: string;

    constructor(titel: string, beschreibung: string, verfasser: string, erstellt: string, vorteile: [{}],
        existiert: boolean, ideenTyp: string, sparte: string, vertriebsweg: [{ value: string }],
        zielgruppe: [{ value: string }], handlungsfeld: string) {
        this.titel = titel
        this.beschreibung = beschreibung
        this.verfasser = verfasser
        this.erstellt = erstellt
        this.vorteile = vorteile
        this.existiert = existiert
        this.ideenTyp = ideenTyp
        this.sparte = sparte
        this.vertriebsweg = vertriebsweg
        this.zielgruppe = zielgruppe
        this.handlungsfeld = handlungsfeld
    }

    toString() {
        return "'"+ this.titel + "' von: " + this.verfasser + " erstellt: " + this.erstellt + " " + this.ideenTyp;
    }

    get IdeenTyp () {
        return this.ideenTyp
    }
}
