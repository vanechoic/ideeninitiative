<template>
  <div id="app">
    <div id="nav"></div>
    <router-view />
  </div>
</template>

<style lang="scss">
body {
  margin: 0;
  padding: 0;
}
#app {
  position: absolute;
  font-family: Tahoma;
  font-size: 1.2rem;
  color: #00894d;
  background-color: #104227;
  width: 100%;
  height: 80em;
  padding: 1%;
  display: flex;
  justify-content: center;
  //align-items: center;
}
.selector-for-some-widget {
  box-sizing: content-box;
}
.example::-webkit-scrollbar {
  display: none;
}
</style>
