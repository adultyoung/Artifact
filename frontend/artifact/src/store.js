import Vue from 'vue'
import Vuex from 'vuex'
import axios from "axios";
import {EventBus} from "../event-bus";
import postsApi from './api/posts'
import commentApi from './api/comment'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    profile,
    ...frontendData,
    posts,
  },
  mutations: {
    setUser (state, payload) {
      state.user = payload
    },
    setAuth (state, payload) {
      localStorage.setItem("auth", payload)
    },
    clearAuth(state) {
      localStorage.removeItem("auth")
    },
    setError (state, payload) {
      state.error = payload
    },
    setLoading (state, payload) {
      state.loading = payload
    },
    addPostMutation(state, post) {
      state.posts = [
        ...state.posts,
        post
      ]
    },
    updatePostMutation(state, post) {
      const updateIndex = state.posts.findIndex(item => item.id === post.id)

      state.posts = [
        ...state.posts.slice(0, updateIndex),
        post,
        ...state.posts.slice(updateIndex + 1)
      ]
    },
    removePostMutation(state, post) {
      const deletionIndex = state.posts.findIndex(item => item.id === post.id)

      if (deletionIndex > -1) {
        state.posts = [
          ...state.posts.slice(0, deletionIndex),
          ...state.posts.slice(deletionIndex + 1)
        ]
      }
    },
    addCommentMutation(state, comment) {
      const updateIndex = state.posts.findIndex(item => item.id === comment.post.id)
      const post = state.posts[updateIndex]

      if (!post.comments.find(it => it.id === comment.id)) {
        state.posts = [
          ...state.posts.slice(0, updateIndex),
          {
            ...post,
            comments: [
              ...post.comments,
              comment
            ]
          },
          ...state.posts.slice(updateIndex + 1)
        ]
      }
    },
    addPostPageMutation(state, posts) {
      const targetPosts = state.posts
          .concat(posts)
          .reduce((res, val) => {
            res[val.id] = val
            return res
          }, {})

      state.posts = Object.values(targetPosts)
    },
    updateTotalPagesMutation(state, totalPages) {
      state.totalPages = totalPages
    },
    updateCurrentPageMutation(state, currentPage) {
      state.currentPage = currentPage
    }
  },
  actions: {
    onExpired () {
      console.log('Expired')
    },
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
            commit('setError', error.post)
            commit('setLoading', false)
          })
    },
    userSignIn ({commit}, payload) {
      let data = {
        username: payload.username,
        password: payload.password,
      }
      commit('setLoading', true)
      return axios.post('/login', data)
    },
    registration ({commit}, payload) {
      let data = {
        username: payload.username,
        password: payload.password,
        email: payload.email,
      }
      axios.post('/registration', data).then(res => console.log(res))
    },
    logout () {
      axios.post('/logout')
    },
    async addPostAction({commit, state}, post) {
      const result = await postsApi.add(post)
      const data = await result.json()
      const index = state.posts.findIndex(item => item.id === data.id)

      if (index > -1) {
        commit('updatePostMutation', data)
      } else {
        commit('addPostMutation', data)
      }
    },
    async updatePostAction({commit}, post) {
      const result = await postsApi.update(post)
      const data = await result.json()
      commit('updatePostMutation', data)
    },
    async removePostAction({commit}, post) {
      const result = await postsApi.remove(post.id)

      if (result.ok) {
        commit('removePostMutation', post)
      }
    },
    async addCommentAction({commit, state}, comment) {
      const response = await commentApi.add(comment)
      const data = await response.json()
      commit('addCommentMutation', data)
    },
    async loadPageAction({commit, state}) {
      const response = await postsApi.page(state.currentPage + 1)
      const data = await response.json()

      commit('addPostPageMutation', data.posts)
      commit('updateTotalPagesMutation', data.totalPages)
      commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1))
    }
  },
  getters: {
    sortedPosts: state => (state.posts || []).sort((a, b) => -(a.id - b.id))
  }
})
