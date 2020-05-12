<template>
  <article>
    <div class="container">
        <div class="links">
            <p>Meine Ideen</p>
            <!--Liste Tutorial:https://www.youtube.com/watch?v=7ZSljEfaiYUs API:https://ej2.syncfusion.com/vue/documentation/api/list-box -->
            <div>
                <ejs-listbox :dataSource='dataItem' :fields='dataFields' cssClass='liste'>
                </ejs-listbox>
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
            <button id="ideeBearbeiten"> Bearbeiten </button>
            <button id="ideeLoeschen"> Löschen </button>
            <router-link to="Startseite" tag="button" id="zurueck"> Zurück </router-link>
        </div>
    </div>
  </article>
</template>

<script lang="ts">
import Vue from 'vue'
import {ListBoxPlugin} from '@syncfusion/ej2-vue-dropdowns'
Vue.use(ListBoxPlugin)
  export default {
    data: () => {
      return {
        dataItem:[
            {Id:'i1', Idee:'Idee1'},
            {Id:'i2', Idee:'Idee als Beispiel für Liste '},
            {Id:'i3', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i4', Idee:'Idee als Beispiel für Liste '},
            {Id:'i5', Idee:'Idee die sehr kreativ ist'}
        ],
        dataFields:{value:'Id', text:'Idee'}
      }
    }
  }
</script>

<style lang="scss" scoped>
.container {
    position: relative;
    width: 900px;
    height: calc(100% - 20px);
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
  }
 button {
    border-radius: 20px;
    border: 1px solid #fff;
    color: rgb(0, 0, 0);
    font-size: .75rem;
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
.links{
    position: relative;
    top: 0;
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    padding: 2%;
    width: calc(50% - 20px);
    height: calc(100% - 30px);
    text-align: left;

    p {
        margin: 8px 0 8px;
        top:0;
    }
    .liste{
        width: 100%;
        height: 300px;
    }
    .filterElement{
        float: left;
    }
    .filter{
        float: left;
        width: 100%;
        margin-right: 3.33333%;
        padding: 10px;
        box-sizing: border-box;

        #filter1, #filter2{
            margin-right: 2px;
        }
    }
    button {
        border-radius: 20px;
        border: 1px solid #00894d;
        background-color:#00894d;
        color: #fff;
        font-size: .75rem;
        font-weight: bold;
        padding: 2px 5px;
        margin-left: 60%;
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
}
.rechts{
      position: absolute;
      top: 0;
      display: flex;
      align-items: center;
      justify-content: space-around;
      flex-direction: column;
      padding: 2%;
      width: calc(50% - 55px);
      height: calc(100% - 20px);
      text-align: center;
      background-color: #00894d;
      margin-left:52.5%;
  }
</style>