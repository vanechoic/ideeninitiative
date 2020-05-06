<template>
  <article>
    <div class="container">
        <button id="zurSystemnachricht">Systemnachricht verfassen</button>
        <button id="zurückZurAnmeldung">Zurück zur Anmeldung</button>

    </div>
  </article>
</template>

<script>
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