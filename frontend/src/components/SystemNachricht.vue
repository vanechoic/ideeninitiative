<template>
  <article>
    <div class="container">
        <p>Bitte geben sie ihren Betreff und ihre Nachricht ein.</p>
          <form>
              <label for="betreff">Betreff:</label>
              <input type="text" id="betreff">
              <label for="nachricht">Nachricht:</label>
              <textarea id="nachricht" rows="20" cols="50"></textarea>
              <button id="abbrechenButton">Abbrechen</button>
              <button id="sendenButton">Senden</button> 
          </form>
    </div>
  </article>
</template>

<script>
export default {
  name: 'SystemNachricht'
}
</script>

<style lang="scss" scoped>
  .container {
    position: relative;
    align-items: center;
    width: 768px;
    height: 100%;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
  }
  h2 {
    margin: 0;
  }
  p {
    margin: 10px 12.5%;
    width: 80%;
  }
  button {
    border-radius: 20px;
    border: 1px solid #00894d;
    background-color:#00894d;
    color: #fff;
    font-size: 1rem;
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
  form {
    top: 0;
    display: flex;
    align-items: left;
    justify-content: space-around;
    flex-direction: column;
    padding: 10px 60px;
    width: calc(80% - 120px);
    height: calc(100% - 100px);
    text-align: center;
    background: linear-gradient(to bottom, #efefef, #ccc);
    transition: all .5s ease-in-out;
    margin: auto;
    div {
      font-size: 1rem;
    }
    input {
      background-color: #eee;
      border: none;
      padding: 5px 15px;
      margin: 6px 0;
      width: calc(40%);
      border-radius: 15px;
      border-bottom: 1px solid #ddd;
      box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4), 
                        0 -1px 1px #fff, 
                        0 1px 0 #fff;
      overflow: hidden;
      &:focus {
        outline: none;
        background-color: #fff;
      }
    }
  }
  label{
    position: relative;
    font-size: 1rem;
    float: left;
    width: 5em;
    margin-bottom: 5px;
  }
  #abbrechenButton{
    background-color:#ec0b0b;
    border-color: #fff;
  }
</style>