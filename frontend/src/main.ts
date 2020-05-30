import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import AsyncComputed from 'vue-async-computed'

import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap';
import { Params } from './services/params-service'

Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(AsyncComputed)



new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
