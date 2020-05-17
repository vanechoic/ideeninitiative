<template>
  <article>
    <div class="container" :class="{'sign-up-active' : signUp}">
      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-links">
            <h2>Zur Anmeldung!</h2>
            <p>Bitte Benutzernamen und Passwort angeben</p>
            <button class="invert" id="zurAnmeldung" @click="signUp = !signUp">Anmelden</button>
          </div>
          <div class="overlay-rechts">
            <h2>Zur Registrierung!</h2>
            <p>Bitte ihre Daten eingeben</p>
            <router-link to="/Startseite" tag="button" class="nichtRegistriert">Weiter ohne Registrierung</router-link>
            <button class="invert" id="zurRegistrierung" @click="signUp = !signUp">Registrieren</button>
          </div>
        </div>
      </div>
      <form class="registrieren" action="#">
        <h2>Registrierung</h2>
        <div>Bitte mit E-Mail-Adresse registrieren</div>
        <input type="text" placeholder="Benutzername" id="benutzernameReg" v-model="benutzernameReg"/>
        <input type="text" placeholder="Vorname" id="vorname" v-model="vorname">
        <input type="text" placeholder="Nachname" id="nachname" v-model="nachname">
        <input type="email" placeholder="beispiel@email.de" id="emailReg" v-model="emailReg"/>
        <input type="password" placeholder="Passwort" id="passwortReg" v-model="passwortReg"/>
        <button class="registrierung" id="registrierungButton" v-on:click="registrieren()">Registrieren</button>
      </form>
      <form class="anmelden" action="#">
        <h2>Anmeldung</h2>
        <div>Bitte geben sie ihre Benutzerdaten ein</div>
        <input type="text" placeholder="Benutzername" id="benutzernameAn" v-model="benutzernameAn"/>
        <input type="password" placeholder="Passwort" id="passwortAn" v-model="passwortAn"/>
        <button class="login" id="anmeldungButton" v-on:click="anmelden()">Anmelden</button>
      </form>
    </div>
  </article>
</template>

<script lang="ts">
import axios from 'axios'
import Vue from "vue"
import HauptseiteVue from './Hauptseite.vue';

export default Vue.extend({
    data: () => ({
        signUp: false,
        benutzernameReg: '',
        vorname: '',
        nachname: '',
        emailReg: '',
        passwortReg: '',
        benutzernameAn: '',
        passwortAn: '',
    }),
    methods: {
            registrieren: function(event: Event) {
              console.log("REGISTRIEREN BUTTON");
              var axiosInstance = axios.create({
                baseURL: 'http://localhost:9090/benutzer',
                headers: {
                  'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'POST, GET, PUT, OPTIONS, DELETE',
            'Access-Control-Allow-Headers': 'Access-Control-Allow-Methods, Access-Control-Allow-Origin, Origin, Accept, Content-Type',
            'Content-Type': 'application/json',
            'Accept': 'application/json'
                }
              });
              axiosInstance.post('http://localhost:9090/benutzer', {
                  benutzername: this.benutzernameReg,
                  vorname: this.vorname,
                  nachname: this.nachname,
                  email: this.emailReg,
                  passwort: this.passwortReg
              })
              .then(function (response) {
                  console.log(response);
              });
            },
            anmelden: function(event: Event) {
              console.log("ANMELDEN BUTTON");
              var axiosInstance = axios.create({
                baseURL: 'http://localhost:9090/benutzer',
                headers: {
                  'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'POST, GET, PUT, OPTIONS, DELETE',
            'Access-Control-Allow-Headers': 'Access-Control-Allow-Methods, Access-Control-Allow-Origin, Origin, Accept, Content-Type',
            'Content-Type': 'application/json',
            'Accept': 'application/json'
                }
              });
              axiosInstance.post('http://localhost:9090/benutzer/login', {
                  benutzername: this.benutzernameAn,
                  passwort: this.passwortAn
              })
              .then(resp => {
                console.log(resp.data)
                const token = resp.data
                localStorage.setItem('token', token)
                axiosInstance.defaults.headers.common['Authorization'] = token

                var jwt = require('jsonwebtoken')
                var decode = jwt.decode(token);
                console.log(decode)

                if (decode["rollen"] == "ROLE_MITARBEITER")
                  this.$router.push({ name: 'Startseite', params: {dtoken:decode}})
              });
            },
            routing: function(e: Event)
            {
              //this.$router.push("/Home")
              
            }
        }
  })
</script>

<style lang="scss" scoped>
  .container {
    position: relative;
    width: 768px;
    height: 480px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
    .overlay-container {
      position: absolute;
      top: 0;
      left: 50%;
      width: 50%;
      height: 100%;
      overflow: hidden;
      transition: transform .5s ease-in-out;
      z-index: 100;
    }
    .overlay {
      position: relative;
      left: -100%;
      height: 100%;
      width: 200%;
      background: linear-gradient(to bottom right, #00894d, #009345);
      color: #fff;
      transform: translateX(0);
      transition: transform .5s ease-in-out;
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
      transition: transform .5s ease-in-out;
    }
    .overlay-links {
      @include overlays(-20%);
      width:50%;
      height: 100%;
    }
    .overlay-rechts {
      @include overlays(0);
      width:50%;
      height: 100%;
      right: 0;
    }
  }
  h2 {
    margin: 0;
  }
  p {
    margin: 20px 0 30px;
  }
  button {
    border-radius: 20px;
    border: 1px solid #00894d;
    background-color:#00894d;
    color: #fff;
    font-size: 1rem;
    font-weight: bold;
    padding: 10px 40px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    transition: transform .1s ease-in;
    &:active {
      transform: scale(.9);
    }
    &:focus {
      outline: none;
    }
  }
  button.invert {
    background-color: transparent;
    border-color: #fff;
  }
  .nichtRegistriert{
    background-color: transparent;
    border-color: #fff;
    font-size: 0.5rem;
  }
  form {
    position: absolute;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    padding: 90px 60px;
    width: calc(50% - 120px);
    height: calc(100% - 180px);
    text-align: center;
    background: linear-gradient(to bottom, #efefef, #ccc);
    transition: all .5s ease-in-out;
    div {
      font-size: 1rem;
    }
    input {
      background-color: #eee;
      border: none;
      padding: 5px 15px;
      margin: 6px 0;
      width: calc(100% - 30px);
      border-radius: 15px;
      border-bottom: 1px solid #ddd;
      box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4), 
                        0 -1px 1px #fff, 
                        0 1px 0 #fff;
      overflow: hidden;
      &:focus {
        outline: none;
        background-color: #fff;
      }
    }
  }
  .anmelden {
    left: 0;
    width:50%;
    height: 100%;
    z-index: 2;
  }
  .registrieren {
    left: 0;
    width:50%;
    height: 100%;
    z-index: 1;
    opacity: 0;
  }
  .sign-up-active {
    .anmelden {
      transform: translateX(100%);
    }
    .registrieren {
      transform: translateX(100%);
      opacity: 1;
      z-index: 5;
      animation: show .5s;
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
