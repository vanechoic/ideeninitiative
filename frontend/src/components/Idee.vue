<template>
  <div class="container">
    <div class="kopfzeile" v-if="showDetails">
      <label id="ideeName">
        Idee Name
        <!--{{}}-->
      </label>
      <div class="erstellInfos">
        <!--Ersteller-->
        <label class="erstellerLbl" for="ersteller">Ersteller:</label>
        <div id="ersteller">
          <!--{{}}-->
          gggg
        </div>
        <!--Erstell Datum-->
        <label id="erstellDatumLbl">Erstellt am:</label>
        <div id="erstellDatum">
          <!--{{}}-->
          20.12.20
        </div>
      </div>
    </div>
    <div class="hauptteil" v-if="showDetails">
      <!--Beschreibung-->
      <div class="beschreibung">
        <label id="beschreibungLbl" for="beschreibung">Beschreibung:</label>
        <div id="beschreibung">
          <!--{{}}-->
          adadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
        </div>
      </div>
      <!--Vorteile-->
      <label id="vorteileLbl" for="vorteile">Vorteile:</label>
      <ul id="vorteile">
        <li>{{}}</li>
      </ul>
      <!--Existiert-->
      <div class="existiert" v-if="showExistiert">
        <label id="existiertLbl" for="existiertDiv">Existiert:</label>
        <div id="existiertDiv">
          <!--Unternehmen-->
          <label id="unternehmenLbl" for="unternehmen">Unternehmen:</label>
          <div id="unternehmen">
            <!--{{}}-->
            Nils ist cool GmbH
          </div>
          <!--Beschreibung in welcher form es existiert-->
          <label id="beschreibungExistiertLbl" for="beschreibungExistiert"
            >Beschreibung der existierenden Idee:</label
          >
          <div id="beschreibungExistiert">
            <!--{{}}-->
            Hier steht eine tolle Beschreibung
            adadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
          </div>
        </div>
      </div>
      <!--IdeeTyp-->
      <div class="ideeTyp">
        <label id="ideeTypLbl" for="ideeTyp">Idee Typ:</label>
        <div id="ideeTyp">
          <!--{{}}-->
          Intern
        </div>
      </div>
      <!--Einblenden, wenn es eine interne Idee ist-->
      <div class="intern" v-if="showIntern">
        <!--Handlungsfeld-->
        <div class="handlungsfeld">
          <label id="handlungsfeldLbl" for="handlungsfeld"
            >Handlungsfeld:</label
          >
          <div id="handlungsfeld">
            <!--{{}}-->
            Handlungsfeld X
          </div>
        </div>
      </div>
      <!--Einblenden, wenn es eine Produktidee ist-->
      <div class="produktIdee" v-if="showProduktidee">
        <!--Sparte-->
        <div class="sparte">
          <label id="sparteLbl" for="sparte">Sparte:</label>
          <div id="sparte">
            <!--{{}}-->
            Sparte X
          </div>
        </div>
        <!-- Anzeigen, wenn Vertriebskanal vorhanden ist-->
        <div class="vertriebskanal" v-if="showVertriebskanal">
          <!--Vertriebskanal-->
          <label id="vertriebskanalLbl" for="vertriebskanal"
            >Vertriebskanal:</label
          >
          <ul id="vertriebskanal">
            <li>{{}}</li>
          </ul>
        </div>
        <!--Zielgruppe-->
        <div class="zielgruppe">
          <label id="zielgruppeLbl" for="zielgruppe">Zielgruppe:</label>
          <ul id="zielgruppe">
            <li>{{}}</li>
          </ul>
        </div>
      </div>
      <!--Status-->
      <label id="statusLbl" for="status">Status:</label>
      <div id="status">{{}} Angenommen</div>
    </div>
    <div class="fußzeile" v-if="showDetails">
      <button class="zurueckBtn" v-on:click="push()">Zurück</button>
      <button
        class="bewertungBtn"
        v-if="showButton"
        v-on:click="(showModal = true), (showDetails = false)"
      >
        Zur Bewertung
      </button>
      <!--Problem mit 2 Variablen-->
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
          <button
            class="zurueckBtn"
            v-on:click="(showModal = false), (showDetails = true)"
          >
            Zurück
          </button>
          <!--Problem mit 2 Variablen-->
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import { properties } from "@syncfusion/ej2-vue-dropdowns/src/drop-down-list/dropdownlist.component";
import { Params } from "../services/params-service";

export default Vue.extend({
  name: "Idee",
  data: () => {
    return {
      showModal: false,
      showDetails: true,
      showButton: true,
      showVertriebskanal: false,
      showExistiert: true,
      showIntern: true,
      showProduktidee: false,
      showAngenommen: false,
      showAbgelehnt: false,
      showNichtBewertet: true,
    };
  },
  mounted() {
    Params.getInstance().tokenObserver.subscribe((res) => {
      console.log("hauptseite", res);
    });
  },
  methods: {
    push: function () {
      this.$router.push({ path: "/Startseite" });
    },
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
#app{
  position: relative;
  
}
</style>
