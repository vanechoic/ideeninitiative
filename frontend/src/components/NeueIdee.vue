<template>
  <article>
    <div class="container-fluid" name="NeueIdeeContainer">
      <h1>Neue Idee</h1>
      <div class="row">
        <!-- Ideen Titel/Name -->
        <h2>Titel</h2>
        <div class="titel">
          <input type="text" id="titel" v-model="titel" />
        </div>
      </div>
      <div class="row">
        <!-- Beschreibungstext -->
        <h2>Beschreibungstext</h2>
        <div class="beschreibung">
          <textarea id="beschreibungsText" v-model="beschreibung"></textarea>
        </div>
      </div>
      <div class="row">
        <!-- Datei Upload -->
        <h2>Datei Upload</h2>
        <div class="dateien">
          <input type="file" />
        </div>
      </div>
      <div class="row">
        <!-- Vorteile -->
        <h2>Vorteile</h2>
        <div class="vorteile">
          <input id="vorteile" type="text" v-model="vorteilText" />
          <button class="ideeButton" id="entfernen" @click="vorteilEntfernen()">
            -
          </button>
          <button class="ideeButton" id="hinzu" @click="vorteilHinzufuegen()">
            +
          </button>
          <br />
          <select v-model="selected" multiple id="selectVorteile">
            <option
              v-for="vorteil in vorteile"
              :key="vorteil"
              @click="vorteilSelection(vorteil)"
              >{{ vorteil }}</option
            >
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
            <input type="text" id="unternehmen" v-model="titel" />
            <label
              id="beschreibungsTextExistiertLbl"
              for="beschreibungsTextExistiert"
              >Beschreibung wie die diese Idee schon umgesetzt ist:</label
            >
            <textarea id="beschreibungsTextExistiert"></textarea>
          </div>
        </div>
      </div>
      <div class="row">
        <!--Comboboxen zur Ideen-Spezifikation -->
        <div class="ideenDropdowns">
          <div class="combobox">
            <p>Ideen-Typ</p>
            <select v-model="ideenTyp" @click="ideeSelection()">
              <option value="PRODUKTIDEE" selected>Produktidee</option>
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
              <option value="WOHNGEBAUEDEVERSICHERUNG"
                >Wohngebäudeversicherung</option
              >
            </select>
          </div>
          <div class="combobox" v-bind:class="[vertriebswegAktiv]">
            <p>Vertriebsweg</p>
            <select v-model="vertriebsweg" multiple>
              <option value="STATIONAERER_VERTRIEB"
                >Stationärer Vertrieb in eigenen Geschäftsstelle</option
              >
              <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
              <option value="KOOPERATION_MIT_KREDITINSTITUTEN"
                >Kooperation mit Kreditinstituten</option
              >
              <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[zielgruppeAktiv]">
            <p>Zielgruppen</p>
            <select v-model="zielgruppe" multiple>
              <option selected value="KINDER_JUGENDLICHE"
                >Kinder/Jugendliche</option
              >
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
import { Helper } from "../services/helper";
import { Params } from "../services/params-service";
export default Vue.extend({
  data: () => ({
    // Auth Token
    token: localStorage.getItem("token"),
    // Idee-Objekt
    idee: {
      titel: "String",
      beschreibung: "String",
      vorteile: [{}],
      existiert: false,
      ideenTyp: "String",
      sparte: "String",
      vertriebsweg: [{ value: "" }],
      zielgruppe: [{ value: "" }],
      handlungsfeld: "String",
    },
    // Beschreibende Attribute
    titel: "",
    beschreibung: "",
    existiert: false,
    // Data für Vorteile und Vorteilslogik
    counter: 0,
    vorteilText: "",
    vorteile: [{}],
    selected: "",
    selectedIndex: 0,
    // Data für Comboboxen und Comboboxlogik
    ideenTyp: "",
    sparte: "",
    vertriebsweg: [{ value: "" }],
    zielgruppe: [{ value: "" }],
    handlungsfeld: "",
    // Aktivieren/Deaktivieren
    sparteAktiv: "aktiv",
    vertriebswegAktiv: "aktiv",
    zielgruppeAktiv: "aktiv",
    handlungsfelderAktiv: "aktiv",
    existiertAktiv: "aktiv",
    // Bootstrap Alert Variablen
    showSuccess: false,
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
    vorteilSelection(selected: {}) {
      this.selectedIndex = this.vorteile.indexOf(selected);
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
      // NOCH NICHT FERTIG
      var errorMessage = "Angaben ungültig: ";
      var error = false;
      if (this.ideenTyp == "INTERNE_IDEE" && this.handlungsfeld.length == 0) {
        errorMessage += "Mindestens eine Zielgruppe auswählen \n";
        error = true;
      }
      return error;
    },
    ideeSpeichern() {
      if (!this.validiereEingaben()) {
        this.idee.titel = this.titel;
        this.idee.beschreibung = this.beschreibung;
        this.idee.vorteile = this.vorteile;
        this.idee.existiert = this.existiert;
        this.idee.ideenTyp = this.ideenTyp;
        this.idee.sparte = this.sparte;
        this.idee.vertriebsweg = this.vertriebsweg;
        this.idee.zielgruppe = this.zielgruppe;
        this.idee.handlungsfeld = this.handlungsfeld;
        console.log(this.idee);

        var axiosInstance = Helper.getInstance().createAxiosInstance();
        var jwt = require("jsonwebtoken");
        var decode = jwt.decode(this.token);

        const config = {
          headers: { Authorization: `Bearer ${this.token}` },
        };
        axiosInstance.post(
          "http://localhost:9090/idee",
          {
            titel: this.titel,
            beschreibung: this.beschreibung,
            bearbeitungsstatus: "ANGELEGT",
            typ: this.ideenTyp,
            erfasser: decode["sub"],
            vorteile: this.vorteile,
            existiertBereits: this.existiert,
            handlungsfeld: this.handlungsfeld,
            sparten: this.sparte,
            vertriebsweg: this.vertriebsweg,
            zielgruppe: this.zielgruppe,
          },
          config
        );
      }
    },
  },
  mounted() {
    this.$delete(this.vorteile, 0);
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
select,
.existiertInfo,
#beschreibungsTextExistiert,
#unternehmen {
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
.beschreibung,
#existiert {
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
.beschreibung textarea,
#beschreibungsTextExistiert {
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
