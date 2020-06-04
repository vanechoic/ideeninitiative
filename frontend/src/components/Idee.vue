<template>
  <div class="container">
    <!--<div class="kopfzeile" v-if="showDetails">-->
    <div class="kopfzeile">
      <label id="ideeName">{{ ideeTitel }}</label>
      <div class="erstellInfos grauer-text grid-row">
        <!--Ersteller-->
        <label for="ersteller" class="grid-item">Ersteller:</label>
        <div id="ersteller" class="grid-item">{{ ideeErsteller }}</div>
        <!--Erstell Datum-->
        <label class="grid-item">Erstellt am:</label>
        <div id="erstellDatum" class="grid-item">{{ ideeErstellt }}</div>
        <label for="typ" class="grid-item">Typ:</label>
        <div id="typ" class="grid-item">{{ ideeTyp | ideetyp}}</div>
        <label for="status" class="grid-item">Bearbeitungsstatus:</label>
        <div id="status" class="grid-item">{{ ideeBearbeitungszustand | status}}</div>
      </div>
    </div>
    <!--<div class="hauptteil" v-if="showDetails">-->
    <div class="hauptteil">
      <!--Beschreibung-->
      <label id="beschreibungLbl" for="beschreibung">Beschreibung</label>
      <div id="beschreibung">{{ ideeBeschreibung }}</div>
      <!--Vorteile-->
      <label id="vorteileLbl" for="vorteile">Vorteile</label>
      <ul id="vorteile">
        <li v-for="vorteil in ideeVorteile" :key="vorteil">{{ vorteil }}</li>
      </ul>
      <!--Existiert-->
      <div class="existiertDiv" v-if="showExistiert">
        <!--Unternehmen-->
        <label
          for="unternehmen"
          class="grauer-text"
        >Das Produkt ist bereits umgesetzt im Unternehmen</label>
        <div id="unternehmen">{{ ideeUnternehmen }}</div>
        <!--Beschreibung in welcher form es existiert-->
        <label for="beschreibungExistiert" class="grauer-text">auf folgende Art und Weise:</label>
        <div id="beschreibungExistiert">{{ ideeExistiertBeschreibung }}</div>
      </div>
      <!--Einblenden, wenn es eine interne Idee ist-->
      <!--Handlungsfeld-->
      <div class="handlungsfeld" v-if="ideeTyp == 'INTERNE_IDEE'">
        <label id="handlungsfeldLbl" for="handlungsfeld">Handlungsfeld</label>
        <div
          id="handlungsfeld"
          class="schriftgroesse-spezialisierung"
        >{{ ideeHandlungsfeld | handlungsfeld}}</div>
      </div>
      <!--Einblenden, wenn es eine Produktidee ist-->
      <div class="produktIdee" v-if="showProduktidee">
        <!--Sparte-->
        <div>
          <label for="sparte">Sparte</label>
          <div id="sparte" class="schriftgroesse-spezialisierung">{{ ideeSparte | sparte}}</div>
        </div>
        <!-- Anzeigen, wenn Vertriebskanal vorhanden ist-->
        <div v-if="showVertriebskanal">
          <!--Vertriebskanal-->
          <label for="vertriebskanal">Vertriebskanal</label>
          <ul id="vertriebskanal">
            <li
              v-for="vertriebskanal in ideeVertriebskanal"
              :key="vertriebskanal"
              class="schriftgroesse-spezialisierung"
            >{{ vertriebskanal | vertriebsweg}}</li>
          </ul>
        </div>
        <!--Zielgruppe-->
        <div>
          <label for="zielgruppe">Zielgruppe</label>
          <ul id="zielgruppe">
            <li
              v-for="zielgruppe in ideeZielgruppe"
              :key="zielgruppe"
              class="schriftgroesse-spezialisierung"
            >{{ zielgruppe | zielgruppe}}</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="fußzeile" v-if="showDetails == true && wirdBewertet==false ">
      <button class="roter-button" v-on:click="push()">Zurück</button>
      <button v-if="showButton" v-on:click="(showModal = true), (showDetails = false)">Zur Bewertung</button>
    </div>

    <!--Modal mit der Bewertung. Wird nur angezeigt, wenn bewertungBtn betätigt wird-->
    <transition name="fade" appear>
      <div class="modal-overlay" v-if="showModal">
        <div class="kopfzeile">
          <label id="bewertungLbl">
            Bewertung
            <!--{{}}-->
          </label>
        </div>
        <div class="hauptteil">
          <!--Status der Idee-->
          <div class="angenommen" v-if="showAngenommen">
            <p>{{ ideeBearbeitungszustand | status}}</p>
          </div>
          <div class="abgelehnt" v-if="showAbgelehnt">
            <p>{{ ideeBearbeitungszustand | status}}</p>
          </div>
          <div class="nichtBewertet" v-if="showNichtBewertet">
            <p>{{ ideeBearbeitungszustand | status}}</p>
          </div>
          <!--Bewertung der Idee-->
          <div id="bewertung">
            <!--{{}}-->
            {{ ideeBegruendung }}
          </div>
        </div>
        <div class="fußzeile">
          <button class="roter-button" v-on:click="(showModal = false), (showDetails = true)">Zurück</button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import { Params } from "../services/params-service";

