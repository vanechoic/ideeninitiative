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
  //height: 80em;
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
//Scrollbar
::-webkit-scrollbar {
  width: 8px;
}
/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}
/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}
/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}
#anzeige-aktuelle-seite {
  text-align: center;
  color: grey;
  line-height: 2;
  font-size: 0.9rem;
  padding-top: 1rem;
}
.grauer-text {
  color: grey;
  line-height: 1.4;
  font-size: 0.9rem;
}
</style>
