<template>
  <div class="startseite">
     <component v-bind:is="component"></component>
  </div>
</template>

<script>
import Hauptseite from '@/components/Hauptseite.vue'
import MeineIdeen from '@/components/MeineIdeen.vue'
import NeueIdee from '@/components/NeueIdee.vue'
import IdeeBearbeiten from '@/components/IdeeBearbeiten.vue'

export default {
  name: 'Startseite',
  components: {
    "hs": Hauptseite,
    "mi": MeineIdeen,
    "ni": NeueIdee,
    "ib": IdeeBearbeiten 
  },
  data(){
    return{
      component:'hs'
    }
  }
}
</script>