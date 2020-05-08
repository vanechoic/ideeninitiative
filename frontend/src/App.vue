<template>
  <div id="app">
    <div id="nav">
    </div>
    <router-view/>
  </div>
</template>

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
  #mi{
    margin: 20% 10%;
  }
</style>
