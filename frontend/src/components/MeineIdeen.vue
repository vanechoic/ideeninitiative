<template>
  <div class="container">
    <div class="links">
      <p>Meine Ideen</p>
      <div class="listeContainer">
        <ul class="liste">
          <li
            v-for="idee in ideenFiltern()"
            :key="idee"
            v-on:click="showModal = true, selectIdee(idee)"
            :class="{ selektierteIdee: tempIdee==idee }"
          >{{idee.titel}} von {{idee.erfasser}} Status: {{idee.bearbeitungsstatus | status}}</li>
        </ul>
      </div>
      <!--3 Filter Dropdowns -->
      <div class="filter">
        <div class="filterElement">
          <select id="filter1" v-model="ideenTyp" @click="selectFilter()">
            <option value disabled selected>Ideentyp</option>
            <option value="ALLE" selected>Alle</option>
            <option value="PRODUKTIDEE">Produkt</option>
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
      </div>
    </div>
    <div class="rechts">
      <button id="ideeVeroeffentlichen" @click="ideeVeroeffentlichen()">Idee veröffentlichen</button>
      <router-link id="ideeBearbeiten" to="/IdeeBearbeiten" tag="button" v-if="ideeBearbeitungszustand == 'ANGELEGT'">Bearbeiten</router-link>
      <button id="ideeLoeschen" @click="ideeLoeschen()" v-if="ideeBearbeitungszustand == 'ANGELEGT'">Löschen</button>
      <router-link to="Startseite" tag="button" id="zurueck">Zurück</router-link>
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
    // Ideenliste
    Ideen: [],
    gefilterteIdeen: [],
    nutzerIdeen: [],
    tempIdee: {},
    //
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
    goBack() {
      if (window.history.length > 1) this.$router.go(-1);
    },
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
    selectIdee(idee: any) {
      this.tempIdee = idee;
      localStorage.setItem("idee", JSON.stringify(this.tempIdee));
      this.ideeTyp = this.tempIdee.typ;
      this.ideeExistiert = this.tempIdee.existiertBereits;
      this.ideeTitel = this.tempIdee.titel;
      this.ideeErsteller = this.tempIdee.erfasser;
      this.ideeErstellt = this.tempIdee.erstellzeitpunkt;
      this.ideeBeschreibung = this.tempIdee.beschreibung;
      this.ideeVorteile = this.tempIdee.vorteile;
      this.ideeUnternehmen = this.tempIdee.unternehmensbezeichnung;
      this.ideeExistiertBeschreibung = this.tempIdee.artDerUmsetzung;
      this.ideeSparte = this.tempIdee.sparten;
      this.ideeVertriebskanal = this.tempIdee.vertriebsweg;
      this.ideeZielgruppe = this.tempIdee.zielgruppe;
      this.ideeHandlungsfeld = this.tempIdee.handlungsfeld;
      this.ideeBearbeitungszustand = this.tempIdee.bearbeitungsstatus;
      console.log(this.tempIdee.bearbeitungsstatus)
    },
    ideenFiltern() {
      var it = this.ideenTyp;
      var sp = this.sparte;
      var vw = this.vertriebsweg;
      var zg = this.zielgruppe;
      var hf = this.handlungsfeld;
      return this.Ideen.filter(function (idee) {
        return it == 'ALLE' || it == '' || it == null || (idee as any).typ == it
      }).filter(function(idee){
        return sp == 'ALLE' || sp == '' || sp == null || (idee as any).sparte == sp
      }).filter(function(idee){
        return hf == 'ALLE' || hf == '' || hf == null || (idee as any).handlungsfeld == hf
      }).filter(function(idee){
        return vw == 'ALLE' || vw == '' || vw == null || (idee as any).vertriebsweg.includes(vw)
      }).filter(function(idee){
        return zg == 'ALLE' || zg == '' || zg == null || (idee as any).zielgruppe.includes(zg)
      });
    },
    meineIdeenladen() {
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      var nutzer = decode["sub"];
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios
        .get("http://localhost:9090/idee/meineideen", config)
        .then((res) => {
          this.Ideen = res.data || [];
        })
        .catch((err) => {
          console.log(err);
        });

      this.Ideen.forEach((idee) => {
        if ((idee as any).erfasser == nutzer) this.nutzerIdeen.push(idee);
      });
      this.Ideen = this.nutzerIdeen;
    },
    ideeVeroeffentlichen() {
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      var nutzer = decode["sub"];
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios
        .post(
          "http://localhost:9090/idee/veroeffentlichen",
          {
            typ: this.ideeTyp,
            existiertBereits: this.ideeExistiert,
            titel: this.ideeTitel,
            erfasser: this.ideeErsteller,
            erstellzeitpunkt: this.ideeErstellt,
            beschreibung: this.ideeBeschreibung,
            vorteile: this.ideeVorteile,
            unternehmensbezeichnung: this.ideeUnternehmen,
            artDerUmsetzung: this.ideeExistiertBeschreibung,
            sparten: this.ideeSparte,
            vertriebsweg: this.ideeVertriebskanal,
            zielgruppe: this.ideeZielgruppe,
            handlungsfeld: this.ideeHandlungsfeld,
            bearbeitungsstatus: this.ideeBearbeitungszustand,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Idee erfolgreich veröffentlicht", "success");
          this.meineIdeenladen();
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Veröffentlichen",
            "error"
          )
        );
    },
    ideeLoeschen() {
      console.log(this.tempIdee as any);
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      var titelT = (this.tempIdee as any).titel;
      var erfasserT = decode["sub"];
      var erstelldatumT = (this.tempIdee as any).erstellzeitpunkt;

      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      axiosInstance
        .post(
          "http://localhost:9090/idee/loeschen",
          {
            typ: this.ideeTyp,
            existiertBereits: this.ideeExistiert,
            titel: this.ideeTitel,
            erfasser: this.ideeErsteller,
            erstellzeitpunkt: this.ideeErstellt,
            beschreibung: this.ideeBeschreibung,
            vorteile: this.ideeVorteile,
            unternehmensbezeichnung: this.ideeUnternehmen,
            artDerUmsetzung: this.ideeExistiertBeschreibung,
            sparten: this.ideeSparte,
            vertriebsweg: this.ideeVertriebskanal,
            zielgruppe: this.ideeZielgruppe,
            handlungsfeld: this.ideeHandlungsfeld,
            bearbeitungsstatus: this.ideeBearbeitungszustand,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Idee erfolgreich gelöscht", "success");
          this.meineIdeenladen();
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Löschen",
            "error"
          )
        );
    },
  },
  created() {
    this.meineIdeenladen();
  },
});
</script>

