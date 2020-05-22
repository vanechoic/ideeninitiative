<template>
  <article>
    <div class="container">
        <div class="links">
            <p>Meine Ideen</p>
            <div class="listeContainer">
              <ul class="liste">
                <li v-for="idee in Ideen" :key="idee.Idee">{{idee.Idee}}</li>
              </ul>
            </div>
            <!--3 Filter Dropdowns -->
            <div class="filter">
                <div class="filterElement">
                    <select id="filter1">
                        <option value="Sparte 1">Sparte 1</option>
                        <option value="Sparte 2">Sparte 2</option>
                        <option value="Sparte 3">Sparte 3</option>
                        <option value="Sparte 3">Sparte 4</option>
                    </select>
                    <select id="filter2">
                        <option value="Sparte 1">Sparte 1</option>
                        <option value="Sparte 2">Sparte 2</option>
                    </select>
                    <select id="filter3">
                        <option value="Sparte 1">Sparte 1</option>
                        <option value="Sparte 2">Sparte 2</option>
                        <option value="Sparte 3">Sparte 3</option>
                    </select>
                </div>
                <div class="filterElement">
                    <!--Filter Button -->
                    <button id="filterButton">Filter</button>
                </div>
            </div> 
        </div>
        <div class="rechts">
            <button id="ideeVeroeffentlichen"> Idee veröffentlichen </button>
            <router-link to="IdeeBearbeiten" tag="button" id="ideeBearbeiten"> Bearbeiten </router-link>
            <button id="ideeLoeschen"> Löschen </button>
            <router-link to="Startseite" tag="button" id="zurueck"> Zurück </router-link>
        </div>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
    data: () => {
      return {
        Ideen:[
            {Id:'i1', Idee:'Idee1'},
            {Id:'i2', Idee:'Idee als Beispiel für Liste '},
            {Id:'i3', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Ikjafbnakibnfaof '},
            {Id:'i5', Idee:'Idee die sehr kreativ ist'}
        ],
        dataFields:{value:'Id', text:'Idee'}
      }
    },
    methods: {
      goBack() {
      if (window.history.length > 1)
        this.$router.go(-1)
      }
    }
  })
</script>

<style lang="scss" scoped>
  .container, .links, .rechts{
    height: 100%;
  }
  p, .rechts, .links{
    top: 0;
  }
  .container, button, #fliterButton{
    border-radius: 20px;
  }
  #ideeVeroeffentlichen,#ideeBearbeiten, #zurueck{
    background-color: black;
  }
  .container, .links{
    position: relative;
  }
  .links, .rechts{
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 50%;
    flex-direction: column;
  }
  .rechts, #filterButton{
    background-color:#00894d;
  }
  .filter, .filterElement{
    float: left;
  }
  button, #filterButton{
    color: #fff;
    font-size: .75rem;
    font-weight: bold;
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
  .listeContainer, li{
    border: 0.5px solid #000;
  }
  .filter, .listeContainer{
    width: 100%;
  }
  .listeContainer, ul {
    height: 400px;
  }
  .container {
    width: 900px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
  }
  ul {
    list-style: none;
    margin: 0;
    overflow: auto;
    padding: 0;
    text-indent: 10px;
  }
  li:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }   
  .filter{
    padding: 10px;
  }
  .rechts{
    position: absolute;
    margin-left: 52.5%;
  }
  p {
    margin: 8px 0 8px;
  }
  #filterButton{
    border: 1px solid #00894d;
    padding: 2px 5px;
    margin-left: 60%;
  }
  #ideeLoeschen{
    background-color: #f80303;
  }
  button {
    border: 1px solid #fff;
    padding: 10px 40px;
    margin-top:10px;
  }
  li {
    line-height: 30px;
    color: black;
  }
</style>