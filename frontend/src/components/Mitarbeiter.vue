<template>
  <article>
    <div class="container">
        <router-link to="/Systemnachricht" tag="button" id="zurSystemnachricht">Systemnachricht verfassen</router-link>
        <router-link to="/" tag="button" id="zurückZurAnmeldung">Zurück zur Anmeldung</router-link>
    </div>
  </article>
</template>

<script lang="ts">
export default {
  name: 'Mitarbeiter'
}
</script>

<style lang="scss" scoped>
  button {
    border-radius: 20px;
    border: 1px solid #fff;
    color: #fff;
    font-size: .75rem;
    font-weight: bold;
    margin: 5px;
    padding: 10px 40px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    transition: transform .1s ease-in;
    &:active {
      transform: scale(.9);
    }
    &:focus {
      outline: none;
    }
  }
  #zurSystemnachricht{
    background-color:#0a0404;
  }
  #zurückZurAnmeldung{
    background-color:#f80303;
  }
</style>