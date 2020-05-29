  
<template>
  <div class="startseite">
     <component v-bind:is="component"></component>
  </div>
</template>

<script>
import Hauptseite from '@/components/Hauptseite.vue'
export default {
  name: 'Startseite',
  components: {
    "hs": Hauptseite
  },
  data(){
    return{
      component:'hs',
    }
  }
}
</script>

<style lang="scss">
.startseite{
  position: fixed;
}
</style>