<template>
  <div class="container">
    <div class="links">
      <p>Alle Ideen</p>
      <div class="listeContainer">
        <ul class="liste">
          <li
            v-for="idee in Ideen"
            :key="idee"
            v-on:click="showModal = true, selectIdee(idee)"
          >{{idee}}</li>
        </ul>
      </div>
      <!--3 Filter Dropdowns -->
      <div class="filter">
        <div class="filterElement">
          <select id="filter1" v-model="ideenTyp" @click="selectFilter()">
            <option value disabled selected>Ideentyp</option>
            <option value="PRODUKTIDEE" selected>Produkt</option>
            <option value="INTERNE_IDEE">Intern</option>
          </select>
          <select id="filter2" v-model="sparte" v-bind:class="[ sparteAktiv ]">
            <option value disabled selected>Sparte</option>
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
          <select id="filter3" v-model="vertriebsweg" v-bind:class="[ vertriebswegAktiv ]">
            <option value disabled selected>Vertriebsweg</option>
            <option value="STATIONAERER_VERTRIEB">Stationärer Vertrieb in eigenen Geschäftsstelle</option>
            <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
            <option value="KOOPERATION_MIT_KREDITINSTITUTEN">Kooperation mit Kreditinstituten</option>
            <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
          </select>
          <select id="filter4" v-model="zielgruppe" v-bind:class="[ zielgruppeAktiv ]">
            <option value disabled selected>Zielgruppe</option>
            <option value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
            <option value="FAMILIEN">Familien</option>
            <option value="SINGLES">Singles</option>
            <option value="PAARE">Paare</option>
            <option value="PERSONEN_50PLUS">Personen 50+</option>
            <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
          </select>
          <select id="filter5" v-model="handlungsfeld" v-bind:class="[ handlungsfelderAktiv ]">
            <option value disabled selected>Handlungsfeld</option>
            <option value="KOSTENSENKUNG">Kostensenkung</option>
            <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
            <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
          </select>
        </div>
        <div class="filterElement">
          <!--Filter Button -->
          <button id="filterButton" @click="ideenFiltern()">Filter</button>
        </div>
      </div>
    </div>
    <div class="rechts">
      <component v-bind:is="component"></component>
    </div>
    <transition name="fade" appear>
      <div class="modal-overlay" v-if="showModal">
        <div class="kopfzeile">
          <label id="ideeName">
            Idee Name
            <!--{{}}-->
          </label>
          <div class="erstellInfos">
            <label class="erstellerLbl" for="ersteller">Ersteller:</label>
            <div id="ersteller">
              <!--{{}}-->
              gggg
            </div>
            <label id="erstellDatumLbl">Erstellt am:</label>
            <div id="erstellDatum">
              <!--{{}}-->
              20.12.20
            </div>
          </div>
        </div>
        <div class="hauptteil">
          <label id="beschreibungLbl" for="beschreibung">Beschreibung:</label>
          <div id="beschreibung">
            <!--{{}}-->
            adadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
          </div>
        </div>
        <div class="fußzeile">
          <button class="zurueckBtn" v-on:click="showModal =false">Zurück</button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import Registrierter from "@/components/Registrierter.vue";
import { properties } from "@syncfusion/ej2-vue-dropdowns/src/drop-down-list/dropdownlist.component";
import { Params } from "../services/params-service";
import { Idee } from "../classes/idea";
import { Helper } from "../services/helper";

