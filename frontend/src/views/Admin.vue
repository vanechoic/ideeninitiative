<template>
  <div class="admin">
    <Admin msg=""/>
  </div>
</template>

<script>
import Admin from '@/components/Admin.vue'

export default {
  name: 'admin',
  components: {
    Admin
  }
}
</script>

<style lang="scss">
  .admin{
    height: 70%;
    margin: auto;
  }
</style>