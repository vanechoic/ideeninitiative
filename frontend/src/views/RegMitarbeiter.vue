<template>
  <div class="reg-mitarbeiter">
    <Hauptseite/>
    <Registrierter/>
  </div>
</template>

<script>
import Registrierter from "@/components/Registrierter.vue";
import Hauptseite from "@/components/Hauptseite.vue";

export default {
  name: "RegMitarbeiter",
  components: {
    Hauptseite,
    Registrierter
  },
};
</script>
