<template>
  <div id="app">
    <Login />
  </div>
</template>

<script>
import Login from './components/Login.vue'
export default {
  name: 'app',
  components: {
    Login
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
</style>