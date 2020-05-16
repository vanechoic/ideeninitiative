import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import LoginScreen from '../views/LoginScreen.vue'
import Startseite from '../views/Startseite.vue'
import SystemnachrichtAnzeigen from '../views/SystemnachrichtAnzeigen.vue'
import RegMitarbeiter from '../views/RegMitarbeiter.vue'

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
    path: '/Systemnachricht',
    name: 'SystemnachrichtAnzeigen',
    component: SystemnachrichtAnzeigen
  },
  {
    path: '/Home',
    name: 'RegMitarbeiter',
    component: RegMitarbeiter
  }
]

const router = new VueRouter({
  routes
})

export default router
