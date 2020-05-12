<template>
  <div class="startseite">
     <IdeeBearbeiten msg=""/>
  </div>
</template>

<script>
//import Hauptseite from '@/components/Hauptseite.vue'
//import MeineIdeen from '@/components/MeineIdeen.vue'
//import NeueIdee from '@/components/NeueIdee.vue'
import IdeeBearbeiten from '@/components/IdeeBearbeiten.vue'

export default {
  name: 'Startseite',
  components: {
    IdeeBearbeiten
  }
}
</script>