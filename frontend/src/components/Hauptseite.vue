<template>
  <div class="container">
    <div class="links">
      <p>Alle Ideen</p>
      <div class="listeContainer">
        <ul class="liste">
          <li v-for="idee in ideenFiltern()" :key="idee" v-on:click="push(), selectIdee(idee)">{{idee.titel}}  
            <br><span class="grauer-text">von {{idee.erfasser}} im Status: {{idee.bearbeitungsstatus | status}}</span></li>
        </ul>
      </div>
      <!--3 Filter Dropdowns -->
      <div class="filter">
          <select id="filter1" v-model="ideenTyp">
            <option value disabled selected>Ideentyp</option>
            <option value="ALLE" selected>Alle</option>
            <option value="PRODUKTIDEE">Produkt</option>
            <option value="INTERNE_IDEE">Intern</option>
          </select>
          <select id="filter2" v-model="sparte" v-if="ideenTyp == 'PRODUKTIDEE'">
            <option value disabled selected>Sparten</option>
            <option value="ALLE" selected>Alle</option>
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
          <select id="filter3" v-model="vertriebsweg" v-if="ideenTyp == 'PRODUKTIDEE'">
            <option value disabled selected>Vertriebswege</option>
            <option value="ALLE" selected>Alle</option>
            <option value="STATIONAERER_VERTRIEB"
              >Stationärer Vertrieb in eigenen Geschäftsstelle</option
            >
            <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
            <option value="KOOPERATION_MIT_KREDITINSTITUTEN"
              >Kooperation mit Kreditinstituten</option
            >
            <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
          </select>
          <select id="filter4" v-model="zielgruppe" v-if="ideenTyp == 'PRODUKTIDEE'">
            <option value disabled selected>Zielgruppe</option>
            <option value="ALLE" selected>Alle</option>
            <option value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
            <option value="FAMILIEN">Familien</option>
            <option value="SINGLES">Singles</option>
            <option value="PAARE">Paare</option>
            <option value="PERSONEN_50PLUS">Personen 50+</option>
            <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
          </select>
          <select id="filter5" v-model="handlungsfeld" v-if="ideenTyp == 'INTERNE_IDEE'">
            <option value disabled selected>Handlungsfeld</option>
            <option value="ALLE" selected>Alle</option>
            <option value="KOSTENSENKUNG">Kostensenkung</option>
            <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
            <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
          </select>
      </div>
    </div>
    <div class="rechts" id="rechts">
      <component v-bind:is="component"></component>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import Registrierter from "@/components/Registrierter.vue";
import Mitarbeiter from "@/components/Mitarbeiter.vue";
import Spezialist from "@/components/Spezialist.vue";
import { Params } from "../services/params-service";
import { Helper } from "../services/helper";

export default Vue.extend({
  name: "Hauptseite",
  components: {
    registrierter: Registrierter,
    mitarbeiter: Mitarbeiter,
    spezialist: Spezialist,
  },
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
    selektierterTyp: "",
    // Modalfenster und Componentenlogik
    showModal: false,
    component: "mitarbeiter",
    // Ideenliste
    Ideen: [],
    gefilterteIdeen: [],
    tempIdee: {},
    baseUrl: process.env.VUE_APP_BACKEND_BASE_URL,
  }),
  methods: {
    selectIdee(idee: any) {
      this.tempIdee = idee;
      localStorage.setItem("idee", JSON.stringify(this.tempIdee));
      localStorage.setItem("bewerten", JSON.stringify(false));
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
        return sp == 'ALLE' || sp == '' || sp == null || (idee as any).sparten == sp
      }).filter(function(idee){
        return hf == 'ALLE' || hf == '' || hf == null || (idee as any).handlungsfeld == hf
      }).filter(function(idee){
        return vw == 'ALLE' || vw == '' || vw == null || (idee as any).vertriebsweg.includes(vw)
      }).filter(function(idee){
        return zg == 'ALLE' || zg == '' || zg == null || (idee as any).zielgruppe.includes(zg)
      });
    },
    alleIdeenladen() {
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axiosInstance.get(this.baseUrl + "/idee").then((res) => {
        this.Ideen = res.data || [];
      });
    },
    push: function () {
      this.$router.push({ path: "/Idee" });
    },
    pushDemo: function () {
      this.$router.push({ path: "/IdeeBewerten" });
    },
  },
  mounted() {
    this.alleIdeenladen();
    var jwt = require("jsonwebtoken");
    var decode = jwt.decode(this.token);
    var rolle = decode["rollen"][0];
    console.log(rolle);

    // Entsprechende Component laden, abhängig von Nutzerrolle
    if (rolle == "ROLE_MITARBEITER") this.component = "registrierter";
    else if (rolle == "ROLE_FACHSPEZIALIST") this.component = "spezialist";
    else if (rolle == "ROLE_ADMIN") this.$router.push({ path: "/Admin" });
    else this.component = "mitarbeiter";
  },
});
</script>

<style lang="scss" scoped>
$medium-green: #00894d;
$light-green: #69a82f;
.rechts,
.container {
  position: relative;
}
.links {
  position: absolute;
}
.container,
.listeContainer,
.filter {
  width: 100%;
}
.rechts,
.links {
  height: 100%;
}
.rechts,
.links,
.filter {
  display: flex;
  justify-content: space-around;
}
p,
.links,
.rechts {
  top: 0;
}
.links,
.rechts {
  width: 50%;
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
.links,
.rechts {
  padding: 2%;
}
.container {
  height: 100%;
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.links,
.rechts {
  align-items: center;
}
button,
.container {
  border-radius: 20px;
}
button,
.rechts {
  background-color: #00894d;
}
.links {
  flex-direction: column;
}
.rechts {
  text-align: center;
}
#filter1,
#filter2,
#filter3,
#filter4,
#filter5 {
  margin: 2px;
  width: 7em;
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
  height: 550px;
  width: 800px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}
.rechts {
  margin-left: 52.5%;
}
.hauptteil {
  height: 65%;
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
