<template>
  <div class="container-fluid" name="AdminContainer">
    <p id="anzeige-aktuelle-seite" v-if="showNachrichtenModal==false">Eingeloggt als Admin</p>
    <!--Liste der Mitarbieter-->
    <div
      class="row mitarbeiterListe"
      v-if="showMitarbeiterModal==false && showNachrichtenModal==false"
    >
      <label class="grid-item" for="liste">Mitarbeiter:</label>
      <ul class="liste grid-item">
        <li
          v-for="mitarbeiter in mitarbeiterListe"
          :key="mitarbeiter"
          v-on:click="selectMitarbeiter(mitarbeiter), mitarbeiterAusgewählt=true"
        >
          {{mitarbeiter.benutzername}} hat Rolle: Mitarbeiter
          <span
            v-if="mitarbeiter.istFachspezialist"
          >| Fachspezialist</span>
          <span v-if="mitarbeiter.istAdmin">| Admin</span>
        </li>
      </ul>
    </div>
    <!--Buttons-->
    <div class="row" v-if="showMitarbeiterModal==false && showNachrichtenModal==false">
      <div class="buttons">
        <button
          id="rollenBtn"
          v-if="mitarbeiterAusgewählt"
          v-on:click="showMitarbeiterModal=true"
        >Rolle bearbeiten</button>
        <button
          id="nachrichten"
          v-on:click="showNachrichtenModal = true, ladeSystemnachrichten()"
        >Systemnachrichten lesen</button>
      </div>
    </div>
    <!--Mitarbeiter Modal-->
    <transition name="fade" appear>
      <div class="mitarbeiterModal" v-if="showMitarbeiterModal">
        <div class="rollen">
          <div class="row">
            <p
              class="grid-item"
              id="mitarbeiterName"
            >Ausgewählter Mitarbeiter: {{ aktiverMitarbeiter.benutzername }}</p>
          </div>
          <div class="row">
            <div class="rolleMitarbeiter">
              <label for="registrierter" class="grid-item rolleLbl">Mitarbeiter:</label>
              <input
                type="radio"
                id="registrierter"
                class="grid item"
                name="Nutzerrollen"
                value="ROLE_MITARBEITER"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = false, showAuenderungBestätigt=true"
              />
            </div>
          </div>
          <div class="row">
            <div class="rolleSpezialist">
              <label for="spezialist" class="grid-item rolleLbl">Fachspezialist:</label>
              <input
                type="radio"
                id="spezialist"
                class="grid-item"
                name="Nutzerrollen"
                value="ROLE_FACHSPEZIALIST"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = true, showAuenderungBestätigt=true"
              />
            </div>
          </div>
          <div class="row rolleAdmin">
            <div class="rolleAdmin">
              <label for="admin" class="grid-item rolleLbl">Admin:</label>
              <input
                type="radio"
                id="admin"
                class="grid-item"
                name="Nutzerrollen"
                value="ROLE_ADMIN"
                v-model="radiobutton"
                v-on:click="showSpezialisierung = false, showAuenderungBestätigt=true"
              />
            </div>
          </div>
        </div>
        <!--Spezialisierungen Dropdown-->
        <div class="row" v-if="showSpezialisierung == true">
          <div class="spezialisierungsDropdown">
            <div class="combobox">
              <label for="spezialisierung1" class="grid-item">Sparte:</label>
              <select id="spezialisierung1" class="grid-item" v-model="sparte" multiple>
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
            </div>
            <div class="combobox">
              <label for="spezialisierung2" class="grid-item">Vertriebsweg:</label>
              <select id="spezialisierung2" class="grid-item" v-model="vertriebsweg" multiple>
                <option
                  value="STATIONAERER_VERTRIEB"
                >Stationärer Vertrieb in eigenen Geschäftsstelle</option>
                <option value="VERSICHERUNGSMAKLER">Versicherungsmakler</option>
                <option value="KOOPERATION_MIT_KREDITINSTITUTEN">Kooperation mit Kreditinstituten</option>
                <option value="DIREKTVERSICHERUNG">Direktversicherung</option>
              </select>
            </div>
            <div class="combobox">
              <label for="spezialisierung3" class="grid-item">Zielgruppe:</label>
              <select id="spezialisierung3" class="grid-item" v-model="zielgruppe" multiple>
                <option value="KINDER_JUGENDLICHE">Kinder/Jugendliche</option>
                <option value="FAMILIEN">Familien</option>
                <option value="SINGLES">Singles</option>
                <option value="PAARE">Paare</option>
                <option value="PERSONEN_50PLUS">Personen 50+</option>
                <option value="GEWERBETREIBENDE">Gewerbetreibende</option>
              </select>
            </div>
            <div class="combobox">
              <label for="spezialisierung4" class="grid-item">Handlungsfeld:</label>
              <select id="spezialisierung4" class="grid-item" v-model="handlungsfeld" multiple>
                <option value="KOSTENSENKUNG">Kostensenkung</option>
                <option value="ERTRAGSSTEIGERUNG">Ertragssteigerung</option>
                <option value="ZUKUNFTSFAEHIGKEIT">Zukunftsfähigkeit</option>
              </select>
            </div>
          </div>
        </div>
        <!--MitarbeiterModal Buttons-->
        <div class="row">
          <div class="buttons">
            <button
              class="zurueckBtn grid-item"
              v-on:click="showMitarbeiterModal = false, 
              mitarbeiterAusgewählt= false, 
              showAuenderungBestätigt=false,
              ladeMitarbeiter()"
            >Zurück</button>
            <button
              id="rolleBestätigenBtn"
              class="grid-item"
              v-if="showAuenderungBestätigt"
              @click="speichereMitarbeiter()"
            >Änderung bestätigen</button>
          </div>
        </div>
      </div>
    </transition>
    <!--Nachrichten Modal-->
    <transition name="fade" appear>
      <div class="nachrichtenModal" v-if="showNachrichtenModal">
        <p class="anzeige-aktuelle-seite">Systemnachrichten</p>
        <!--Nachrichten Liste-->
        <div class="row nachrichtenListe" v-if="nachrichtLesen == false">
          <label for="liste" class="grid-item">Empfangende Nachrichten:</label>
          <ul class="liste grid-item">
            <li
              v-for="systemnachricht in systemnachrichtListe"
              :key="systemnachricht"
              v-on:click="selectSystemnachricht(systemnachricht),
               systemnachrichtAusgewählt = true"
            >{{ systemnachricht.titel }}</li>
          </ul>
        </div>
        <!--Nachricht Details-->
        <div class="row nachrichtLesen" v-if="nachrichtLesen == true">
          <div class="nachrichtDetails">
            <label for="betreff" id="betreffLbl">Betreff:</label>
            <div id="betreff" class="grid-item">
              <!--{{}}-->
              {{ aktiveSystemnachricht.titel }}
            </div>
            <label for="text">Nachricht:</label>
            <div id="text" class="grid-item">
              <!--{{}}-->
              {{ aktiveSystemnachricht.beschreibung }}
            </div>
            <div class="buttons">
              <button
                class="zurueckBtn grid-item"
                v-on:click="nachrichtLesen=false, systemnachrichtAusgewählt=false"
              >Zurück</button>
            </div>
          </div>
        </div>
        <div class="row" v-if="systemnachrichtAusgewählt == true && nachrichtLesen==false">
          <div class="buttons">
            <button
              id="nachrichtLöschenBtn"
              class="grid-item"
              @click="loescheSystemnachricht()"
            >Nachricht löschen</button>
            <button
              id="nachrichtLesenBtn"
              class="grid-item"
              v-on:click="nachrichtLesen= true"
            >Nachricht lesen</button>
          </div>
        </div>
        <div class="row">
          <div class="buttons">
            <button
              class="zurueckBtn grid-item"
              v-on:click="showNachrichtenModal=false, systemnachrichtAusgewählt=false"
              v-if="nachrichtLesen==false"
            >Zurück</button>
          </div>
        </div>
      </div>
    </transition>
    <div class="row" v-if="showNachrichtenModal == false && showMitarbeiterModal==false">
      <div class="buttons">
        <router-link
          id="abmelden"
          :to="{ path: '/' }"
          replace
          tag="button"
          @click="logout()"
          class="grid-item"
        >Abmelden</router-link>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import axios from "axios";
