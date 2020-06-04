<template>
  <div class="neue-idee-anlegen">
     <NeueIdee msg=""/>
  </div>
</template>

<script>
import NeueIdee from '@/components/NeueIdee.vue'

export default {
  name: 'NeueIdeeAnlegen',
  components: {
    NeueIdee
  }
}
</script>
<style lang="scss" scoped>
.neue-idee-anlegen{
  height: 95%; //größer, weil mehr Inhalt
  margin: auto;
}
</style>