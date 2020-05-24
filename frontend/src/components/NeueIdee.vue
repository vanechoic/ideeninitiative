<template>
  <article>
    <div class="container-fluid" name="NeueIdeeContainer">
      <h1>Neue Idee</h1>
      <div class="row">
        <!-- Ideen Titel/Name -->
        <div class="titel">
          <h2>Titel</h2>
          <input type="text" id="titel" v-model="titel" />
        </div>
      </div>
      <div class="row">
        <!-- Beschreibungstext -->
        <div class="beschreibung">
          <h2>Beschreibungstext</h2>
          <textarea id="beschreibungsText" v-model="beschreibung"></textarea>
        </div>
      </div>
      <div class="row">
        <!-- Datei Upload -->
        <div class="dateien">
          <h2>Datei Upload</h2>
          <input type="file" />
        </div>
      </div>
      <div class="row">
        <!-- Vorteile -->
        <div class="vorteile">
          <h2>Vorteile</h2>
          <input id="vorteile" type="text" v-model="vorteilText" />
          <button class="ideeButton" id="hinzu" @click="vorteilHinzufuegen()">+</button>
          <button class="ideeButton" id="entfernen" @click="vorteilEntfernen()">-</button>
          <br />
          <select v-model="selected" multiple id="selectVorteile">
            <option
              v-for="vorteil in Vorteile"
              :key="vorteil.vorteil"
              @click="vorteilSelection(vorteil)"
            >{{ vorteil.vorteil }}</option>
          </select>
        </div>
      </div>
      <div class="row">
        <!-- Existierende Idee - Checkbox -->
        <div class="existiert" v-bind:class="[ existiertAktiv ]">
          <label for="existiertBereits">Existiert eine vergleichbare Idee?</label>
          <input type="checkbox" v-model="existiert" id="existiertBereits" />
        </div>
      </div>
      <div class="row">
        <!--Comboboxen zur Ideen-Spezifikation -->
        <div class="ideenDropdowns">
          <div class="combobox">
            <p>Ideen-Typ</p>
            <select v-model="ideenTyp" @click="ideeSelection()">
              <option value="Produktidee" selected>Produktidee</option>
              <option value="Interne Idee">Interne Idee</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[ sparteAktiv ]">
            <p>Sparte</p>
            <select v-model="sparte">
              <option>KFZ</option>
              <option>Unfall</option>
              <option>Krankenversicherung</option>
              <option>Rechtsschutz</option>
              <option>Lebensversicherung</option>
              <option>Rentenversicherung</option>
              <option>Haftpflicht</option>
              <option>Hausrat</option>
              <option>Wohngebäudeversicherung</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[ vertriebswegAktiv ]">
            <p>Vertriebsweg</p>
            <select v-model="vertriebsweg" multiple>
              <option>Stationärer Vertrieb in eigenen Geschäftsstelle</option>
              <option>Versicherungsmakler</option>
              <option>Kooperation mit Kreditinstituten</option>
              <option>Direktversicherung</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[ zielgruppeAktiv ]">
            <p>Zielgruppen</p>
            <select v-model="zielgruppe" multiple>
              <option selected>Kinder/Jugendliche</option>
              <option>Familien</option>
              <option>Singles</option>
              <option>Paare</option>
              <option>Personen 50+</option>
              <option>Gewerbetreibende</option>
            </select>
          </div>
          <div class="combobox" v-bind:class="[ handlungsfelderAktiv ]">
            <p>Handlungsfelder</p>
            <select v-model="handlungsfeld">
              <option>Kostensenkung</option>
              <option>Ertragssteigerung</option>
              <option>Zukunftsfähigkeit</option>
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
export default Vue.extend({
  data: () => ({
    // Idee-Objekt
    idee: {
      titel: "String",
      beschreibung: "String",
      vorteile: [{ vorteil: "" }],
      existiert: false,
      ideenTyp: "String",
      sparte: "String",
      vertriebsweg: [{ id: 0, value: "" }],
      zielgruppe: [{ id: 0, value: "" }],
      handlungsfeld: "String",
    },
    // Beschreibende Attribute
    titel: "",
    beschreibung: "",
    existiert: false,
    // Data für Vorteile und Vorteilslogik
    counter: 0,
    vorteilText: "",
    Vorteile: [{ vorteil: "Vorteil" }],
    selected: "",
    selectedIndex: 0,
    // Data für Comboboxen und Comboboxlogik
    ideenTyp: "",
    sparte: "",
    vertriebsweg: [{ id: 1, value: "" }],
    zielgruppe: [{ id: 1, value: "" }],
    handlungsfeld: "",
    // Aktivieren/Deaktivieren
    sparteAktiv: "aktiv",
    vertriebswegAktiv: "aktiv",
    zielgruppeAktiv: "aktiv",
    handlungsfelderAktiv: "aktiv",
    existiertAktiv: "aktiv",
    // Bootstrap Alert Variablen
    showSuccess: false
  }),
  methods: {
    // Methode für Abbrechen-Button => Zurück im Browser
    goBack() {
      if (window.history.length > 1) this.$router.go(-1);
    },
    // Logik für das Hinzufügen/Entfernen von Vorteilen
    vorteilHinzufuegen() {
      if (this.counter < 3 && this.vorteilText != "") {
        this.Vorteile.push({ vorteil: this.vorteilText });
        this.counter++;
      } else if (this.counter > 2)
        alert("Pro Idee können nur 3 Ideen hinzugefügt werden");
      else alert("Eingabefeld darf nicht leer sein");
    },
    vorteilEntfernen() {
      if (this.counter) {
        this.$delete(this.Vorteile, this.selectedIndex);
        this.counter--;
        if (this.counter < 0) this.counter = 0;
      }
    },
    vorteilSelection(selected: { id: number; vorteil: string }) {
      this.selectedIndex = this.Vorteile.indexOf(selected);
    },
    // Logik für das Auswählen/Ausblenden von Comboboxen
    ideeSelection() {
      if (this.ideenTyp == "Produktidee") {
        this.handlungsfelderAktiv = "inaktiv";
        (this.sparteAktiv = "aktiv"),
          (this.vertriebswegAktiv = "aktiv"),
          (this.zielgruppeAktiv = "aktiv"),
          (this.existiertAktiv = "aktiv");
      } else {
        this.handlungsfelderAktiv = "aktiv";
        (this.sparteAktiv = "inaktiv"),
          (this.vertriebswegAktiv = "inaktiv"),
          (this.zielgruppeAktiv = "inaktiv"),
          (this.existiertAktiv = "inaktiv");
      }
    },
    // Eingaben überprüfen und Idee speichern
    validiereEingaben(): boolean {
      // NOCH NICHT FERTIG
      var errorMessage = "Angaben ungültig: ";
      var error = true;
      if (this.ideenTyp == "Interne Idee" && this.handlungsfeld.length == 0) {
        errorMessage += "Mindestens eine Zielgruppe auswählen \n";
        error = true;
      }
      return error;
    },
    ideeSpeichern() {
      if (this.validiereEingaben()) {
        this.idee.titel = this.titel;
        this.idee.beschreibung = this.beschreibung;
        this.idee.vorteile = this.Vorteile;
        this.idee.existiert = this.existiert;
        this.idee.ideenTyp = this.ideenTyp;
        this.idee.sparte = this.sparte;
        this.idee.vertriebsweg = this.vertriebsweg;
        this.idee.zielgruppe = this.zielgruppe;
        this.idee.handlungsfeld = this.handlungsfeld;
        console.log(this.idee);
      }
    }
  },
  mounted() {
    this.$delete(this.Vorteile, 0);
  },
});
</script>

