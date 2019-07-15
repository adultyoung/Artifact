import router from '@/router'
import axios from 'axios'
import { EventBus } from '../event-bus.js'




export const actions = {
    oauth2Google ({commit}) {
        commit('setLoading', true)
        axios.get('http://localhost:8091/auth/oauth')
            .then(res => {
                commit ('setAuth', true)
                commit ('setLoading', false)
                commit ('setError', null)
                EventBus.$emit('authenticated', 'User authenticated')
                router.push('/home')
            })
            .catch(error => {
                commit('setError', error.message)
                commit('setLoading', false)
            })
    },
  userSignIn ({commit}, payload) {
    let data = {
      username: payload.username,
      password: payload.password,
    }
    let token = payload.token;
    commit('setLoading', true)
    axios.post('http://localhost:8091/login', data)
     .then(res => {
       commit ('setAuth', true)
       commit ('setLoading', false)
       commit ('setError', null)
       EventBus.$emit('authenticated', 'User authenticated')
       router.push('/home')
     })
    .catch(error => {
      commit('setError', error.message)
      commit('setLoading', false)
    })
  },
  userSignOut ({commit}) {
    commit ('clearAuth')
    EventBus.$emit('authenticated', 'User not authenticated')
    router.push('/signIn')
  }


}