import { Params } from "../services/params-service";
import { Helper } from "../services/helper";
export default Vue.extend({
  name: 'Admin',
  data: () => ({
    // Component spezifische Variablen
    mitarbeiterAusgewählt :false,
    showNachrichtenModal: false,
    showMitarbeiterModal: false,
    showSpezialisierung: false,
    systemnachrichtAusgewählt: false,
    nachrichtLesen: false,
    showAuenderungBestätigt: false,
    // Logik-Variablen - Listen
    mitarbeiterListe: [],
    systemnachrichtListe: [],
    aktiverMitarbeiter: {},
    aktiveSystemnachricht: {},
    // Frontend-Element Variablen
    sparte: [],
    vertriebsweg: [],
    zielgruppe: [],
    handlungsfeld: [],
    radiobutton: "",
  }),
  methods:{
    logout: function(){
      localStorage.clear();
    },
    ladeMitarbeiter(){
      var jwt = require("jsonwebtoken");
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios.get("http://localhost:9090/benutzer", config)
        .then((res) => {
          this.mitarbeiterListe = res.data || [];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    selectMitarbeiter(mitarbeiter)
    {
      this.aktiverMitarbeiter = mitarbeiter;
    },
    speichereMitarbeiter(){
      var jwt = require("jsonwebtoken");
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };
      var axiosInstance = Helper.getInstance().createAxiosInstance();

      if (this.radiobutton == "ROLE_MITARBEITER")
      {
        axiosInstance.put("http://localhost:9090/benutzer",
          {
            benutzername: this.aktiverMitarbeiter.benutzername,
            vorname: this.aktiverMitarbeiter.vorname,
            nachname: this.aktiverMitarbeiter.nachname,
            email: this.aktiverMitarbeiter.email,
            fachspezialistVertriebswege: null,
            fachspezialistHandlungsfelder: null,
            fachspezialistSparten: null,
            fachspezialistZielgruppen: null,
            istFachspezialist: false,
            istAdmin: false,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Benutzer erfolgreich aktualisiert", "success");
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Aktualisieren",
            "error"
          )
        );
      }
      else if(this.radiobutton == "ROLE_FACHSPEZIALIST")
      {
        axiosInstance.put("http://localhost:9090/benutzer",
          {
            benutzername: this.aktiverMitarbeiter.benutzername,
            vorname: this.aktiverMitarbeiter.vorname,
            nachname: this.aktiverMitarbeiter.nachname,
            email: this.aktiverMitarbeiter.email,
            fachspezialistVertriebswege: this.vertriebsweg,
            fachspezialistHandlungsfelder: this.handlungsfeld,
            fachspezialistSparten: this.sparte,
            fachspezialistZielgruppen: this.zielgruppe,
            istFachspezialist: true,
            istAdmin: false,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Benutzer erfolgreich aktualisiert", "success");
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Aktualisieren",
            "error"
          )
        );
      }
      else if(this.radiobutton == "ROLE_ADMIN")
      {
        axiosInstance.put("http://localhost:9090/benutzer",
          {
            benutzername: this.aktiverMitarbeiter.benutzername,
            vorname: this.aktiverMitarbeiter.vorname,
            nachname: this.aktiverMitarbeiter.nachname,
            email: this.aktiverMitarbeiter.email,
            fachspezialistVertriebswege: null,
            fachspezialistHandlungsfelder: null,
            fachspezialistSparten: null,
            fachspezialistZielgruppen: null,
            istFachspezialist: false,
            istAdmin: true,
          },
          config
        )
        .then((response) => {
          this.$alert("", "Benutzer erfolgreich aktualisiert", "success");
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Aktualisieren",
            "error"
          )
        );
      }
    },
    ladeSystemnachrichten(){
      var jwt = require("jsonwebtoken");
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios.get("http://localhost:9090/systemnachricht", config)
        .then((res) => {
          this.systemnachrichtListe = res.data || [];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    selectSystemnachricht(systemnachricht)
    {
      this.aktiveSystemnachricht = systemnachricht;
      console.log(this.aktiveSystemnachricht)
    },
    loescheSystemnachricht()
    {
      var jwt = require("jsonwebtoken");
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      };

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axiosInstance.post("http://localhost:9090/systemnachricht/loeschen",
          {
            titel: this.aktiveSystemnachricht.titel,
            beschreibung: this.aktiveSystemnachricht.beschreibung
          },
          config
        ).then((response) => {
          this.ladeSystemnachrichten();
          this.$alert(response.data, "Nachricht erfolgreich gelöscht")
        })
        .catch((error) => {
          this.$alert(error.response.data.fehlertext, "Fehler beim Löschen", "error")
        });
    }
  },
  created() {
    this.ladeMitarbeiter();
  }
});
</script>

<style lang="scss" scoped>
$medium-green: #00894d;
$light-green: #69a82f;
.spezialisierungsDropdown,
select {
  width: 100%;
}
p,
label,
button {
  font-size: 1rem;
}
.container-fluid {
  height: 100%;
}
.container-fluid,
button,
#betreff,
#text {
  border-radius: 20px;
}
.container-fluid {
  overflow: hidden;
}
.combobox {
  margin-right: 2px;
}
.container-fluid {
  width: 800px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
  padding-left: 30px;
  padding-right: 30px;
}
.spezialisierungsDropdown {
  display: flex;
  justify-content: space-between;
}
.buttons {
  padding: 1%;
  margin: auto;
}
#rollenBtn,
#nachrichtLesenBtn,
#rolleBestätigenBtn {
  background-color: $light-green;
  border: none;
}
#nachrichten,
#nachrichtLöschenBtn {
  border: 1.5px solid #8300008e;
  color: #830000d5;
  font-size: 1rem;
}
button {
  border: 1px solid #fff;
  font-weight: bold;
  padding: 2px 8px;
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
select {
  font-size: 0.8rem;
}
#anzeige-aktuelle-seite {
  text-align: center;
  color: grey;
  line-height: 2;
  font-size: 0.9rem;
  padding-top: 1rem;
}
.zurueckBtn,
#abmelden {
  color: #fff;
  background-color: #f80303;
}
#betreff,
#text {
  background: #fff;
  border: 1px solid #ccc;
  width: 100%;
  padding: 1%;
}
#text {
  height: 80%;
}
.nachrichtDetails {
  width: 100%;
  height: 100%;
}
ul {
  width: 100%;
  height: 85%;
  background: #fff;
  margin: 0;
  padding-bottom: 0;
  overflow: hidden;
  overflow-y: scroll;
}
li {
  padding: 1px;
}
.row {
  display: flex;
  justify-content: space-between;
}
.mitarbeiterListe {
  height: 68%;
}
#mitarbeiterName {
  margin: auto;
  font-size: 1.2rem;
}
.mitarbeiterModal {
  height: 85%;
}
.rolleMitarbeiter,
.rolleSpezialist,
.rolleAdmin {
  width: 100%;
}
.nachrichtenModal,
.nachrichtenListe {
  height: 85%;
}
.nachrichtLesen {
  height: 85%;
}
</style>