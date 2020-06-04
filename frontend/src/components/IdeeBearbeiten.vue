<template>
  <div class="container-fluid" name="IdeeBearbeitenContainer">
    <div id="anzeige-aktuelle-seite">Idee bearbeiten</div>
    <!-- Ideen Titel/Name -->
    <h3 id="titel">{{titel}}</h3>
    <div>
      <!-- Beschreibungstext -->
      Beschreibung
      <div class="beschreibung grid-item">
        <textarea id="beschreibungsText" v-model="beschreibung"></textarea>
      </div>
    </div>
    <!--  <div class="row">
        Datei Upload 
        <h2>Datei Upload</h2>
        <div class="dateien">
          <input type="file" />
        </div>
    </div>-->

    <!-- Vorteile -->
    Vorteile
    <div class="grid-row">
      <div class="vorteile grid-item vorteile-input">
        <input id="vorteile" type="text" v-model="vorteilText" />
        <select v-model="selectedVorteil" multiple id="selectVorteile">
          <option
            v-for="vorteil in vorteile"
            :key="vorteil"
            @click="vorteilSelection(vorteil)"
          >{{ vorteil }}</option>
        </select>
      </div>
      <div class="grid-item" id="spalte-vorteile-buttons">
        <button class="ideeButton" @click="vorteilHinzufuegen()">+ hinzufügen</button>
        <button class="ideeButton roter-button" @click="vorteilEntfernen()">- entfernen</button>
      </div>
    </div>
    <!--Comboboxen zur Ideen-Spezifikation -->
    <div class="ideenDropdowns">
      <div class="combobox">
        Ideen-Typ
        <select v-model="ideenTyp">
          <option value="PRODUKTIDEE">Produktidee</option>
          <option value="INTERNE_IDEE">Interne Idee</option>
        </select>
      </div>
      <div class="combobox" v-if="ideenTyp == 'PRODUKTIDEE'">
        Sparte
        <select v-model="sparte">
          <option value="KFZ">KFZ</option>
          <option value="UNFALL">Unfall</option>
          <option value="KRANKENVERSICHERUNG">Krankenversicherung</option>
          <option value="RECHTSSCHUTZ">Rechtsschutz</option>
          <option value="LEBENSVERSICHERUNG">Lebensversicherung</option>
          <option value="RENTENVERSICHERUNG">Rentenversicherung</option>
          <option value="HAFTPFLICHT">Haftpflicht</option>
          <option value="HAUSRAT">Hausrat</option>
          <option value="WOHNGEBAUEDEVERSICHERUNG">Wohngebäudeversicherung</option>
        </select>
      </div>
      <div class="combobox" v-if="ideenTyp == 'PRODUKTIDEE'">
        Vertriebsweg
        <select v-model="vertriebsweg" multiple>
          <option value="STATIONAERER_VERTRIEB">Stationärer Vertrieb in eigenen Geschäftsstelle</option>
          <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
          <option value="KOOPERATION_MIT_KREDITINSTITUTEN">Kooperation mit Kreditinstituten</option>
          <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
        </select>
      </div>
      <div class="combobox" v-if="ideenTyp == 'PRODUKTIDEE'">
        Zielgruppen
        <select v-model="zielgruppe" multiple>
          <option selected value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
          <option value="FAMILIEN">Familien</option>
          <option value="SINGLES">Singles</option>
          <option value="PAARE">Paare</option>
          <option value="PERSONEN_50PLUS">Personen 50+</option>
          <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
        </select>
      </div>
      <div class="combobox" v-if="ideenTyp == 'INTERNE_IDEE'">
        Handlungsfelder
        <select v-model="handlungsfeld">
          <option value="KOSTENSENKUNG">Kostensenkung</option>
          <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
          <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
        </select>
      </div>
    </div>
    <!-- Existierende Idee - Checkbox -->
    <div class="existiert" v-if="ideenTyp == 'PRODUKTIDEE'">
      <!--<p for="existiertBereits">-->
      <span>Existiert eine vergleichbare Idee?
      <!--</p>-->
      <input type="checkbox" v-model="existiert" id="existiertBereits">
      <span class="grauer-text">Ja, es gibt bereits ähnliche Produkte.</span></span>
      <div class="grid-row-zusatzinfos" v-if="existiert">
        <label id="unternehmenLbl" for="unternehmen" class="grid-item grauer-text">Unternehmen:</label>
        <input type="text" id="unternehmen" v-model="unternehmen" class="grid-item" />
        <label
          id="beschreibungsTextExistiertLbl"
          for="beschreibungsTextExistiert"
          class="grid-item grauer-text"
        >In welcher Form ist es dort bereits umgesetzt?</label>
        <textarea id="beschreibungsTextExistiert" v-model="beschreibungEx" class="grid-item"></textarea>
      </div>
    </div>
    <div class="buttons">
      <button class="roter-button" @click="goBack()">Abbrechen</button>
      <button @click="ideeSpeichern()">Speichern</button>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import { Helper } from "../services/helper";
