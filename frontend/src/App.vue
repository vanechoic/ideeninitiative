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
    font-size: 1.2rem;
    color: #00894d;
    background-color: #104227;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
.selector-for-some-widget {
  box-sizing: content-box;
}
</style>
