import Vue from 'vue'
import App from './App'
import router from './router'
import VueResource from 'vue-resource'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import store from './store'
import * as axios from "axios"

var VueCookie = require('vue-cookie');
Vue.use(VueCookie);

axios.defaults.withCredentials = true

// Setting up Axios on Vue Instance, for use via this.$axios
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(VueResource)
Vue.use(Vuetify, {
  theme: {
    primary: 'teal'
  }
})

axios.interceptors.response.use(response => {
      return Promise.resolve(response)
    },
    error => {
      if (error.response.status === 401) {
        console.log('Unauthorized, logging out ...');
        store.dispatch('userSignOut')
        router.replace('signIn')
        return Promise.reject(error)
      } else {
        return Promise.reject(error.response);
      }
    })

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
