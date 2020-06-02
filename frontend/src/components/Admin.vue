<template>
  <article>
    <div class="container">
      <p v-if="showNachrichtenModal==false">Eingeloggt als Admin</p>
      <!--Liste der Mitarbieter-->
      <div class=listenTeil v-if="showMitarbeiterModal==false && showNachrichtenModal==false">
        <label for="liste">Mitarbeiter:</label>
        <ul class="liste">
          <li v-on:click="mitarbeiterAusgewählt=true"> beispiel</li>
        </ul>
      </div>
      <!--Buttons-->
      <div class="buttons" v-if="showMitarbeiterModal==false && showNachrichtenModal==false">
        <button id="rollenBtn" v-if="mitarbeiterAusgewählt" v-on:click="showMitarbeiterModal=true">Rolle bearbeiten</button>
        <button id="nachrichten" v-on:click="showNachrichtenModal = true">Systemnachrichten lesen</button>
      </div> 


      <!--Mitarbeiter Modal-->
      <transition name="fade" appear>
        <div class="mitarbeiterModal" v-if="showMitarbeiterModal">
          <div class="rollen">
            <div class="rolleRegistrierter">
              <label for="registrierter">Mitarbeiter</label>
              <input
                type="radio"
                id="registrierter"
                name="Nutzerrollen"
                value="ROLE_MITARBEITER"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = false"
              />
            </div>
            <div class="rolleSpezialist">
              <label for="spezialist">Fachspezialist</label>
              <input
                type="radio"
                id="spezialist"
                name="Nutzerrollen"
                value="ROLE_FACHSPEZIALIST"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = true"
              />
            </div>
            <div class="rolleAdmin">
              <label for="admin">Admin</label>
              <input
                type="radio"
                id="admin"
                name="Nutzerrollen"
                value="ROLE_ADMIN"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = false"
              />
            </div>
          </div>
          <!--Spezialisierungen Dropdown-->
          <div class="spezialisierungen" v-if="showSpezialisierung == true">
            <label for="spezialisierung1">Sparte:</label>
            <select id="spezialisierung1" v-model="sparte">
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
            <label for="spezialisierung2">Vertriebsweg:</label>
            <select id="spezialisierung2" v-model="vertriebsweg">
              <option value disabled selected>Vertriebsweg</option>
              <option value="STATIONAERER_VERTRIEB">Stationärer Vertrieb in eigenen Geschäftsstelle</option>
              <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
              <option value="KOOPERATION_MIT_KREDITINSTITUTEN">Kooperation mit Kreditinstituten</option>
              <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
            </select>
            <label for="spezialisierung3">Zielgruppe:</label>
            <select id="spezialisierung3" v-model="zielgruppe">
              <option value disabled selected>Zielgruppe</option>
              <option value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
              <option value="FAMILIEN">Familien</option>
              <option value="SINGLES">Singles</option>
              <option value="PAARE">Paare</option>
              <option value="PERSONEN_50PLUS">Personen 50+</option>
              <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
            </select>
            <label for="spezialisierung4">Handlungsfeld:</label>
            <select id="spezialisierung4" v-model="handlungsfeld">
              <option value disabled selected>Handlungsfeld</option>
              <option value="KOSTENSENKUNG">Kostensenkung</option>
              <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
              <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
            </select>
          </div>
          <!--MitarbeiterModal Buttons-->
          <div class="mitarbeiterModalButtons">
            <button class="zurückBtn" v-on:click="showMitarbeiterModal = false">Zurück</button>
            <button id="rolleBestätigenBtn">Änderung bestätigen</button>
          </div>
        </div>
      </transition>
      <!--Nachrichten Modal-->
      <transition name="fade" appear>
        <div class="NachrichtenModal" v-if="showNachrichtenModal">
          <p>Systemnachrichten</p>
          <!--Nachrichen Liste-->
          <div class="systemnachrichten" v-if="nachrichtLesen == false">
            <label for="liste">Empfangende Nachrichten:</label>
            <ul class="liste">
              <li v-on:click="systemnachrichtAusgewählt = true"> beispiel</li>
            </ul>
          </div>
          <div class="systemnachrichtDarstellen" v-if="nachrichtLesen == true">
            <div id="Betreff"><!--{{}}-->Toller Betreff</div>
            <div id="text"><!--{{}}-->Text der Nachricht</div>
            <button class="zurückButton" v-on:click="nachrichtLesen=false">Zurück</button>
          </div>
          <div class="systemnachrichtButtons" v-if="systemnachrichtAusgewählt == true && nachrichtLesen==false">
            <button id="nachrichtLöschenBtn">Nachricht löschen</button>
            <button id="nachrichtLesenBtn" v-on:click="nachrichtLesen= true">Nachricht lesen</button>
          </div>
          <button class="zurückButton" v-on:click="showNachrichtenModal=false" v-if="nachrichtLesen==false">Zurück</button>
        </div>
      </transition>
      <div class="abmelden" v-if="showNachrichtenModal == false && showMitarbeiterModal==false">
        <router-link id="abmelden" :to="{ path: '/' }" replace tag="button" @click="logout()">Abmelden</router-link>
      </div>
    </div>
  </article>
</template>
<script>
import Vue from "vue";
import axios from "axios";
import { Params } from "../services/params-service";
import { Helper } from "../services/helper";
export default Vue.extend({
  name: 'Admin',
  data: () => ({
    mitarbeiterAusgewählt :false,
    showNachrichtenModal: false,
    showMitarbeiterModal: false,
    showSpezialisierung: false,
    systemnachrichtAusgewählt: false,
    nachrichtLesen: false,
  }),
  methods:{
    logout: function(){
      localStorage.clear();
    },
  },
});
</script>

<style lang="scss" scoped>
 .container{
    position: relative;
    background: linear-gradient(to bottom, #efefef, #ccc);
    border-radius: 20px;
    overflow: hidden;
    height: 550px;
    width: 800px;
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
}

</style>