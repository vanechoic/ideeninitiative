<template>
  <div id="app">
    <component v-bind:is="component"></component>
    <button class="demo" v-on:click="component='sn'">SystemNachricht</button>
  </div>
</template>

<script>
import Login from './components/Login.vue'
import SystemNachricht from './components/SystemNachricht.vue'
export default {
  name: 'app',
  components: {
    'login': Login,
    'sn': SystemNachricht
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
</style>