<style lang="scss" scoped>
.container-fluid {
  float: left;
  justify-content: space-around;
  height: calc(100% - 20px);
  width: 800px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
  .vorteile button {
    padding: 5px;
    margin: 5px;
  }
  h1 {
    text-align: center;
    margin: 1%;
  }
  label {
    font-size: 1.1em;
    // margin-right: 30px;
  }
  .beschreibung {
    top: 2%;
    // margin: auto;
    left: 0;
    right: 0;
    height: 250px;
  }
  .beschreibung textarea {
    //resize: none;
    width: 100%;
    height: 80%;
  }
  .ideeButton {
    width: 30px;
    height: 30px;
  }
  .dateien {
    // margin: 20px;
  }
  .vorteile {
    //margin: 20px;
  }
  #vorteileAnzeigen {
    //resize: none;
    width: 30%;
  }
  #vorteile {
    width: 30%;
  }
  #selectVorteile {
    overflow: hidden;
  }
  #titel {
    width: 30%;
  }
  .existiert {
    //margin: 30px;
    margin-bottom: 0px;
  }
  .buttons {
    float: right;
    //margin: 2%;
  }
  #hinzu {
    background-color: #00894d;
    color: #fff;
  }
  #entfernen {
    background-color: #f80303;
    color: #fff;
    margin-right: 2px;
  }
}
button {
  border-radius: 20px;
  border: 1px solid #fff;
  color: rgb(0, 0, 0);
  font-size: 1rem;
  font-weight: bold;
  padding: 10px 40px;
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
p,
label {
  font-size: 0.75em;
}
.combobox {
  float: left;
  //margin: 35px;
}
select {
  width: 5em;
}
.inaktiv {
  display: none;
}
.aktiv {
  display: inline;
}
</style>