export default Vue.extend({
  name: "Idee",
  data: () => ({
    // Modal Attribute
    showModal: false,
    showDetails: true,
    showButton: true,
    showVertriebskanal: false,
    showExistiert: false,
    showIntern: true,
    showProduktidee: false,
    showAngenommen: false,
    showAbgelehnt: false,
    showNichtBewertet: true,
    wirdBewertet: true,
    // Idee-Objekt
    idee: {},
    // Seitenattribute für Ideendetails
    ideeTitel: "",
    ideeErsteller: "",
    ideeErstellt: "",
    ideeBeschreibung: "",
    ideeVorteile: [{}],
    ideeExistiert: "",
    ideeUnternehmen: "",
    ideeExistiertBeschreibung: "",
    ideeTyp: "",
    ideeSparte: "",
    ideeVertriebskanal: [{}],
    ideeZielgruppe: [{}],
    ideeHandlungsfeld: "",
    ideeBearbeitungszustand: "",
    ideeBegruendung: "",
  }),
  methods: {
    push: function () {
      localStorage.removeItem("idee");
      if (window.history.length > 1) this.$router.go(-1);
    },
  },
  mounted() {
    this.idee = JSON.parse(localStorage.getItem("idee") as string);
    var etc = localStorage.getItem("bewerten" as string);
    console.log(etc);
    if (etc) this.wirdBewertet = false;

    this.ideeTyp = (this.idee as any).typ;
    this.ideeExistiert = (this.idee as any).existiertBereits;
    this.ideeBegruendung = (this.idee as any).begruendung;
    this.ideeBearbeitungszustand = (this.idee as any).bearbeitungsstatus;

    if (this.ideeBegruendung == null || this.ideeBegruendung == "")
      this.ideeBegruendung == "Keine Begründung angegeben";

    if (this.bearbeitungsstatus == null)
      this.ideeBegruendung == "Noch nicht bewertet";

    if (this.ideeTyp == "PRODUKTIDEE") {
      if (this.ideeExistiert) this.showExistiert = true;
      this.showVertriebskanal = true;
      this.showIntern = true;
      this.showProduktidee = true;
    } else {
      this.showVertriebskanal = false;
      this.showExistiert = false;
      this.showIntern = true;
      this.showProduktidee = false;
    }

    this.ideeTitel = (this.idee as any).titel;
    this.ideeErsteller = (this.idee as any).erfasser;
    this.ideeErstellt = (this.idee as any).erstellzeitpunkt;
    this.ideeBeschreibung = (this.idee as any).beschreibung;
    this.ideeVorteile = (this.idee as any).vorteile;
    this.ideeUnternehmen = (this.idee as any).unternehmensbezeichnung;
    this.ideeExistiertBeschreibung = (this.idee as any).artDerUmsetzung;
    this.ideeSparte = (this.idee as any).sparten;
    this.ideeVertriebskanal = (this.idee as any).vertriebsweg;
    this.ideeZielgruppe = (this.idee as any).zielgruppe;
    this.ideeHandlungsfeld = (this.idee as any).handlungsfeld;
    localStorage.removeItem("bewerten");
  },
});
</script>

<style lang="scss" scoped>
li,
#beschreibung,
#ersteller,
#erstellDatum,
#handlungsfeld,
#sparte,
#status,
#beschreibungExistiert,
#unternehmen,
#bewertung {
  color: black;
}
.container,
#beschreibung,
.modal-overlay,
#bewertung,
#beschreibungExistiert {
  border-radius: 20px;
}
.hauptteil,
.fußzeile .container {
  position: relative;
}
.modal-overlay {
  padding: 2%;
  margin: auto;
  position: relative;
  width: 80%;
  top: -40rem;
  height: auto;
  background: linear-gradient(to bottom, #efefef, #ccc);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}
.container{
  width: 100%;
  background: linear-gradient(to bottom, #efefef, #ccc);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  height: 100%;
}
.erstellInfos,
#ideeName,
.produktIdee,
.handlungsfeld,
.fußzeile,
#bewertungLbl {
  display: flex;
}
.erstellInfos,
#ideeName,
.fußzeile,
#bewertungLbl {
  justify-content: space-evenly;
}
.produktIdee {
  justify-content: space-evenly;
}
#beschreibung,
#beschreibungExistiert,
#bewertung {
  display: block;
  word-wrap: break-word;
  padding: 0.2% 1%;
  overflow: scroll;
  overflow: hidden;
}
#ideeName,
#bewertungLbl {
  flex-direction: column;
  align-items: center;
  font-size: 1.5rem;
}
.existiertDiv,
.produktIdee,
#status,
ul,
#handlungsfeld,
#handlungsfeldLbl,
#unternehmen {
  margin-left: 2%;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
button {
  margin-top: 10px;
}
li {
  line-height: 150%;
  margin: 0.5em;
  text-indent: 0;
}
ul {
  list-style: none;
  overflow: auto;
  padding: 0;
  height: 80px;
}
.fußzeile {
  padding: 1%;
}
.modal-overlay {
  position: absolute;
  padding: 2%;
  margin: auto;
  top: 2%;
  left: 0;
  right: 0;
  width: 80%;

}
#bewertung,
.abgelehnt,
.angenommen,
.nichtBewertet {
  margin: 5% 0;
}
p {
  color: black;
}
.abgelehnt {
  color: #f80303;
}
.angenommen {
  color: #00894d;
}
#app {
  position: relative;
}
#ideeName {
  line-height: 3;
}
.grid-row {
  display: grid;
  grid-template-columns: 20% 30% 20% 30%;
  grid-template-rows: 50%;
}
.container {
  padding-left: 30px;
  padding-right: 30px;
}
.schriftgroesse-spezialisierung {
  font-size: 0.9rem;
}
.handlungsfeld,
.produktIdee,
#beschreibungLbl,
#vorteileLbl {
  margin-top: 3%;
}
</style>
