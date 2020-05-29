<template>
  <article>
    <div class="container">
        <p>Bitte geben sie ihren Betreff und ihre Nachricht ein.</p>
          <form>
              <label for="betreff">Betreff:</label>
              <input type="text" id="betreff">
              <label for="nachricht">Nachricht:</label>
              <textarea id="nachricht" rows="20" cols="50"></textarea>
              <!-- Beachten ob ein Nutzer eingeloggt ist! !-->
              <button id="abbrechenButton" @click="goBack()">Abbrechen</button>
              <button id="sendenButton">Senden</button> 
          </form>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
  name: 'Systemnachricht',
  methods: {
    goBack() {
      if (window.history.length > 1)
        this.$router.go(-1)
    }
  }
})
</script>

<style lang="scss" scoped>
  textarea, #abbrechenButton, input, form, p{
    width:100%
  }
  button, .container, input{
    border-radius: 20px;
  }
  input, .container{
    overflow: hidden;
  }
  button, div{
    font-size: 1rem;
  }
  .container {
    position: absolute;
    height: calc(100% - 250px);
    top:2%;
    left: 2%;
    right: 2%;
    bottom: 2%;
    align-items: center;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
  }
  button {
    border: 1px solid #fff;
    background-color:#00894d;
    color: #fff;
    font-weight: bold;
    padding: 10px 40px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    margin: 5px;
    transition: transform .1s ease-in;
    &:active {
      transform: scale(.9);
    }
    &:focus {
      outline: none;
    }
  }
  input {
      background-color: #eee;
      border: none;
      padding: 5px 15px;
      border-bottom: 1px solid #ddd;
      box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4), 
                        0 -1px 1px #fff, 
                        0 1px 0 #fff;
      &:focus {
        outline: none;
        background-color: #fff;
      }
    }
  form {
    display: flex;
    flex-direction: column;
    padding: 10px 60px;
    height: calc(100% - 50px);
    background: linear-gradient(to bottom, #efefef, #ccc);
    transition: all .5s ease-in-out;
  }
  p {
    margin: 10px 16.5%;
    font-size: 2rem;
  }
  textarea{
    height: 80%;
  }
  #abbrechenButton{
    background-color:#ec0b0b;
  }
  label{
    font-size: 1rem;
    width: 5em;
  }
</style>