<template>
  <div class="ideeseite">
     <Idee msg=""/>
  </div>
</template>

<script>
import Idee from '@/components/Idee.vue'
export default {
  components: {
    Idee
  }
}
</script>

<style lang="scss" scoped>
.ideeseite{
  //position: fixed;
  height: 70%; //Höhe des gesamten grauen Dings
  margin: auto;
}
</style>