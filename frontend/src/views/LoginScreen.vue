<template>
  <div class="loginScreen">
    <Login msg=""/>
  </div>
</template>

<script>
import Login from '@/components/Login.vue'
export default {
  name: 'LoginScreen',
  components: {
    Login
  }
}
</script>

<style lang="scss">
.loginScreen{
  height: 70%;
  margin:auto;
}
</style>
