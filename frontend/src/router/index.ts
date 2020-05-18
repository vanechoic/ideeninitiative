import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import LoginScreen from '../views/LoginScreen.vue'
import Startseite from '../views/Startseite.vue'
import SystemnachrichtAnzeigen from '../views/SystemnachrichtAnzeigen.vue'
import Mitarbeiter from '../views/Mitarbeiter.vue'
import MeineIdeen from '../views/MeineIdeen.vue'
import IdeeBearbeiten from '../views/IdeeBearbeiten.vue'
import NeueIdeeAnlegen from '../views/NeueIdeeAnlegen.vue'

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
]

const router = new VueRouter({
  routes
})

export default router
