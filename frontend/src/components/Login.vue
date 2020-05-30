<template>
  <article>
    <div class="container" :class="{ 'sign-up-active': signUp }">
      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-links">
            <h2>Zur Anmeldung!</h2>
            <p>Bitte Benutzernamen und Passwort angeben</p>
            <button class="invert" id="zurAnmeldung" @click="signUp = !signUp">
              Anmelden
            </button>
          </div>
          <div class="overlay-rechts">
            <h2>Zur Registrierung!</h2>
            <p>Bitte ihre Daten eingeben</p>
            <button class="nichtRegistriert" @click="weiterOhneAnmeldung()">Weiter ohne Registrierung</button>
            <button class="invert" id="zurRegistrierung" @click="signUp = !signUp">Registrieren</button>
          </div>
        </div>
      </div>
      <form class="registrieren" action="#">
        <h2>Registrierung</h2>
        <div>Bitte mit E-Mail-Adresse registrieren</div>
        <input
          type="text"
          placeholder="Benutzername"
          id="benutzernameReg"
          v-model="benutzernameReg"
        />
        <input
          type="text"
          placeholder="Vorname"
          id="vorname"
          v-model="vorname"
        />
        <input
          type="text"
          placeholder="Nachname"
          id="nachname"
          v-model="nachname"
        />
        <input
          type="email"
          placeholder="beispiel@email.de"
          id="emailReg"
          v-model="emailReg"
        />
        <input
          type="password"
          placeholder="Passwort"
          id="passwortReg"
          v-model="passwortReg"
        />
        <button
          class="registrierung"
          id="registrierungButton"
          v-on:click="registrieren()"
          @click="signUp = !signUp"
        >
          Registrieren
        </button>
      </form>
      <form class="anmelden" action="#">
        <h2>Anmeldung</h2>
        <div>Bitte geben sie ihre Benutzerdaten ein</div>
        <input
          type="text"
          placeholder="Benutzername"
          id="benutzernameAn"
          v-model="benutzernameAn"
        />
        <input
          type="password"
          placeholder="Passwort"
          id="passwortAn"
          v-model="passwortAn"
        />
        <button class="login" id="anmeldungButton" v-on:click="anmelden()">
          Anmelden
        </button>
      </form>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import HauptseiteVue from "./Hauptseite.vue";
import { Params } from "../services/params-service";
import { Helper } from "../services/helper";

export default Vue.extend({
  data: () => ({
    signUp: false,
    benutzernameReg: "",
    vorname: "",
    nachname: "",
    emailReg: "",
    passwortReg: "",
    benutzernameAn: "",
    passwortAn: "",

    dismissSecs: 10,
    dismissCountDown: 0,
    showDismissibleAlert: false,
  }),
  methods: {
    registrieren: function (event: Event) {
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axiosInstance
        .post("http://localhost:9090/benutzer", {
          benutzername: this.benutzernameReg,
          vorname: this.vorname,
          nachname: this.nachname,
          email: this.emailReg,
          passwort: this.passwortReg,
        })
        .then(function (response) {});
    },
    anmelden: function (event: Event) {

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axiosInstance
        .post("http://localhost:9090/benutzer/login", {
          benutzername: this.benutzernameAn,
          passwort: this.passwortAn,
        })
        .then((resp) => {
          const token = resp.data;
          localStorage.setItem("token", token);
          axiosInstance.defaults.headers.common["Authorization"] = token;
          var jwt = require("jsonwebtoken");
          var decode = jwt.decode(token);
          Params.getInstance().tokenSubject.next(decode);
          this.$router.push("Startseite");
        });
    },
    weiterOhneAnmeldung(){
      this.$router.push("Startseite-Mitarbeiter");
    }
  },
  beforeCreate() {
    window.localStorage.clear();
  },
});
</script>

<style lang="scss" scoped>
.anmelden,
.registrieren,
.overlay-container,
.overlay {
  height: 100%;
}
.anmelden,
.registrieren,
.overlay-container {
  width: 50%;
}
.container,
input,
.overlay-container {
  overflow: hidden;
}
.anmelden,
.container,
.registrieren {
  left: 0;
}
.overlay-container,
form {
  position: absolute;
}
.overlay-container,
form {
  top: 0;
}
.overlay-container,
.overlay {
  transition: transform 0.5s ease-in-out;
}
button,
.container,
input {
  border-radius: 20px;
}
.overlay,
button {
  color: #fff;
  background: linear-gradient(to bottom right, #00894d, #009345);
}
.nichtRegistriert,
button.invert {
  border-color: #fff;
}
.container {
  width: 768px;
  height: 480px;
  top: 10%;
  right: 0;
  position: relative;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}
.overlay-container {
  left: 50%;
  z-index: 100;
}
p {
  margin: 20px 0 30px;
}
div {
  font-size: 1rem;
}
.nichtRegistriert {
  font-size: 0.5rem;
}
.overlay {
  position: relative;
  left: -100%;
  width: 200%;
  transform: translateX(0);
}
button {
  border: 1px solid #00894d;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px 40px;
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
form {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  padding: 90px 60px;
  width: calc(50% - 120px);
  height: calc(100% - 180px);
  text-align: center;
  background: linear-gradient(to bottom, #efefef, #ccc);
  transition: all 0.5s ease-in-out;
}
input {
  background-color: #eee;
  border: none;
  padding: 5px 15px;
  margin: 6px 0;
  width: calc(100% - 30px);
  border-bottom: 1px solid #ddd;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.4), 0 -1px 1px #fff, 0 1px 0 #fff;
  &:focus {
    outline: none;
    background-color: #fff;
  }
}
.anmelden {
  z-index: 2;
}
.registrieren {
  z-index: 1;
  opacity: 0;
}
@mixin overlays($property) {
  position: absolute;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-direction: column;
  padding: 70px 40px;
  width: calc(50% - 80px);
  height: calc(100% - 140px);
  text-align: center;
  transform: translateX($property);
  transition: transform 0.5s ease-in-out;
}
.overlay-links {
  @include overlays(-20%);
  width: 50%;
  height: 100%;
}
.overlay-rechts {
  @include overlays(0);
  width: 50%;
  height: 100%;
  right: 0;
}
.sign-up-active {
  .anmelden {
    z-index: 1;
    transform: translateX(100%);
  }
  .registrieren {
    opacity: 1;
    z-index: 5;
    transform: translateX(100%);
  }
  .overlay-container {
    transform: translateX(-100%);
  }
  .overlay {
    transform: translateX(50%);
  }
  .overlay-links {
    transform: translateX(0);
  }
  .overlay-rechts {
    transform: translateX(20%);
  }
}
@keyframes show {
  0% {
    opacity: 0;
    z-index: 1;
  }
  49% {
    opacity: 0;
    z-index: 1;
  }
  50% {
    opacity: 1;
    z-index: 10;
  }
}
</style>