<style lang="scss" scoped>
$medium-green: #00894d;
$light-green: #69a82f;
.container,
.links,
.rechts {
  height: 100%;
}
p,
.rechts,
.links {
  top: 0;
}
.container,
#fliterButton {
  border-radius: 20px;
}
button, .roter-button, .hellgruener-button{
  width: 70%;
}
#ideeVeroeffentlichen,
#ideeBearbeiten,
#zurueck {
  background-color: $light-green;
}
.container,
.links {
  position: relative;
}
.links,
.rechts {
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 50%;
  flex-direction: column;
}
.rechts{
  background-color: $medium-green;
}
.filter,
.filterElement {
  float: left;
}
#filter1,
#filter2,
#filter3,
#filter4,
#filter5 {
  margin-right: 2px;
  width: 6em;
}
.listeContainer,
ul {
  //border: 0.5px solid #fff;
  height: 100%;
  border-radius: 20px;
  list-style: none;
  margin: 0;
  //overflow: auto;
  padding: 0;
  text-indent: 10px;
}
li {
  border: none;
  border-bottom: 0.5px solid #fff;
  line-height: 30px;
  color: $medium-green;
  &:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }
}
.filter,
.listeContainer {
  width: 100%;
}
.container {
  width: 900px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.filter {
  padding: 10px;
}
.rechts {
  position: absolute;
  margin-left: 52.5%;
}
p {
  margin: 8px 0 8px;
}
#ideeLoeschen {
  background-color: #f80303;
}

.selektierteIdee {
  background: rgba(0, 0, 0, 0.1);
}

//Scrollbar
::-webkit-scrollbar {
  width: 8px;
}
/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}
/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}
/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>