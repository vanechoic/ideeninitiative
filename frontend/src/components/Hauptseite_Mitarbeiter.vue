<template>
  <div class="container">
    <div class="links">
      <p>Alle Ideen</p>
      <div class="listeContainer">
        <ul class="liste">
          <li v-for="idee in Ideen" :key="idee.Idee" v-on:click="showModal = true">{{idee.Idee}}</li>
        </ul>
      </div>
      <!--3 Filter Dropdowns -->
      <div class="filter">
        <div class="filterElement">
          <select id="filter1">
            <option value disabled selected>Filter 1</option>
            <option value="Sparte 1">Sparte 1</option>
            <option value="Sparte 2">Sparte 2</option>
            <option value="Sparte 3">Sparte 3</option>
            <option value="Sparte 3">Sparte 4</option>
          </select>
          <select id="filter2">
            <option value disabled selected>Filter 2</option>
            <option value="Sparte 1">Sparte 1</option>
            <option value="Sparte 2">Sparte 2</option>
          </select>
          <select id="filter3">
            <option value disabled selected>Filter 3</option>
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
        <div class="kopfzeile">
          <label id="ideeName">
            Idee Name
            <!--{{}}-->
          </label>
          <div class="erstellInfos">
            <label class="erstellerLbl" for="ersteller">Ersteller:</label>
            <div id="ersteller">
              <!--{{}}-->
              gggg
            </div>
            <label id="erstellDatumLbl">Erstellt am:</label>
            <div id="erstellDatum">
              <!--{{}}-->
              20.12.20
            </div>
          </div>
        </div>
        <div class="hauptteil">
          <label id="beschreibungLbl" for="beschreibung">Beschreibung:</label>
          <div id="beschreibung">
            <!--{{}}-->
            adadaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
          </div>
        </div>
        <div class="fußzeile">
          <button class="zurueckBtn" v-on:click="showModal =false">Zurück</button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import axios from 'axios'
import Mitarbeiter from './Mitarbeiter.vue'
import { properties } from '@syncfusion/ej2-vue-dropdowns/src/drop-down-list/dropdownlist.component'
import { Params } from "../services/params-service";

  export default {
    name:'Hauptseite_Mitarbeiter',
    components: {
        'mitarbeiter': Mitarbeiter
    },
    data: ()=> {
      return {
        showModal: false,
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
    },
    mounted(){
        Params.getInstance().tokenObserver.subscribe(res =>  {
                        console.log('hauptseite', res);
        });
    }
  }
</script>

<style lang="scss" scoped>  
  .rechts,.hauptteil{
    position: relative;
  }
  .container, .links, .modal-overlay,.fußzeile{
    position: absolute;
  }
  .hauptteil, .listeContainer, .filter, .fußzeile, #beschreibung{
    width: 100%
  }
  .rechts, .links, .erstellInfos, #beschreibung{
    height: 100%;
  }
  .rechts, .links, .erstellInfos, .filter, #ideeName{
    display: flex;
    justify-content: space-around;
  }
  p, .container, .links, .rechts, .modal-overlay{
    top:0;
  }
  .container, .links, .rechts{
    width: 50%;
  }
  li, #beschreibung, #ersteller, #erstellDatum{
    color: black;
  }
  li, .listeContainer, #beschreibung{
    border: 0.5px solid #000;
  }
  .links, .rechts, .modal-overlay{
    padding: 2%;
  }
  .container, .modal-overlay{
    background: linear-gradient(to bottom, #efefef, #ccc);
  }
  .links, .rechts, #ideeName{
    align-items: center;
  }
  button, .container, #beschreibung{
    border-radius: 20px;
  }
  button, .rechts{
    background-color: #00894d;
  }
  .links, #ideeName{
    flex-direction: column;
  }
  .rechts, #ideeName{
    text-align: center;
  }
  ul, .listeContainer{
    height: 300px;
  }
  #filter1, #filter2{
    margin-right: 2px;
  }
  .fade-enter-active, .fade-leave-active{
    transition: opacity 0.5;
  }
  .fade-enter, .fade-leave-to{
    opacity: 0;
  }
  button{
    border: 1px solid #fff;
    color: #fff;
    font-size: 0.75rem;
    font-weight: bold;
    padding: 2px 5px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    transition: transform 0.1s ease-in;
    &:active{
      transform: scale(0.9);
    }
    &:focus{
      outline: none;
    }
  }
  #beschreibung{
    display: block;
    background-color: #fff;
    word-wrap: break-word;
    padding: 0.2% 1%;
    overflow: scroll;
  }
  .container{
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2), 0 10px 10px rgba(0, 0, 0, 0.2);
  }
  .rechts{
    margin-left: 52.5%;
  }
  .hauptteil{
    height: 65%;
  }
  .zurueckBtn{
    background-color: #f80303;
    right: 5%;
  }
  .fußzeile{
    bottom: 5%;
  }
  #ideeName{
    font-size: 1.5rem;
  }
  .modal-overlay{
    left: 0;
    right: 0;
    bottom: 0;
  }
  p{
    margin: 8px 0 8px;
  }
  ul{
    list-style: none;
    overflow: auto;    text-indent: 10px;
    padding: 0;
  }
  li{
    line-height: 150%;
  }
  li:hover{
    background-color: rgba(0, 0, 0, 0.1);
  }
</style>