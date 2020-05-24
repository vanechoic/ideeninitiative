<template>
  <article>
    <div class="container">
        <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild">
        <button id="profilbildHochladen">Profilbild hochladen</button>
        <button id="nachrichten">Systemnachrichten lesen</button>
        <button id="lMitabeiter">Liste der Mitarbeiter</button>
    </div>
  </article>
</template>

<script>
export default {
  name: 'Admin'
}
</script>

<style lang="scss" scoped>
  #profilbildHochladen{
    background-color:#0a0404;
  }
  #zurückZurAnmeldung{
    background-color:#f80303;
  }
  #nachrichten{
    margin-right: 2px;
  }
  #nachrichten, #lMitarbeiter{
    color: #000;
  } 
</style>