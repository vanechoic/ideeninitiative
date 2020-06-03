<template>
  <div class="systemnachricht">
    <Systemnachricht msg="" />
  </div>
</template>

<script>
import Systemnachricht from "@/components/Systemnachricht.vue";

export default {
  name: "SystemnachrichtAnzeigen",
  components: {
    Systemnachricht,
  },
};
</script>

<style lang="scss">
  .systemnachricht{
    height: 80%;
    margin: auto;
  }
</style>
