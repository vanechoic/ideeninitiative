<template>
  <article>
    <div class="container">
      <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild" />
      <button id="profilbildHochladen">Profilbild hochladen</button>
      <router-link id="ideen" to="/IdeeBewerten" tag="button">Zugewiesende Ideen</router-link>
      <button id="ideenspeicher">Zum Ideenspeicher</button>
      <router-link id="abmelden" to="/" tag="button">Abmelden</router-link>
    </div>
  </article>
</template>

<script>
import Vue from "vue"
export default Vue.extend({
  name: 'Spezialist'
})
</script>

<style lang="scss" scoped>
button {
  border-radius: 20px;
  border: 1px solid #fff;
  color: #fff;
  font-size: 0.75rem;
  font-weight: bold;
  padding: 10px 40px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  margin-top: 10px;
  transition: transform 0.1s ease-in;
  &:active {
    transform: scale(0.9);
  }
  &:focus {
    outline: none;
  }
}
#zurSystemnachricht,
#profilbildHochladen {
  background-color: #0a0404;
}
#zurückZurAnmeldung {
  background-color: #f80303;
}
#ideen {
  margin-right: 2px;
}
#ideen,
#ideenspeicher {
  color: #000;
}
#abmelden {
  background-color: #f80303;
}
</style>