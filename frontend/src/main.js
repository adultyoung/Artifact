import Vue from 'vue'
import VeeValidate from 'vee-validate'
import Vuetify from "vuetify"
import '@babel/polyfill'
import './api/resource.js'
import router from './router/router.js'
import App from './App'

import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify)
Vue.use(VeeValidate);

new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
});