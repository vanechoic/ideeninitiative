<template>
  <div class="ideenSpeicher">
    <IdeenSpeicher msg=""/>
  </div>
</template>

<script>
import IdeenSpeicher from '@/components/IdeenSpeicher.vue'

export default {
  name: 'Ideenspeicher',
  components: {
    IdeenSpeicher
  }
}
</script>
<style lang="scss" scoped>
.ideenSpeicher{
  //position: fixed;
  height: 70%; //Höhe des gesamten grauen Dings
  margin: auto;
}
</style>
