<template>
  <div class="container">
    <h2 v-if="showDetails">Bewertung</h2>
    <div class="idee">
      <component v-bind:is="component" v-if="showDetails"></component>
    </div>
    <div class="bewertungsTeil" v-if="showDetails">
      <button id="späterBtn" v-on:click="push()">Später bewerten</button>
      <button id="jetztBewertenBtn" v-on:click="showModal=true, showDetails=false">Jetzt bewerten</button>
    </div>
    <!-- Modal.....-->
    <transition name="fade" appear>
      <div class="modal-overlay" v-if="showModal">
        <div class="kopfzeile">
          <h2>Bewertung</h2>
        </div>
        <div class="hauptteil">
          <!--Idee annehmen oder ablehnen-->
          <div class="entscheidung">
            <div class="annehmen">
              <label for="ideeAnnehnmen">Idee annehnmen</label>
              <input
                type="radio"
                id="ideeAnnehmen"
                name="bewerten"
                value="AKZEPTIERT"
                v-model="radiobutton"
              />
            </div>
            <div class="ablehnen">
              <label for="ideeAblehnen">Idee ablehnen</label>
              <input
                type="radio"
                id="ideeAblehnen"
                name="bewerten"
                value="ABGELEHNT"
                v-model="radiobutton"
              />
            </div>
          </div>
          <!--Bewertung der Idee-->
          <div class="bewertung">
            <label for="bewertung">Begründung:</label>
            <textarea id="bewertung" v-model="begruendung"></textarea>
          </div>
        </div>
        <div class="fußzeile">
          <button class="zurueckBtn" v-on:click="(showModal = false), (showDetails = true)">Zurück</button>
          <button
            class="bewertenBtn"
            v-on:click="(showModal = false), (showDetails = true), bewertungVeroeffentlichen()"
          >Bewertung veröffentlichen</button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import Idee from "@/components/Idee.vue";
import { Params } from "../services/params-service";
import { Helper } from "../services/helper";

export default Vue.extend({
  name: "Ideebewerten",
  components: {
    idee: Idee,
  },
  data: () => ({
    // Auth Token
    token: localStorage.getItem("token"),
    // Modalfenster und Componentenlogik
    showModal: false,
    component: "idee",
    showDetails: true,
    // Ideevariablen
    ideeObjekt: {},
    Ideen: [],
    titel: "",
    beschreibung: "",
    existiert: false,
    unternehmen: "",
    beschreibungEx: "",
    vorteile: [],
    ideenTyp: "",
    sparte: "",
    vertriebsweg: [],
    zielgruppe: [],
    handlungsfeld: "",
    // Spezialist Variablen
    radiobutton: "",
    begruendung: "",
  }),
  asyncComputed: {
    // a computed getter
    async getIdeen() {
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      var ideenListe = await axiosInstance
        .get("http://localhost:9090/idee/zugewiesene", config)
        .then((res) => {
          return res.data || [];
        })
        .catch((err) => {
          console.log(err);
        });
      if (ideenListe) {
        localStorage.setItem("idee", JSON.stringify(ideenListe[0]));
        this.$forceUpdate();

        this.ideeObjekt = ideenListe[0];
        if ((this.ideeObjekt as any).existiertBereit) this.existiert = true;
        else this.existiert = false;
        this.ideeTyp = (this.ideeObjekt as any).typ;
        this.titel = (this.ideeObjekt as any).titel;
        this.erfasser = (this.ideeObjekt as any).erfasser;
        this.beschreibung = (this.ideeObjekt as any).beschreibung;
        this.vorteile = (this.ideeObjekt as any).vorteile;
        this.unternehmen = (this.ideeObjekt as any).unternehmensbezeichnung;
        this.beschreibungEx = (this.ideeObjekt as any).artDerUmsetzung;
        this.sparte = (this.ideeObjekt as any).sparten;
        this.vertriebsweg = (this.ideeObjekt as any).vertriebsweg;
        this.zielgruppe = (this.ideeObjekt as any).zielgruppe;
        this.handlungsfeld = (this.ideeObjekt as any).handlungsfeld;
        this.ideeBearbeitungszustand = (this.ideeObjekt as any).bearbeitungsstatus;
        this.erstelldatum = (this.ideeObjekt as any).erstellzeitpunkt;
        if (this.vorteile == null) this.vorteile = [];
      }
      return ideenListe;
    },
  },
  methods: {
    push: function () {
      this.$router.push({ path: "/Startseite" });
    },
    bewertungVeroeffentlichen() {
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };
      var axiosInstance = Helper.getInstance().createAxiosInstance();

      axiosInstance.put(
        "http://localhost:9090/idee",
        {
          typ: this.ideeTyp,
          existiertBereits: this.existiert,
          titel: this.titel,
          erfasser: this.erfasser,
          erstellzeitpunkt: this.erstelldatum,
          beschreibung: this.beschreibung,
          vorteile: this.vorteile,
          unternehmensbezeichnung: this.unternehmensbezeichnung,
          artDerUmsetzung: this.beschreibungEx,
          sparten: this.sparte,
          vertriebsweg: this.vertriebsweg,
          zielgruppe: this.zielgruppe,
          handlungsfeld: this.handlungsfeld,
          // Daten vom Spezialisten
          bearbeitungsstatus: this.radiobutton,
          begruendung: this.begruendung,
        },
        config
      );
    },
  },
});
</script>

<style lang="scss" scoped>
.container {
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  position: relative;
}
.bewertungsTeil {
  position: relative;
  display: flex;
  justify-content: space-between;
  margin: 3%;
}
h2 {
  width: 100%;
  margin: auto;
  margin-bottom: 4%;
  text-align: center;
  position: relative;
}
.idee {
  text-align: left;
  position: relative;
  border: 1px solid rgb(99, 91, 91);
  padding: 1%;
}
.modal-overlay {
  position: relative;
  width: 900px;
}
#bewertung {
  width: 100%;
  height: 400px;
}
.hauptteil {
  width: 100%;
}
.fußzeile,
.annehmen,
.ablehnen,
.entscheidung {
  display: flex;
}
.fußzeile,
.annehmen,
.ablehnen {
  justify-content: space-between;
}
button {
  border: 1px solid #fff;
  font-weight: bold;
  padding: 2px 8px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  color: #fff;
  transition: transform 0.1s ease-in;
  &:active {
    transform: scale(0.9);
  }
  &:focus {
    outline: none;
  }
}
.entscheidung {
  justify-content: space-around;
}
.annehmen {
  color: #00894d;
  width: 30%;
}
.ablehnen {
  color: #f80303;
  width: 30%;
}
button,
#bewertung,
.modal-overlay,
.container,
.idee {
  border-radius: 20px;
}
.container,
.modal-overlay {
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.zurueckBtn {
  background-color: #f80303;
}
.bewertenBtn {
  background-color: #00894d;
}
.fußzeile {
  padding: 1%;
}
#ideeAnnehmen,
#ideeAblehnen {
  height: 20px;
  width: 20px;
  margin-left: 10px;
  margin-top: 2px;
}
#jetztBewertenBtn {
  background-color: #00894d;
}
#späterBtn {
  background-color: black;
}
</style>
