<template>
  <article>
    <div class="container">
        <img src="https://www.w3schools.com/images/picture.jpg" alt="Profilbild">
        <button id="profilbildHochladen">Profilbild hochladen</button>
        <button id="ideen">Zugewiesende Ideen</button>
        <button id="ideenspeicher">Zum Ideenspeicher</button>
    </div>
  </article>
</template>

<script>
export default {
  name: 'Admin'
}
</script>

<style lang="scss" scoped>
 button {
    border-radius: 20px;
    border: 1px solid #fff;
    color: #fff;
    font-size: .75rem;
    font-weight: bold;
    padding: 10px 40px;
    letter-spacing: 1px;
    text-transform: uppercase;
    cursor: pointer;
    margin-top:10px;
    transition: transform .1s ease-in;
    &:active {
      transform: scale(.9);
    }
    &:focus {
      outline: none;
    }
  }
  #zurSystemnachricht, #profilbildHochladen{
    background-color:#0a0404;
  }
  #zurückZurAnmeldung{
    background-color:#f80303;
  }
  #ideen{
    margin-right: 2px;
  }
  #ideen, #ideenspeicher{
    color: #000;
  } 
</style>