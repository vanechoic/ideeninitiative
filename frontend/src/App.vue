<template>
  <div id="app">
    <component v-bind:is="component"></component>
    <button id="sn" class="demo" v-on:click="component='sn'">SystemNachricht</button><!--Demo -->
    <button id="hs" class="demo" v-on:click="component='hs'">Hauptseite</button><!--Demo -->
  </div>
</template>

<script>
import Login from './components/Login.vue'
import SystemNachricht from './components/SystemNachricht.vue'
import Hauptseite from './components/Hauptseite.vue'
export default {
  name: 'app',
  components: {
    'login': Login,
    'sn': SystemNachricht,
    'hs': Hauptseite
  },
  data(){
    return{
      component: 'login'
    }
  }
}
</script>

<style lang="scss">
  body {
    margin: 0;
    padding: 0;
  }
  #app {
    font-family: Tahoma;
    font-size: 1.6rem;
    color: #00894d;
    background-color: #104227;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .demo{
    position: absolute;
    margin: 20% 0;
    z-index: 110;
  }
  #hs{
    margin: 22% 0;
  }
</style>