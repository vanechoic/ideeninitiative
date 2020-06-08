<template>
  <article>
    <div class="container">
      <p class="rolle">Eingeloggt als Mitarbeiter</p>
      <img :src="profilbildLaden" alt="Profilbild" />
      <div class="profilbild-upload-wrapper">
        <!-- form und action für das submit hinzufügen !-->
        <input type="file" name="profilbild" @change="profilbildHochladen" />
        <button class="profilbildHochladen hellgruener-button">Profilbild hochladen</button>
      </div>
      <router-link id="ideen" to="/Meineideen" tag="button" class="hellgruener-button">Meine Ideen</router-link>
      <router-link
        id="ideeErstellen"
        to="/Ideeanlegen"
        tag="button"
        class="hellgruener-button"
      >Idee erstellen</router-link>
      <router-link
        id="zurSystemnachricht"
        to="/Systemnachricht"
        tag="button"
        class="hellgruener-button"
      >Systemnachricht verfassen</router-link>
      <router-link
        id="abmelden"
        :to="{ path: '/' }"
        replace
        tag="button"
        @click="logout()"
        class="roter-button"
      >Abmelden</router-link>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import { Helper } from "../services/helper";
export default Vue.extend({
  name: "Registrierter",
  data: () => ({
    // Auth Token
    token: localStorage.getItem("token"),
    baseUrl: process.env.VUE_APP_BACKEND_BASE_URL,
  }),
  asyncComputed: {
    profilbildLaden: {
      get() {
        var jwt = require("jsonwebtoken");
        var decode = jwt.decode(this.token);
        var nutzer = decode["sub"];
        let config = {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        };

        var axiosInstance = Helper.getInstance().createAxiosInstance();
        return axios
          .get(this.baseUrl + "/benutzer/profilbild", config)
          .then((res) => {
            if (res.data) {
              return (
                "data:" +
                (res.data.dateityp || "") +
                ";base64," +
                (res.data.dateiinhalt || "")
              );
            }
            return "https://www.w3schools.com/images/picture.jpg";
          })
          .catch((err) => {
            console.log(err);
          });
      },
      default: "https://www.w3schools.com/images/picture.jpg",
    },
  },
  methods: {
    logout: function () {
      localStorage.clear();
    },
    profilbildHochladen(event) {
      console.log(event.target.files);
      var jwt = require("jsonwebtoken");
      var decode = jwt.decode(this.token);
      var nutzer = decode["sub"];
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
          "Content-Type": "multipart/form-data",
        },
      };
      var formData = new FormData();
      formData.append("file", event.target.files[0]);

      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios
        .post(this.baseUrl + "/benutzer/profilbild", formData, config)
        .then((res) => {
          this.$alert("", res.data, "success");
        })
        .catch((err) => {
          this.$alert("", err.response.data.fehlertext, "error");
          console.log(err);
        });
      location.reload();
    },
  },
});
</script>

<style lang="scss" scoped>
img {
  max-height: 200px;
  max-width: 97%;
  border-radius: 20px;
}
#ideen {
  margin-right: 2px;
}
.profilbild-upload-wrapper {
  position: relative;
  overflow: hidden;
  cursor: pointer;
}
.profilbild-upload-wrapper input[type="file"] {
  position: absolute;
  font-size: 100px;
  left: 0;
  top: 0;
  opacity: 0;
}
.rolle {
  color: #444;
  margin-top: 2%;
  margin-right: 10px;
  line-height: 2;
  font-size: 0.9rem;
  text-align: right;
}
.container {
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
}
button,
.roter-button,
.hellgruener-button {
  width: 97%;
}
</style>