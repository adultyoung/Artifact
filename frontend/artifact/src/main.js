import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import store from './store'
import * as axios from "axios"
import {connect} from './util/ws'

connect();

axios.defaults.withCredentials = true;

// Setting up Axios on Vue Instance, for use via this.$axios
Vue.prototype.$axios = axios;
Vue.config.productionTip = false;
Vue.use(Vuetify);

axios.interceptors.response.use(response => {
        return Promise.resolve(response)
    },
    error => {
        if (error.response.status === 401) {
            console.log('Unauthorized, logging out ...');
            store.dispatch('userSignOut');
            router.replace('signIn');
            return Promise.reject(error)
        } else {
            return Promise.reject(error.response);
        }
    });


export default new Vue({
    el: '#app',
    router,
    store,
    render: a => a(App)
})
