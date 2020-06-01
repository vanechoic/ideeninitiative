import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import AsyncComputed from 'vue-async-computed'
import VueSimpleAlert from "vue-simple-alert"

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap';

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(AsyncComputed)
Vue.use(VueSimpleAlert);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
