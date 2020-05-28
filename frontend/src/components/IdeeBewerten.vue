<template>
  <div class="container">
      <h2>Bewertung</h2>
      <div class="idee">
        <component v-bind:is="component"></component>
      </div>
      <div class="bewertungsTeil">
        <button>Später bewerten</button>
        <button>Jetzt bewerten</button>
      </div>
      <!-- Modal.....-->
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
    component: "idee",
    // Ideenliste
    Ideen: [],
    gefilterteIdeen: [],
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
    selectIdee() {
      // Platzhalter für später
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
    alleIdeenladen() {
      axios.get("http://localhost:9090/idee").then((res) => {
        this.Ideen = res.data;
      });
    },
     push: function () {
      this.$router.push({ path: "/Idee" });
    },
  },
  created() {
    this.alleIdeenladen();
  },
});
</script>

<style lang="scss" scoped>
.container {
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
  border-radius: 20px;
  position: relative;
}
.bewertungsTeil{
  position: relative;
  display: flex;
  justify-content: space-between;
  margin: 3%;
}
h2{
  margin: auto;
}
.idee{
  text-align: left;
  position: relative;
}
</style>
