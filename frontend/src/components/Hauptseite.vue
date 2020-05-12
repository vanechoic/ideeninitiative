<template>
    <div class="container">
        <div class="links">
            <p>Alle Ideen</p>
            <div class="listeContainer">
                <ul class="liste">
                    <li v-for='idee in Ideen' :key='idee.Idee'  v-on:click="showModal = true">{{idee.Idee}}</li>
                </ul>
            </div>
            <!--3 Filter Dropdowns -->
            <div class="filter">
                <div class="filterElement">
                    <select id="filter1">
                        <option value="" disabled selected>Filter 1</option>
                        <option value="Sparte 1">Sparte 1</option>
                        <option value="Sparte 2">Sparte 2</option>
                        <option value="Sparte 3">Sparte 3</option>
                        <option value="Sparte 3">Sparte 4</option>
                    </select>
                    <select id="filter2">
                        <option value="" disabled selected>Filter 2</option>
                        <option value="Sparte 1">Sparte 1</option>
                        <option value="Sparte 2">Sparte 2</option>
                    </select>
                    <select id="filter3">
                        <option value="" disabled selected>Filter 3</option>
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
            <component v-bind:is="component"></component>
        </div>
        <transition name="fade" appear>
            <div class="modal-overlay" v-if="showModal">
                <button class="zurückBtn" v-on:click="showModal =false">Zurück</button>
            </div>  
        </transition>
    </div>
</template>

<script lang="ts">
import Vue from 'vue'
import {ListBoxPlugin} from '@syncfusion/ej2-vue-dropdowns'
import Mitarbeiter from './Mitarbeiter.vue'
Vue.use(ListBoxPlugin)
  export default {
    components: {
        'mitarbeiter': Mitarbeiter,
    },
    data: () => {
      return {
        showModal:false,
        component:'mitarbeiter',
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

    .demo{
        position: absolute;
        margin: 20% 0;
        z-index: 110;
    }
    #rm{
        margin: 60% 0;
    }
    #ad{
        margin: 67% 0;
    }
    #mi{
        margin: 74% 0;
    }
    #liste{
        height: 100px;
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
    .listeContainer{
        width: 100%;
        height: 400px;
        border:1px solid #000;
        font-family:arial;
    }
    ul{
        height: 400px;
        list-style:none;
        margin:0;
        overflow:auto;
        padding:0;
        text-indent:10px;
    }
    li{
        line-height:30px;
        color: rgba(0, 0, 0, 9);
        border: .5px solid #000;
    }
    li:hover{
            background-color: rgba(0, 0, 0, .1);
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
  .modal-overlay{
        position: absolute;
        top:0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 200;
        background-color: rgba(0, 0, 0, .2);
        #zurückBtn{
            background-color:#f80303;
        }
  }
  .fade-enter-active, .fade-leave-active{
      transition: opacity 0.5;
  }
  .fade-enter, .fade-leave-to{
      opacity: 0;
  }
</style>