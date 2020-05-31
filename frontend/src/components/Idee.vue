<template>
  <div class="container">
    <div class="kopfzeile" v-if="showDetails">
      <label id="ideeName">
        {{ ideeTitel }}
      </label>
      <div class="erstellInfos">
        <!--Ersteller-->
        <label class="erstellerLbl" for="ersteller">Ersteller:</label>
        <div id="ersteller">
          {{ ideeErsteller }}
        </div>
        <!--Erstell Datum-->
        <label id="erstellDatumLbl">Erstellt am:</label>
        <div id="erstellDatum">
          {{ ideeErstellt }}
        </div>
      </div>
    </div>
    <div class="hauptteil" v-if="showDetails">
      <!--Beschreibung-->
      <div class="beschreibung">
        <label id="beschreibungLbl" for="beschreibung">Beschreibung:</label>
        <div id="beschreibung">
          {{ ideeBeschreibung }}
        </div>
      </div>
      <!--Vorteile-->
      <label id="vorteileLbl" for="vorteile">Vorteile:</label>
      <ul id="vorteile" v-for="vorteil in ideeVorteile" :key="vorteil">
        <li>{{ vorteil }}</li>
      </ul>
      <!--Existiert-->
      <div class="existiert" v-if="showExistiert">
        <label id="existiertLbl" for="existiertDiv">Existiert: {{ ideeExistiert }}</label>
        <div id="existiertDiv">
          <!--Unternehmen-->
          <label id="unternehmenLbl" for="unternehmen">Unternehmen:</label>
          <div id="unternehmen">
            {{ ideeUnternehmen }}
          </div>
          <!--Beschreibung in welcher form es existiert-->
          <label id="beschreibungExistiertLbl" for="beschreibungExistiert"></label>
          <div id="beschreibungExistiert">
            {{ ideeExistiertBeschreibung }}
          </div>
        </div>
      </div>
      <!--IdeeTyp-->
      <div class="ideeTyp">
        <label id="ideeTypLbl" for="ideeTyp">Idee Typ:</label>
        <div id="ideeTyp">
          {{ ideeTyp }}
        </div>
      </div>
      <!--Einblenden, wenn es eine interne Idee ist-->
      <div class="intern" v-if="showIntern">
        <!--Handlungsfeld-->
        <div class="handlungsfeld">
          <label id="handlungsfeldLbl" for="handlungsfeld">Handlungsfeld:</label>
          <div id="handlungsfeld">
            {{ ideeHandlungsfeld }}
          </div>
        </div>
      </div>
      <!--Einblenden, wenn es eine Produktidee ist-->
      <div class="produktIdee" v-if="showProduktidee">
        <!--Sparte-->
        <div class="sparte">
          <label id="sparteLbl" for="sparte">Sparte:</label>
          <div id="sparte">
            {{ ideeSparte }}
          </div>
        </div>
        <!-- Anzeigen, wenn Vertriebskanal vorhanden ist-->
        <div class="vertriebskanal" v-if="showVertriebskanal">
          <!--Vertriebskanal-->
          <label id="vertriebskanalLbl" for="vertriebskanal">Vertriebskanal:</label>
          <ul
            id="vertriebskanal"
            v-for="vertriebskanal in ideeVertriebskanal"
            :key="vertriebskanal"
          >
            <li>{{ vertriebskanal }}</li>
          </ul>
        </div>
        <!--Zielgruppe-->
        <div class="zielgruppe">
          <label id="zielgruppeLbl" for="zielgruppe">Zielgruppe:</label>
          <ul id="zielgruppe" v-for="zielgruppe in ideeZielgruppe" :key="zielgruppe">
            <li>{{ zielgruppe }}</li>
          </ul>
        </div>
      </div>
      <!--Status-->
      <label id="statusLbl" for="status">Status:</label>
      <div id="status">{{ ideeBearbeitungszustand }}</div>
    </div>
    <div class="fußzeile" v-if="showDetails == true && wirdBewertet==false ">
      <button class="zurueckBtn" v-on:click="push()">Zurück</button>
      <button
        class="bewertungBtn"
        v-if="showButton"
        v-on:click="(showModal = true), (showDetails = false)"
      >Zur Bewertung</button>
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
            <p>Diese Idee wurde angenommen.</p>
          </div>
          <div class="abgelehnt" v-if="showAbgelehnt">
            <p>Diese Idee wurde abgelehnt.</p>
          </div>
          <div class="nichtBewertet" v-if="showNichtBewertet">
            <p>Diese Idee wird noch bearbeitet.</p>
          </div>
          <!--Bewertung der Idee-->
          <div id="bewertung">
            <!--{{}}-->
            Hier steht eine tolle Beschreibung
            adadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
          </div>
        </div>
        <div class="fußzeile">
          <button class="zurueckBtn" v-on:click="(showModal = false), (showDetails = true)">Zurück</button>
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
  }),
  methods: {
    push: function () {
      localStorage.removeItem("idee");
      this.$router.push({ path: "/Startseite" });
    },
  },
  mounted() {
    this.idee = JSON.parse(localStorage.getItem("idee") as string);
    var etc = localStorage.getItem("bewerten")
    if (etc)
      this.wirdBewertet = false;
    console.log(this.idee);

    this.ideeTyp = (this.idee as any).typ;
    this.ideeExistiert = (this.idee as any).existiertBereits;

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
    this.ideeBearbeitungszustand = (this.idee as any).bearbeitungsstatus;
    localStorage.removeItem("bewerten")
  },
});
</script>

<style lang="scss" scoped>
li,
#beschreibung,
#ersteller,
#erstellDatum,
#ideeTyp,
#handlungsfeld,
#sparte,
#status,
#beschreibungExistiert,
#unternehmen,
#bewertung {
  color: black;
}
button,
.container,
#beschreibung,
.modal-overlay,
#bewertung {
  border-radius: 20px;
}
.hauptteil,
.fußzeile .container,
.modal-overlay {
  position: relative;
}
.container,
.modal-overlay {
  width: 100%;
  background: linear-gradient(to bottom, #efefef, #ccc);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
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
.produktIdee,
.fußzeile,
#bewertungLbl {
  justify-content: space-between;
}
#beschreibung,
#beschreibungExistiert,
#bewertung {
  display: block;
  background-color: #fff;
  word-wrap: break-word;
  padding: 0.2% 1%;
  overflow: scroll;
  border: 0.5px solid #000;
}
#ideeName,
#bewertungLbl {
  flex-direction: column;
  align-items: center;
  font-size: 1.5rem;
}
#existiertDiv,
.intern,
.produktIdee,
#status,
ul,
#ideeTyp,
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
.zurueckBtn {
  background-color: #f80303;
  right: 5%;
}
button {
  border: 1px solid #fff;
  color: #fff;
  font-size: 0.75rem;
  font-weight: bold;
  padding: 8px 20px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  background-color: #00894d;
  transition: transform 0.1s ease-in;
  &:active {
    transform: scale(0.9);
  }
  &:focus {
    outline: none;
  }
}
li {
  line-height: 150%;
}
ul {
  list-style: none;
  overflow: auto;
  text-indent: 10px;
  padding: 0;
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
</style>
