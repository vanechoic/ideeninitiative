<template>
  <article>
    <div class="container">
      <p class="rolle">Eingeloggt als Spezialist</p>
      <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild" />
      <button id="profilbildHochladen">Profilbild hochladen</button>
      <router-link to="/IdeeBewerten" tag="button" @click="setVariable()" class="hellgruener-button">Zugewiesende Ideen</router-link>
      <button v-on:click="push()">Zum Ideenspeicher</button>
      <router-link to="/" tag="button" class="roter-button">Abmelden</router-link>
    </div>
  </article>
</template>

<script>
import Vue from "vue"
export default Vue.extend({
  name: 'Spezialist',
  methods:{
    push: function () {
      this.$router.push({ path: "/IdeenSpeicher" });
    },
    setVariable() {
      localStorage.setItem("bewerten", "true");
    },
  },
})
</script>

<style lang="scss" scoped>
article, .container{
  height: 100%;
}
button,
#profilbildHochladen {
  width: 85%;
  margin-top: 10px;
}
.rolle{
  color: #444;
  margin-top: 2%;
  margin-right: 10px;
  line-height: 2;
  font-size: 0.9rem;
  text-align: right;
}
</style>