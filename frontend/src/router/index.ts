import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import LoginScreen from '../views/LoginScreen.vue'
import Startseite from '../views/Startseite.vue'
import SystemnachrichtAnzeigen from '../views/SystemnachrichtAnzeigen.vue'
import Mitarbeiter from '../views/Mitarbeiter.vue'
import MeineIdeen from '../views/MeineIdeen.vue'
import IdeeBearbeiten from '../views/IdeeBearbeiten.vue'
import NeueIdeeAnlegen from '../views/NeueIdeeAnlegen.vue'
import Idee from '../views/Idee.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'LoginScreen',
    component: LoginScreen
  },
  {
    path: '/Startseite',
    name: 'Startseite',
    component: Startseite
  },
  {
    path: '/Home',
    name: 'Mitarbeiter',
    component: Mitarbeiter
  },
  {
    path: '/Ideeanlegen',
    name: 'NeueIdeeAnlegen',
    component: NeueIdeeAnlegen
  },
  {
    path: '/Meineideen',
    name: 'MeineIdeen',
    component: MeineIdeen
  },
  {
    path: '/Ideebearbeiten',
    name: 'IdeeBearbeiten',
    component: IdeeBearbeiten
  },
  {
    path: '/Systemnachricht',
    name: 'SystemnachrichtAnzeigen',
    component: SystemnachrichtAnzeigen
  },
  {
    path: '/Idee',
    name: 'Idee',
    component: Idee
  },
]

const router = new VueRouter({
  routes
})

// Navigation Guard - Erlaubt nur den Zugriff
// auf geschützen Bereich, wenn ein Token gesetzt ist
router.beforeEach((to, from, next) => {

  var jwt = require("jsonwebtoken");
  var token = jwt.decode(localStorage.getItem("token"));

  if ( (to.name === 'Mitarbeiter' || to.name === 'SystemnachrichtAnzeigen') )
    next();
  else if ( (to.name !== 'LoginScreen' && !token) )
    next({ name: 'LoginScreen' });
  else
    next();
})

export default router;
