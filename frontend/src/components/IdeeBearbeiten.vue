<template>
  <article>
    <div class="container-fluid" name="IdeeBearbeitenContainer">
      <h1>Idee bearbeiten</h1>
      <div class="row">
        <!-- Ideen Titel/Name -->
        <h2>Titel</h2>
        <div class="titel">
          <label id="ideeName">{{ titel }}</label>
        </div>
      </div>
      <div class="row">
        <!-- Beschreibungstext -->
        <h2>Beschreibungstext</h2>
        <div class="beschreibung">
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
      <div class="row">
        <!-- Vorteile -->
        <h2>Vorteile</h2>
        <div class="vorteile">
          <input id="vorteile" type="text" v-model="vorteilText" />
          <button class="ideeButton" id="entfernen" @click="vorteilEntfernen()">-</button>
          <button class="ideeButton" id="hinzu" @click="vorteilHinzufuegen()">+</button>
          <br />
          <select v-model="selectedVorteil" multiple id="selectVorteile">
            <option
              v-for="vorteil in vorteile"
              :key="vorteil"
              @click="vorteilSelection(vorteil)"
            >{{ vorteil }}</option>
          </select>
        </div>
      </div>
      <div class="row">
        <!-- Existierende Idee - Checkbox -->
        <div class="existiert" v-bind:class="[existiertAktiv]">
          <p for="existiertBereits">Existiert eine vergleichbare Idee?</p>
          <input
            type="checkbox"
            v-model="existiert"
            id="existiertBereits"
            v-on:click="existiert = true"
          />
          <div class="existiertInfo" v-if="existiert">
            <label id="unternehmenLbl" for="unternehmen">Unternehmen:</label>
            <input type="text" id="unternehmen" v-model="unternehmen" />
            <label
              id="beschreibungsTextExistiertLbl"
              for="beschreibungsTextExistiert"
            >Beschreibung wie die diese Idee schon umgesetzt ist:</label>
            <textarea id="beschreibungsTextExistiert" v-model="beschreibungEx"></textarea>
          </div>
        </div>
      </div>
      <div class="row">
        <!--Comboboxen zur Ideen-Spezifikation -->
        <div class="ideenDropdowns">
          <div class="combobox">
            <p>Ideen-Typ</p>
            <select v-model="ideenTyp" @click="ideeSelection()">
              <option value="PRODUKTIDEE">Produktidee</option>
              <option value="INTERNE_IDEE">Interne Idee</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[sparteAktiv]">
            <p>Sparte</p>
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
          <div class="combobox" v-bind:class="[vertriebswegAktiv]">
            <p>Vertriebsweg</p>
            <select v-model="vertriebsweg" multiple>
              <option value="STATIONAERER_VERTRIEB">Stationärer Vertrieb in eigenen Geschäftsstelle</option>
              <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
              <option value="KOOPERATION_MIT_KREDITINSTITUTEN">Kooperation mit Kreditinstituten</option>
              <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[zielgruppeAktiv]">
            <p>Zielgruppen</p>
            <select v-model="zielgruppe" multiple>
              <option selected value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
              <option value="FAMILIEN">Familien</option>
              <option value="SINGLES">Singles</option>
              <option value="PAARE">Paare</option>
              <option value="PERSONEN_50PLUS">Personen 50+</option>
              <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[handlungsfelderAktiv]">
            <p>Handlungsfelder</p>
            <select v-model="handlungsfeld">
              <option value="KOSTENSENKUNG">Kostensenkung</option>
              <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
              <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
            </select>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="buttons">
          <button id="entfernen" @click="goBack()">Abbrechen</button>
          <button id="hinzu" @click="ideeSpeichern()">Speichern</button>
        </div>
      </div>
    </div>
  </article>
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
    // Logik für das Auswählen/Ausblenden von Comboboxen
    ideeSelection() {
      if (this.ideenTyp == "PRODUKTIDEE") {
        this.handlungsfelderAktiv = "inaktiv";
        this.sparteAktiv = "aktiv";
        this.vertriebswegAktiv = "aktiv";
        this.zielgruppeAktiv = "aktiv";
        this.existiertAktiv = "aktiv";
      } else {
        this.handlungsfelderAktiv = "aktiv";
        this.sparteAktiv = "inaktiv";
        this.vertriebswegAktiv = "inaktiv";
        this.zielgruppeAktiv = "inaktiv";
        this.existiertAktiv = "inaktiv";
      }
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

      axiosInstance.put(
        "http://localhost:9090/idee",
        {
          typ: this.ideeTyp,
          existiertBereits: this.existiert,
          titel: this.titel,
          erfasser: erfasserT,
          erstellzeitpunkt: this.erstelldatum,
          beschreibung: this.beschreibung,
          vorteile: this.vorteile,
          unternehmensbezeichnung: this.unternehmensbezeichnung,
          artDerUmsetzung: this.beschreibungEx,
          sparten: this.sparte,
          vertriebsweg: this.vertriebsweg,
          zielgruppe: this.zielgruppe,
          handlungsfeld: this.handlungsfeld,
          bearbeitungsstatus: this.ideeBearbeitungszustand,
        },
        config
      );
    },
  },
  mounted() {},
  created() {
    this.idee = JSON.parse(localStorage.getItem("idee") as string);

    if ((this.idee as any).existiertBereit) this.existiert = true;
    else this.existiert = false;
    this.ideeTyp = (this.idee as any).typ;
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

    if (this.vorteile == null) this.vorteile = [];
  },
});
</script>

<style lang="scss" scoped>
.beschreibung,
.beschreibung textarea,
.dateien,
.row,
.titel,
.ideenDropdowns,
#selectVorteile,
select {
  width: 100%;
}
.ideeButton,
#vorteile,
#titel {
  height: 30px;
}
p,
label,
button {
  font-size: 1rem;
}
.container-fluid,
.beschreibung {
  height: 100%;
}
.container-fluid,
button {
  border-radius: 20px;
}
.container-fluid,
#selectVorteile {
  overflow: hidden;
}
#vorteile,
#titel {
  width: 88%;
  margin-bottom: 2px;
}
#entfernen,
#hinzu {
  color: #fff;
}
.combobox,
#entfernen {
  margin-right: 2px;
}
.container-fluid {
  width: 800px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.vorteile button {
  padding: 5px;
}
h1 {
  text-align: center;
}
.beschreibung textarea {
  height: 200px;
}
.ideeButton {
  width: 30px;
}
.vorteile {
  width: 650px;
}
.row {
  padding-left: 3%;
}
.ideenDropdowns {
  display: flex;
  justify-content: space-between;
}
#existiertBereits {
  height: 15px;
  width: 15px;
  margin-left: 20px;
}
.buttons {
  padding: 1%;
  margin: auto;
}
#hinzu {
  background-color: #00894d;
}
#entfernen {
  background-color: #f80303;
}
button {
  border: 1px solid #fff;
  font-weight: bold;
  padding: 2px 8px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: transform 0.1s ease-in;
  &:active {
    transform: scale(0.9);
  }
  &:focus {
    outline: none;
  }
}
select {
  font-size: 0.8rem;
}
.inaktiv {
  display: none;
}
</style>