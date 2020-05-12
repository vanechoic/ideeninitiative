<template>
  <article>
    <div class="container">
        <h1>Idee bearbeiten</h1>
        <!-- Beschreibungstext -->
        <div class="beschreibung">
            <p> Beschreibungstext </p>
            <textarea> </textarea>
        </div>
        <!-- Datei Upload -->
        <div class="dateien">
            <p> Datei Upload </p>
            <input type="file">
        </div>
        <!-- Vorteile -->
        <div class="vorteile">
            <p > Vorteile </p>
            <input type="text">
            <button class="ideeButton" id="hinzu"> + </button>
            <button class="ideeButton" id="entfernen"> - </button><br>
            <textarea readonly> </textarea>
        </div>
        <!-- Existierende Idee - Checkbox -->
        <div class="existiert">
            <label for="existiertBereits"> Existiert eine vergleichbare Idee? </label>
            <input type="checkbox" id="existiertBereits">
        </div>
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
        <div class="buttons">
            <button id="hinzu"> Abbrechen </button>
            <button id="entfernen"> Speichern </button>
        </div>
    </div>
  </article>
</template>

<script lang="ts">

  export default {
    
  }
</script>

<style lang="scss" scoped>
.container {
    position: relative;
    float: left;
    width: 900px;
    height: calc(100% - 20px);
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
    }
    .beschreibung{
        margin-left: 30px;
        margin-right: 30px;
        height: 200px;
    }
    .beschreibung textarea{
        resize: none;
        width: 70%;
        height: 100px;
    }
    .ideeButton{
        width: 30px;
        height: 30px;
    }
    .dateien{
        margin: 30px;
    }
    .vorteile{
        margin: 30px;
    }
    .existiert{
        margin: 30px;
        margin-bottom: 0px;
    }
    .buttons{
        float: right;
        margin: 30px;
    }
    #hinzu{
        background-color:green;
    }
    #entfernen{
        background-color: red;
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
    margin-top:10px;
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
    margin: 35px;
}
</style>