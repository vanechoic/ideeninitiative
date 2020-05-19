<template>
  <article>
    <div class="container">
        <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild">
        <button id="profilbildHochladen">Profilbild hochladen</button>
        <router-link id="ideen" to="/Meineideen" tag="button">Meine Ideen</router-link>
        <router-link id="ideeErstellen" to="/Ideeanlegen" tag="button">Idee erstellen</router-link>
        <router-link id="zurSystemnachricht" to="/Systemnachricht" tag="button">Systemnachricht verfassen</router-link>
        <router-link id="abmelden" to="/" tag="button">Abmelden</router-link>
    </div>
  </article>
</template>

<script lang="ts">
export default {
  name: 'Registrierter',
}


</script>

<style lang="scss" scoped>
 button {
    border-radius: 20px;
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
  #zurSystemnachricht, #profilbildHochladen{
    background-color:#0a0404;
    width: 80%;
    left:0;
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
    background-color:#ccc;
  } 
</style>