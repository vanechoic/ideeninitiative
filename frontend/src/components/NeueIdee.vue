<template>
  <article>
    <div class="container-fluid" name="NeueIdeeContainer">
        <h1>Neue Idee</h1>
        <div class="row">
            <!-- Beschreibungstext -->
            <div class="beschreibung">
                <h2> Beschreibungstext </h2>
                <textarea id="beschreibungsText"> </textarea>
            </div>
        </div>
        <div class="row">
            <!-- Datei Upload -->
            <div class="dateien">
                <h2> Datei Upload </h2>
                <input type="file">
            </div>
        </div>
        <div class="row">
            <!-- Vorteile -->
            <div class="vorteile">
                <h2> Vorteile </h2>
                <input id="vorteile" type="text">
                <button class="ideeButton" id="hinzu"> + </button>
                <button class="ideeButton" id="entfernen"> - </button><br>
                <textarea readonly id="vorteileAnzeigen"> </textarea>
            </div>
        </div>
        <div class="row">
            <!-- Existierende Idee - Checkbox -->
            <div class="existiert">
                <label for="existiertBereits"> Existiert eine vergleichbare Idee? </label>
                <input type="checkbox" id="existiertBereits">
            </div>
        </div>
        <div class="row">
        <!--Comboboxen zur Ideen-Spezifikation -->
        <div class="ideenDropdowns">
            <div class="combobox">
                <p> Ideen-Typ </p>
                <select>
                    <option value="Sparte 1">Sparte 1</option>
                    <option value="Sparte 2">Sparte 2</option>
                    <option value="Sparte 3">Sparte 3</option>
                    <option value="Sparte 3">Sparte 4</option>
                </select>
            </div>
            <div class="combobox">
                <p> Sparte </p>
                <select>
                    <option value="Sparte 1">Sparte 1</option>
                    <option value="Sparte 2">Sparte 2</option>
                </select>
            </div>
            <div class="combobox">
                <p> Vertriebskanal </p>
                <select>
                    <option value="Sparte 1">Sparte 1</option>
                    <option value="Sparte 2">Sparte 2</option>
                    <option value="Sparte 3">Sparte 3</option>
                </select>
            </div>
            <div class="combobox">
                <p> Zielgruppen </p>
                <select>
                    <option value="Sparte 1">Sparte 1</option>
                    <option value="Sparte 2">Sparte 2</option>
                    <option value="Sparte 3">Sparte 3</option>
                </select>
            </div>
            <div class="combobox">
                <p> Handlungsfelder </p>
                <select>
                    <option value="Sparte 1">Sparte 1</option>
                    <option value="Sparte 2">Sparte 2</option>
                    <option value="Sparte 3">Sparte 3</option>
                </select>
            </div>
        </div>
        </div>
        <div class="row">
            <div class="buttons">
                <button id="entfernen" @click="goBack()"> Abbrechen </button>
                <button id="hinzu"> Speichern </button>
            </div>
        </div>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
    methods: {
    goBack() {
      if (window.history.length > 1)
        this.$router.go(-1)
    }
  }
  })
</script>

<style lang="scss" scoped>

.container-fluid{
    float: left;
    justify-content: space-around;
    height: calc(100% - 20px);
    width:800px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
    .vorteile button{
        padding: 5px;
        margin: 5px;
    }
    h1{
        text-align: center;
        margin: 1%;
    }
    label{
        font-size: 1.1em;
       // margin-right: 30px;
    }
    .beschreibung{
        top:2%;
       // margin: auto;
        left:0;
        right: 0;
        height: 250px;
    }
    .beschreibung textarea{
        //resize: none;
        width: 100%;
        height: 80%;
    }
    .ideeButton{
        width: 30px;
        height: 30px;
    }
    .dateien{
       // margin: 20px;
    }
    .vorteile{
        //margin: 20px;
    }
    #vorteileAnzeigen{
        //resize: none;
        width: 30%;
    }
    #vorteile{
        width: 30%;
    }
    .existiert{
        //margin: 30px;
        margin-bottom: 0px;
    }
    .buttons{
        float: right;
        //margin: 2%;
    }
    #hinzu{
        background-color:#00894d;
        color: #fff;
    }
    #entfernen{
        background-color: #f80303;
        color: #fff;
        margin-right: 2px;
    }
  }
 button {
    border-radius: 20px;
    border: 1px solid #fff;
    color: rgb(0, 0, 0);
    font-size: 1rem;
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
p, label{
    font-size: .75em;
}
.combobox{
    float: left;
    //margin: 35px;
}
</style>