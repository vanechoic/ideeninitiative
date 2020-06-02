<template>
  <div class="meineideen">
    <MeineIdeen msg=""/>
  </div>
</template>

<script>
import MeineIdeen from '@/components/MeineIdeen.vue'

export default {
  name: 'Meineideen',
  components: {
    MeineIdeen
  }
}
</script>

<style lang="scss" scoped>
.container, .links, .rechts{
  position: relative;
}
p, .rechts, .links{
  top: 0;
}
.links, .rechts{
  display: flex;
  align-items: center;
  justify-content: space-around;
  width: 50%;
}
 button {
    border-radius: 20px;
    border: 1px solid #fff;
    color:#fff;
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
.meineideen{
  //position: fixed;
  height: 70%; //Höhe des gesamten grauen Dings
  margin: auto;
}
</style>
