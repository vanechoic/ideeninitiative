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
  margin: auto;
  width: 900px;
  height: 90%;
}
</style>