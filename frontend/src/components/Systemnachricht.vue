<template>
  <div class="container">
    <p id="anzeige-aktuelle-seite">Systemnachricht</p>
    <form>
      <label for="betreff">Betreff:</label>
      <input type="text" id="betreff" v-model="titel" />
      <label for="nachricht">Nachricht:</label>
      <textarea id="nachricht" rows="20" cols="50" v-model="beschreibung"></textarea>
      <!-- Beachten ob ein Nutzer eingeloggt ist! !-->
      <button class="roter-button" @click="goBack()">Abbrechen</button>
      <button @click="nachrichtAbschicken()">Senden</button>
    </form>
  </div>
</template>

<script lang="ts">
import Vue from "vue";
import axios from "axios";
import { Helper } from "../services/helper";
export default Vue.extend({
  name: "Systemnachricht",
  data: () => ({
    titel: "",
    beschreibung: "",
    baseUrl: process.env.VUE_APP_BACKEND_BASE_URL,
  }),
  methods: {
    goBack() {
      if (window.history.length > 1) this.$router.go(-1);
    },
    nachrichtAbschicken() {
      var axiosInstance = Helper.getInstance().createAxiosInstance();
      axios
        .post(this.baseUrl + "/systemnachricht",{
          titel: this.titel,
          beschreibung: this.beschreibung
        }).then((response) => {
          this.$alert("", "Systemnachricht gesendet");
          this.goBack();
        })
        .catch((error) =>
          this.$alert(
            error.response.data.fehlertext,
            "Fehler beim Senden",
            "error"
          )
        );
    },
  },
});
</script>

<style lang="scss" scoped>
textarea,
input,
form,
p,
button {
  width: 100%;
}
.container,
input {
  border-radius: 20px;
}
input,
.container {
  overflow: hidden;
}
div {
  font-size: 1rem;
}
.container {
  align-items: center;
  width: 900px;
  height: 100%;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  background: linear-gradient(to bottom, #efefef, #ccc);
}
input {
  background-color: #eee;
  border: none;
  padding: 5px 15px;
  border-bottom: 1px solid #ddd;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.4), 0 -1px 1px #fff, 0 1px 0 #fff;
  &:focus {
    outline: none;
    background-color: #fff;
  }
}
form {
  display: flex;
  flex-direction: column;
  padding: 10px 50px;
  height: 100%;
  background: linear-gradient(to bottom, #efefef, #ccc);
  transition: all 0.5s ease-in-out;
}
label {
  font-size: 1rem;
  width: 5em;
}
textarea{
  height: 50%;
}
button{
  margin-top: 10px;
}
</style>