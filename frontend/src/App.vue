<template>
  <div id="app">
    <div id="nav"></div>
    <router-view />
  </div>
</template>

<style lang="scss">
$medium-green: #00894d;
$light-green: #69a82f;
body {
  margin: 0;
  padding: 0;
}
h1, h2, h3, h4, h5, h6{
  width: 100%;
  text-align: center;
}
#app {
  position: absolute;
  font-family: Tahoma, sans-serif;
  font-size: 1.2rem;
  color: #00894d;
  background-color: #104227;
  width: 100%;
  height: 100%;
  padding: 1%;
  display: flex;
  justify-content: center;
}
.selector-for-some-widget {
  box-sizing: content-box;
}
.example::-webkit-scrollbar {
  display: none;
}
input, textarea{
  padding: 5px 15px;
  border: none;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.4), 0 -1px 1px #fff, 0 1px 0 #fff;
  border-radius: 20px;
}
select{
  font-size: 14px !important;
  padding: 5px;
  border: none;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.4), 0 -1px 1px #fff, 0 1px 0 #fff;
  border-radius: 20px;
}
ul {
  height: 100%;
  border-radius: 20px;
  list-style: none;
  margin: 0;
  padding: 0;
  text-indent: 10px;
}
li {
  border: none;
  line-height: 30px;
  color: $medium-green;
  &:hover {
    background-color: rgba(0, 0, 0, 0.1);
  }
}
</style>
