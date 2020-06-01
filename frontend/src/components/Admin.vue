<template>
  <article>
    <div class="container">
      <p>Eingeloggt als Admin</p>
      <!---->

      <label for="liste">Mitarbeiter:</label>
        <ul class="liste">
          <li v-on:click="showModal=true"> beispiel</li>
        </ul>
        <button id="nachrichten">Systemnachrichten lesen</button>
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