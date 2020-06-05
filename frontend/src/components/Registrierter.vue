<template>
  <article>
    <div class="container">
      <p class="rolle">Eingeloggt als Mitarbeiter</p>
        <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild">
        <div class="profilbild-upload-wrapper" v-if="false">
          <!-- form und action für das submit hinzufügen !-->
          <input type="file" name="profilbild">
          <button class="profilbildHochladen hellgruener-button">Profilbild hochladen</button>
        </div>
        <router-link id="ideen" to="/Meineideen" tag="button" class="hellgruener-button">Meine Ideen</router-link>
        <router-link id="ideeErstellen" to="/Ideeanlegen" tag="button" class="hellgruener-button">Idee erstellen</router-link>
        <router-link id="zurSystemnachricht" to="/Systemnachricht" tag="button" class="hellgruener-button">Systemnachricht verfassen</router-link>
        <router-link id="abmelden" :to="{ path: '/' }" replace tag="button" @click="logout()" class="roter-button">Abmelden</router-link>
    </div>
  </article>
</template>

<script lang="ts">
export default {
  name: 'Registrierter',
  methods: {
    logout: function(){
      localStorage.clear();
    },
  }
}
</script>

<style lang="scss" scoped>
  #ideen{
    margin-right: 2px;
  }
  .profilbild-upload-wrapper {
  position: relative;
  overflow: hidden;
  cursor: pointer;
  }
  .profilbild-upload-wrapper input[type=file] {
  position: absolute;
  font-size: 100px;
  left: 0;
  top: 0;
  opacity: 0;
  }
  .rolle{
     color: #444;
    margin-top: 2%;
    margin-right: 10px;
    line-height: 2;
    font-size: 0.9rem;
    text-align: right;
  }
  .container{
    height: 100%;
    position: absolute;
    top:0;
    left: 0;
  }
  button, .roter-button, .hellgruener-button{
    width: 97%;
  }
</style>