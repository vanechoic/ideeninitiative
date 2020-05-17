<template>
  <div class="startseite">
     <child-comp :some-token-prop="tokenProp"></child-comp>
  </div>
</template>

<script>
import Hauptseite from '@/components/Hauptseite.vue'
import MeineIdeen from '@/components/MeineIdeen.vue'
import NeueIdee from '@/components/NeueIdee.vue'
import IdeeBearbeiten from '@/components/IdeeBearbeiten.vue'

export default {
  name: 'Startseite',
  props:['Token'],
  components: {
   // "hs": Hauptseite,
    //"mi": MeineIdeen,
    //"ni": NeueIdee,
    //"ib": IdeeBearbeiten 
  },
  data(){
    return{
      component:'hs',
      tokenProp: this.fetchData()
    }
  },
  methods:{
     fetchData () {
       var dToken= this.$route.params.dtoken
        console.log(dToken)
        return dToken;
    }
  }
}
</script>