export default Vue.extend({
  name: "Hauptseite",
  components: {
    registrierter: Registrierter,
  },
  data: () => ({
    // Filterwerte
    sparte: "",
    vertriebsweg: "",
    zielgruppe: "",
    handlungsfeld: "",
    // Aktivieren/Deaktivieren der Filter
    sparteAktiv: "aktiv",
    vertriebswegAktiv: "aktiv",
    zielgruppeAktiv: "aktiv",
    handlungsfelderAktiv: "aktiv",
    existiertAktiv: "aktiv",
    ideenTyp: "",
    // Modalfenster und Componentenlogik
    showModal: false,
    component: "registrierter",
    // Demodaten anlegen
    Ideen: Helper.getInstance().erzeugeDemoDaten(),
  }),
  methods: {
    selectFilter() {
      if (this.ideenTyp == "PRODUKTIDEE") {
        this.handlungsfelderAktiv = "inaktiv";
        this.sparteAktiv = "aktiv";
        this.vertriebswegAktiv = "aktiv";
        this.zielgruppeAktiv = "aktiv";
      } else {
        this.handlungsfelderAktiv = "aktiv";
        this.sparteAktiv = "inaktiv";
        this.vertriebswegAktiv = "inaktiv";
        this.zielgruppeAktiv = "inaktiv";
      }
    },
    selectIdee(){
      console.log(this.Ideen[0].IdeenTyp)
    },
    ideenFiltern() {
      // TODO: Wenn erneut gefiltert wird muss die Liste vorher resetted werden #############
      var ideeArray = new Array();
      this.Ideen.forEach((idee) => {
        if (idee.IdeenTyp === this.ideenTyp) ideeArray.push(idee);
        console.log(idee.IdeenTyp)
        console.log(idee)
      });
      this.Ideen = ideeArray;
    }
  }
});
</script>

<style lang="scss" scoped>
.container,
.rechts,
.hauptteil {
  position: relative;
}
.links,
.modal-overlay,
.fußzeile {
  position: absolute;
}
.container,
.hauptteil,
.listeContainer,
.filter,
.fußzeile,
#beschreibung {
  width: 100%;
}
.container,
.rechts,
.links,
.erstellInfos,
#beschreibung {
  height: 100%;
}
.rechts,
.links,
.erstellInfos,
.filter,
#ideeName {
  display: flex;
  justify-content: space-around;
}
p,
.links,
.rechts,
.modal-overlay {
  top: 0;
}
.links,
.rechts {
  width: 50%;
}
li,
#beschreibung,
#ersteller,
#erstellDatum {
  color: black;
}
li,
.listeContainer,
#beschreibung {
  border: 0.5px solid #000;
}
.links,
.rechts,
.modal-overlay {
  padding: 2%;
}
.container,
.modal-overlay {
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.links,
.rechts,
#ideeName {
  align-items: center;
}
button,
.container,
#beschreibung {
  border-radius: 20px;
}
button,
.rechts {
  background-color: #00894d;
}
.links,
#ideeName {
  flex-direction: column;
}
.rechts,
#ideeName {
  text-align: center;
}
ul,
.listeContainer {
  height: 300px;
}
#filter1,
#filter2,
#filter3,
#filter4,
#filter5 {
  margin: 2px;
  width: 5.5em;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
#beschreibung {
  display: block;
  background-color: #fff;
  word-wrap: break-word;
  padding: 0.2% 1%;
  overflow: scroll;
}
.container {
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}
.rechts {
  margin-left: 52.5%;
}
.hauptteil {
  height: 65%;
}
.zurueckBtn {
  background-color: #f80303;
  right: 5%;
}
.fußzeile {
  bottom: 5%;
}
#ideeName {
  font-size: 1.5rem;
}
.modal-overlay {
  left: 0;
  right: 0;
  bottom: 0;
}
p {
  margin: 8px 0 8px;
}
ul {
  list-style: none;
  overflow: auto;
  text-indent: 10px;
  padding: 0;
}
li {
  line-height: 150%;
}
li:hover {
  background-color: rgba(0, 0, 0, 0.1);
}
button {
  border: 1px solid #fff;
  color: #fff;
  font-size: 0.75rem;
  font-weight: bold;
  padding: 2px 5px;
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
.inaktiv {
  display: none;
}
.aktiv {
  display: inline;
}
</style>