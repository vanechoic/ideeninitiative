<template>
  <div class="idee-bearbeiten">
    <IdeeBearbeiten msg=""/>
  </div>
</template>

<script>
import IdeeBearbeiten from '@/components/IdeeBearbeiten.vue'

export default {
  name: 'Ideebearbeiten',
  components: {
    IdeeBearbeiten
  }
}
</script>
<style lang="scss" scoped>
.idee-bearbeiten{
  height: 90%; //größer, weil mehr Inhalt
  margin: auto;
}
</style>
