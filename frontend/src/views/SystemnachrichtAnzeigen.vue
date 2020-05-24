<template>
  <div class="systemnachricht">
     <Systemnachricht msg=""/>
  </div>
</template>

<script>
import Systemnachricht from '@/components/Systemnachricht.vue'

export default {
  name: 'SystemnachrichtAnzeigen',
  components: {
    Systemnachricht
  }
}
</script>
<style lang="scss" scoped>
  .systemnachricht{
    width: 100%;
    height: 100%;
  }
</style>