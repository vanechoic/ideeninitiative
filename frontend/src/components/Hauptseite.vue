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
                <div class="kopfzeile">
                    <label id="ideeName">Idee Name
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
import Registrierter from '@/components/Registrierter.vue'
  export default {
    name:'Startseite',
    components: {
        'mitarbeiter': Mitarbeiter,
        'registrierter': Registrierter
    },
    props:['dToken'],
    data: ()=> {
      return {
        showModal:false,
        component:'mitarbeiter',
        props:{
            tokenProp:{
                required:true
            }
        },
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
    created(){
        
        //onsole.log(tokenProp+'###########################')
    }
  }
</script>

<style lang="scss" scoped>
  .container {
    position: absolute;
    width: 100%;
    height: calc(100% - 200px);
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 15px 30px rgba(0, 0, 0, .2),
                0 10px 10px rgba(0, 0, 0, .2);
    background: linear-gradient(to bottom, #efefef, #ccc);
    left: 10%;
    top:10%;
    right:10%;
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
  .rechts{
      position: absolute;
      top: 0;
      display: flex;
      align-items: center;
      justify-content: space-around;
      flex-direction: column;
      padding: 2%;
      width: 50%;
      height:100%;
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
        padding: 3%;
        background-color:#ccc;
        .zurueckBtn{
            background-color:#f80303;
        }
  }
  .fade-enter-active, .fade-leave-active{
      transition: opacity 0.5;
  }
  .fade-enter, .fade-leave-to{
      opacity: 0;
  }
  .kopfzeile{
      
      #ideeName{
      font-size: 1.5rem;
      display: flex;
      align-items: center;
      justify-content: space-around;
      flex-direction: column;
      text-align: center;
      }
      .erstellInfos{
          justify-content: space-around;
          #ersteller{
              color: black;
          }
          #erstellDatum{
              color: black;
          }
      }
  }
  .hauptteil{
      position:absolute;
      width: 100%;
      height: 70%;
      align-items: center;
      justify-content: space-around;
      #beschreibung{
          display: block;
          background-color: #fff;
          word-wrap: break-word;
          margin: 0 2%;
          padding: .2% 1%;
          width:90%;
          height:85%;
          border:1px solid  rgba(0, 0, 0, .3);
          overflow: scroll;
          color: black;
          border-radius: 20px;
      }
  }
  .fußzeile{
      position: absolute;
      bottom: 0;
      top:95%;
      left:90%;
  }
</style>