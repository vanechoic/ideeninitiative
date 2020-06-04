<template>
  <div id="app">
    <div id="nav"></div>
    <router-view />
  </div>
</template>

<style lang="scss">
$medium-green: #00894d;
$rot: #f80303;
$light-green: #69a82f;
body {
  margin: 0;
  padding: 0;
}
h1,
h2,
h3,
h4,
h5,
h6 {
  width: 100%;
  text-align: center;
}
#app {
  position: absolute;
  font-family: Tahoma, sans-serif;
  font-size: 1.2rem;
  color: $medium-green;
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
input,
textarea {
  padding: 5px 15px;
  border: none;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.4), 0 -1px 1px #fff, 0 1px 0 #fff;
  border-radius: 20px;
}
select {
  font-size: 0.8rem !important;
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

button{
  color: #fff;
  border-radius: 20px !important;
  padding: 10px 40px;
  margin-top: 10px;
  border: none;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2), 0 5px 5px rgba(0, 0, 0, 0.2);
  font-size: 0.75rem !important;
  font-weight: bold;
  letter-spacing: 1px;
  text-transform: uppercase  !important;
  background: $light-green;
  cursor: pointer;
  transition: transform 0.1s ease-in;
    &:active {
    transform: scale(0.9);
  }
  &:focus {
    outline: none;
  }
  &:hover {
    background-color: #555;
  }
}
.hellgruener-button {
  @extend button;
  background: $light-green;

}
.roter-button {
  @extend button;
  background: $rot;
}
</style>