export default Vue.extend({
  data: () => ({
    // Auth Token
    token: localStorage.getItem("token"),
    // Idee-Objekt
    idee: {},
    // Beschreibende Attribute
    titel: "",
    beschreibung: "",
    existiert: false,
    unternehmen: "",
    beschreibungEx: "",
    // Data für Vorteile und Vorteilslogik
    counter: 0,
    vorteilText: "",
    vorteile: [],
    selectedVorteil: [{}],
    selectedIndex: 0,
    // Data für Comboboxen und Comboboxlogik
    ideenTyp: "",
    sparte: "",
    vertriebsweg: [{}],
    zielgruppe: [{}],
    handlungsfeld: "",
    // Aktivieren/Deaktivieren
    sparteAktiv: "aktiv",
    vertriebswegAktiv: "aktiv",
    zielgruppeAktiv: "aktiv",
    handlungsfelderAktiv: "aktiv",
    existiertAktiv: "aktiv",
    // Zusätzliche Attribute
    ideeBearbeitungszustand: "",
    erstelldatum: "",
  }),
  methods: {
    // Methode für Abbrechen-Button => Zurück im Browser
    goBack() {
      if (window.history.length > 1) this.$router.go(-1);
    },
    // Logik für das Hinzufügen/Entfernen von Vorteilen
    vorteilHinzufuegen() {
      if (this.counter < 3 && this.vorteilText != "") {
        this.vorteile.push(this.vorteilText);
        this.counter++;
      } else if (this.counter > 2)
        alert("Pro Idee können nur 3 Ideen hinzugefügt werden");
      else alert("Eingabefeld darf nicht leer sein");
    },
    vorteilEntfernen() {
      if (this.counter) {
        this.$delete(this.vorteile, this.selectedIndex);
        this.counter--;
        if (this.counter < 0) this.counter = 0;
      }
    },
    vorteilSelection(selectedVorteil: { id: number; vorteil: string }) {
      this.selectedIndex = this.vorteile.indexOf(selectedVorteil);
    },
    // Eingaben überprüfen und Idee speichern
    validiereEingaben(): boolean {
      var errorMessage = "Angaben ungültig: ";
      var error = true;
      if (this.ideenTyp == "Interne Idee" && this.handlungsfeld.length == 0) {
        errorMessage += "Mindestens eine Zielgruppe auswählen \n";
        error = true;
      }
      return error;
    },
    ideeSpeichern() {
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      var erfasserT = decode["sub"];
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      axiosInstance
        .put(
          "http://localhost:9090/idee",
          {
            typ: this.ideenTyp,
            existiertBereits: this.existiert,
            titel: this.titel,
            erfasser: erfasserT,
            erstellzeitpunkt: this.erstelldatum,
            beschreibung: this.beschreibung,
            vorteile: this.vorteile,
            unternehmensbezeichnung: this.unternehmen,
            artDerUmsetzung: this.beschreibungEx,
            sparten: this.sparte,
            vertriebsweg: this.vertriebsweg,
            zielgruppe: this.zielgruppe,
            handlungsfeld: this.handlungsfeld,
            bearbeitungsstatus: this.ideeBearbeitungszustand,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Idee erfolgreich bearbeitet", "success");
          this.$router.push("Meineideen");
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Veröffentlichen",
            "error"
          )
        );
    },
  },
  mounted() {},
  created() {
    this.idee = JSON.parse(localStorage.getItem("idee") as string);

    if ((this.idee as any).existiertBereits) this.existiert = true;
    else this.existiert = false;
    this.ideenTyp = (this.idee as any).typ;
    this.titel = (this.idee as any).titel;
    this.erfasser = (this.idee as any).erfasser;
    this.beschreibung = (this.idee as any).beschreibung;
    this.vorteile = (this.idee as any).vorteile;
    this.unternehmen = (this.idee as any).unternehmensbezeichnung;
    this.beschreibungEx = (this.idee as any).artDerUmsetzung;
    this.sparte = (this.idee as any).sparten;
    this.vertriebsweg = (this.idee as any).vertriebsweg;
    this.zielgruppe = (this.idee as any).zielgruppe;
    this.handlungsfeld = (this.idee as any).handlungsfeld;
    this.ideeBearbeitungszustand = (this.idee as any).bearbeitungsstatus;
    this.erstelldatum = (this.idee as any).erstellzeitpunkt;
    this.counter = this.vorteile != null ? this.vorteile.length : 0;
    if (this.vorteile == null) this.vorteile = [];
  },
});
</script>

<style lang="scss" scoped>
$medium-green: #00894d;
$light-green: #69a82f;
.beschreibung,
.beschreibung textarea,
.dateien,
.ideeButton .ideenDropdowns,
#selectVorteile,
.existiert,
select {
  width: 100%;
}
#vorteile,
#titel {
  height: 30px;
  width: 100%;
}
label {
  font-size: 1rem;
}
.container-fluid {
  border-radius: 20px;
}
.container-fluid,
#selectVorteile {
  overflow: hidden;
}
#vorteile,
#titel {
  margin-bottom: 2px;
}
.combobox {
  margin-right: 2px;
}
.container-fluid {
  height: 100%;
  width: 800px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
  padding-left: 30px;
  padding-right: 30px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  & div{
    padding-top: 0.5rem;
  }
}
.vorteile button {
  padding: 5px;
}
h1 {
  text-align: center;
}
.beschreibung textarea {
  height: 8rem;
}
.ideenDropdowns {
  display: flex;
  justify-content: start;
}
#existiertBereits {
  margin-left: 20px;
}
.buttons {
  padding: 1%;
  margin: auto;
}
select {
  font-size: 0.8rem;
}
#titel {
  text-align: center;
}
.grid-row {
  display: grid;
  grid-template-columns: 70% auto;
  grid-template-rows: 100%;
}
.grid-row-zusatzinfos {
  flex: 1;
  display: grid;
  grid-template-columns: 20% auto;
  grid-template-rows: 30px auto;
}
.vorteile-input input,
#unternehmen {
  border-radius: 0;
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
}
.vorteile-input select,
#beschreibungsTextExistiert {
  border-radius: 0;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
}
.existiert{
  flex: 1;
  display: flex;
  flex-direction: column;
}
#spalte-vorteile-buttons{
  display:flex;
  flex-direction: column;
  justify-content: space-around;
  padding-left: 15px;
}
</style>