<template>
  <div class="container">
    <h2 v-if="showDetails">Bewertung</h2>
    <div class="idee" v-if="showDetails">
      <component v-bind:is="component" :key="componentKey"></component>
    </div>
    <div class="bewertungsTeil" v-if="showDetails">
      <button class="roter-button" v-on:click="goBack()">Später bewerten</button>
      <button
        v-on:click="showModal=true, showDetails=false, ideeladen()"
      >Jetzt bewerten</button>
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
              <label for="ideeAnnehnmen">Idee annehmen</label>
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
          <button class="roter-button" v-on:click="(showModal = false), (showDetails = true)">Zurück</button>
          <button
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
    // Ideevariablen
    ideeObjekt: {},
    // Modalfenster und Componentenlogik
    showModal: false,
    component: "idee",
    showDetails: true,
    componentKey: 0,
    Ideen: [],
    titel: "",
    beschreibung: "",
    existiert: false,
    unternehmen: "",
    beschreibungEx: "",
    vorteile: [],
    ideeTyp: "",
    sparte: "",
    vertriebsweg: [],
    zielgruppe: [],
    handlungsfeld: "",
    erfasser: "",
    erstelldatum: "",
    unternehmensbezeichnung: "",
    fachspezialist: "",
    bearbeitungsstatus: "",
    // Spezialist Variablen
    radiobutton: "",
    begruendung: "",
    baseUrl: process.env.VUE_APP_BACKEND_BASE_URL,
  }),
  methods: {
    bewertungVeroeffentlichen() {
      var jwt = require("jsonwebtoken");
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };
      var axiosInstance = Helper.getInstance().createAxiosInstance();

      axiosInstance
        .put(
          this.baseUrl + "/idee",
          {
            typ: this.ideeTyp,
            existiertBereits: this.existiert,
            titel: this.titel,
            erfasser: this.erfasser,
            fachspezialist: this.fachspezialist,
            erstellzeitpunkt: this.erstelldatum,
            beschreibung: this.beschreibung,
            vorteile: this.vorteile,
            unternehmensbezeichnung: this.unternehmensbezeichnung,
            artDerUmsetzung: this.beschreibungEx,
            sparten: this.sparte,
            vertriebsweg: this.vertriebsweg,
            zielgruppe: this.zielgruppe,
            handlungsfeld: this.handlungsfeld,
            bearbeitungsstatus: this.radiobutton,
            begruendung: this.begruendung,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Idee bewertet");
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim bewerten",
            "error"
          )
        );
      this.$router.push("Startseite");
    },
    goBack() {
      if (window.history.length > 1) this.$router.go(-1);
    },
    ideeladen() {
      this.ideeObjekt = JSON.parse(localStorage.getItem("idee") as string);

      if ((this.ideeObjekt as any).existiertBereit) this.existiert = true;
      else this.existiert = false;

      this.ideeTyp = (this.ideeObjekt as any).typ;
      this.titel = (this.ideeObjekt as any).titel;
      this.erfasser = (this.ideeObjekt as any).erfasser;
      this.fachspezialist = (this.ideeObjekt as any).fachspezialist;
      this.beschreibung = (this.ideeObjekt as any).beschreibung;
      this.vorteile = (this.ideeObjekt as any).vorteile;
      this.unternehmen = (this.ideeObjekt as any).unternehmensbezeichnung;
      this.beschreibungEx = (this.ideeObjekt as any).artDerUmsetzung;
      this.sparte = (this.ideeObjekt as any).sparten;
      this.vertriebsweg = (this.ideeObjekt as any).vertriebsweg;
      this.zielgruppe = (this.ideeObjekt as any).zielgruppe;
      this.handlungsfeld = (this.ideeObjekt as any).handlungsfeld;
      this.erstelldatum = (this.ideeObjekt as any).erstellzeitpunkt;
      if (this.vorteile == null) this.vorteile = [];
    },
  },
});
</script>

<style lang="scss" scoped>
.container {
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.bewertungsTeil {
  position: relative;
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 3%;
}
h2 {
  line-height: 3;
  margin-top: inherit;
}
.idee {
  text-align: left;
  position: relative;
  padding: 1%;
  flex: 1;
}
.modal-overlay {
  position: relative;
  //width: 900px;
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
button {
  margin-top: 10px;
  width: 40%;
}
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
</style>
