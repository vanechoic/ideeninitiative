<template>
  <article>
    <div class="container">
        <p class="rollen">Sie sind nicht eingeloggt.</p>
        <div class="buttons">
          <router-link to="/Systemnachricht" tag="button" class="hellgruener-button">Systemnachricht verfassen</router-link>
          <router-link to="/" tag="button" class="roter-button">Zurück zur Anmeldung</router-link>
        </div>
    </div>
  </article>
</template>

<script lang="ts">
export default {
  name: 'Mitarbeiter'
}
</script>

<style lang="scss" scoped>
  router-link{
    width: 80%;
  }
  .rollen{
    color: #444;
    margin-top: 2%;
    margin-right: 10px;
    line-height: 2;
    font-size: 0.9rem;
    text-align: right;
  }
  button{
    width: 97%;
  }
  .buttons{
    margin-top:55%;
    height: 40%;
  }
  .container{
    top:0;
    left: 0;
    position:absolute;
    height: 100%;
  }
</style>