<template>
  <article>
    <div class="container">
        <p class="rollen">Sie sind nicht eingeloggt</p>
        <div class="buttons">
          <router-link to="/Systemnachricht" tag="button" id="zurSystemnachricht">Systemnachricht verfassen</router-link>
          <router-link to="/" tag="button" id="zurückZurAnmeldung">Zurück zur Anmeldung</router-link>
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
  #zurückZurAnmeldung,  #zurSystemnachricht{
    width: 80%;
  }
  .rollen{
    color: black;
    margin-top: 1%
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