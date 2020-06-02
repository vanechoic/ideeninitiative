<template>
  <article>
    <div class="container">
      <div class="links">
        <p>Ideenspeicher</p>
        <div class="listeContainer">
          <ul class="liste">
            <li
              v-for="idee in ideenFiltern()"
              :key="idee"
              v-on:click="showModal = true, selectIdee(idee)"
            >{{idee.titel}} von {{idee.erfasser}}</li>
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
        </div>
      </div>
      <div class="rechts">
        <div class="buttons">
          <router-link to="Bewerten" tag="button" id="ideeBewerten">Idee bewerten</router-link>
          <router-link to="Startseite" tag="button" id="zurueck">Zurück</router-link>
        </div>
      </div>
    </div>
  </article>
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
    ladeIdeenspeicher() {
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios
        .get("http://localhost:9090/idee/ideenspeicher", config)
        .then((res) => {
          console.log(res.data);
          this.Ideen = res.data || [];
        })
        .catch((err) => {
          console.log(err);
        });
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
    },
    ideenFiltern() {
      var it = this.ideenTyp;
      var sp = this.sparte;
      var vw = this.vertriebsweg;
      var zg = this.zielgruppe;
      var hf = this.handlungsfeld;

      return this.Ideen.filter(function (idee) {
        if ((idee as any).typ == it) return true;
        else if ((idee as any).sparten == sp) return true;
        else if ((idee as any).vertriebsweg == vw) return true;
        else if ((idee as any).zielgruppe == zg) return true;
        else if ((idee as any).handlungsfeld == hf) return true;
      });
    },
  },
  created() {
    this.ladeIdeenspeicher();
  },
});
</script>

<style lang="scss" scoped>
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
button {
  border-radius: 20px;
}
#ideeVeroeffentlichen,
#zurueck {
  background-color: black;
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
#zurück {
  background-color: #f80303;
}
#ideeBewerten {
  background-color: #ffdd00;
  color: black;
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
li {
  border: 0.5px solid #000;
}
.filter,
.listeContainer {
  width: 100%;
}
.listeContainer,
ul {
  height: 400px;
}
.container {
  width: 900px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}
ul {
  list-style: none;
  margin: 0;
  overflow: auto;
  padding: 0;
  text-indent: 10px;
}
li:hover {
  background-color: rgba(0, 0, 0, 0.1);
}
.filter {
  padding: 10px;
}
.rechts {
  position: absolute;
  margin-left: 50%;
  background-color: #00894d;
}
p {
  margin: 8px 0 8px;
}
button {
  border: 1px solid #fff;
  padding: 10px 40px;
  margin-top: 10px;
  color: #fff;
  font-size: 0.75rem;
  font-weight: bold;
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
li {
  line-height: 30px;
  color: black;
}
#ideeBewerten,
#zurueck {
  width: 85%;
  margin-left: 5.5%;
}
</style>