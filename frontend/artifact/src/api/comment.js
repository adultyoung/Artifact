import Vue from 'vue'
import VueResource from "vue-resource";

Vue.use(VueResource)


const comments = Vue.resource('/comment{/id}')

export default {
    add: comment => comments.save({}, comment),
}
