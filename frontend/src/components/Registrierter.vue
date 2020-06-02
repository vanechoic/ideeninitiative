<template>
  <article>
    <div class="container">
      <p class="rolle">Eingeloggt als Mitarbeiter</p>
        <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild">
        <div class="profilbild-upload-wrapper">
          <!-- form und action für das submit hinzufügen !-->
          <input type="file" name="profilbild">
          <button class="profilbildHochladen">Profilbild hochladen</button>
        </div>
        <router-link id="ideen" to="/Meineideen" tag="button">Meine Ideen</router-link>
        <router-link id="ideeErstellen" to="/Ideeanlegen" tag="button">Idee erstellen</router-link>
        <router-link id="zurSystemnachricht" to="/Systemnachricht" tag="button">Systemnachricht verfassen</router-link>
        <router-link id="abmelden" :to="{ path: '/' }" replace tag="button" @click="logout()">Abmelden</router-link>
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
 button {
    border-radius: 20px;
    width: 90%;
    border: 1px solid #fff;
    color: #fff;
    font-size: .75rem;
    font-weight: bold;
    padding: 10px 40px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    margin-top:10px;
    transition: transform .1s ease-in;
    &:active {
      transform: scale(.9);
    }
    &:focus {
      outline: none;
    }
  }
  #zurSystemnachricht, .profilbildHochladen{
    background-color:#0a0404;
    width: 90%;
    left: 0;
    right: 0;
  }
  #abmelden{
    background-color:#f80303;
  }
  #ideen{
    margin-right: 2px;
  }
  #ideen, #ideeErstellen{
    color: #000;
    background-color: #ffdd00;
    border: none;
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
    color:#000;
  }
  .container{
    height: 100%;
    position: absolute;
    top:0;
    left: 0;
  }
</style>