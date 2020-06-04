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

Vue.filter('status', function (value) {
  switch (value) {
    case 'IN_BEARBEITUNG':
      return 'Im Bewertungsprozess';
      break;
    case 'ANGELEGT':
      return 'Angelegt';
      break;
    case 'AKZEPTIERT':
      return 'Akzeptiert';
      break;
    case 'ABGELEHNT':
      return 'Abgelehnt';
      break;
    case 'GESPEICHERT':
      return 'Im Ideenspeicher';
      break;
    default:
      return value;
  }
})

Vue.filter('sparte', function (value) {
  switch (value) {
    case 'UNFALL':
      return 'Unfall';
      break;
    case 'KRANKENVERSICHERUNG':
      return 'Krankenversicherung';
      break;
    case 'RECHTSSCHUTZ':
      return 'Rechtsschutz';
      break;
    case 'LEBENSVERSICHERUNG':
      return 'Lebensversicherung';
      break;
    case 'RENTENVERSICHERUNG':
      return 'Rentenversicherung';
      break;
    case 'HAFTPFLICHT':
      return 'Haftpflicht';
      break;
    case 'HAUSRAT':
      return 'Hausrat';
      break;
    case 'WOHNGEBAUEDEVERSICHERUNG':
      return 'Wohngebäudeversicherung';
      break;
    default:
      return value;
  }
})

Vue.filter('vertriebsweg', function (value) {
  switch (value) {
    case 'STATIONAERER_VERTRIEB':
      return 'Stationärer Vertrieb in eigener Geschäftsstelle';
      break;
    case 'VERSICHERUNGSMAKLER':
      return 'Versicherungsmakler';
      break;
    case 'KOOPERATION_MIT_KREDITINSTITUTEN':
      return 'Kooperation mit Kreditinstituten';
      break;
    case 'DIREKTVERSICHERUNG':
      return 'Direktversicherung';
      break;
    default:
      return value;
  }
})

Vue.filter('ideetyp', function (value) {
  switch (value) {
    case 'PRODUKTIDEE':
      return 'Produktidee';
      break;
    case 'INTERNE_IDEE':
      return 'Interne Idee';
      break;
    default:
      return value;
  }
})

Vue.filter('handlungsfeld', function (value) {
  switch (value) {
    case 'KOSTENSENKUNG':
      return 'Kostensenkung';
      break;
    case 'ERTRAGSSTEIGERUNG':
      return 'Ertragssteigerung';
      break;
    case 'ZUKUNFTSFAEHIGKEIT':
      return 'Zukunftsfähigkeit';
      break;
    default:
      return value;
  }
})

Vue.filter('zielgruppe', function (value) {
  switch (value) {
    case 'KINDER_JUGENDLICHE':
      return 'Kinder/Jugendliche';
      break;
    case 'FAMILIEN':
      return 'Familien';
      break;
    case 'SINGLES':
      return 'Singles';
      break;
    case 'PAARE':
      return 'Paare';
      break;
    case 'PERSONEN_50PLUS':
      return 'Personen 50+';
      break;
    case 'GEWERBETREIBENDE':
      return 'Gewerbetreibende';
      break;
    default:
      return value;
  }
})


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
