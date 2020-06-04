<template>
  <div class="Ideebewerten">
    <IdeeBewertenSpeicher msg=""/>
  </div>
</template>

<script>
import IdeeBewertenSpeicher from '@/components/IdeeBewertenSpeicher.vue'

export default {
  name: 'GespeicherteIdeeBewerten',
  components: {
    IdeeBewertenSpeicher
  }
}
</script>
<style lang="scss" scoped>
.Ideebewerten {
  margin: auto;
  width: 900px;
  height: 90%;
}
</style>
