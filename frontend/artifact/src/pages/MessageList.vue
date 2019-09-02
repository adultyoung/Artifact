<template>
    <v-container fluid>
        <post-form :postAttr="post"/>
        <v-layout column align-center>
            <post-row v-for="post in sortedPosts"
                      :key="post.id"
                      :post="post"
                      :editPost="editPost"/>
            <lazy-loader></lazy-loader>
        </v-layout>
    </v-container>
</template>

<script>
    import {mapGetters} from 'vuex'
    import PostRow from '../components/posts/PostRow.vue'
    import PostForm from '../components/posts/PostForm.vue'
    import LazyLoader from '../components/LazyLoader.vue'

    export default {
        components: {
            LazyLoader,
            PostRow,
            PostForm
        },
        data() {
            return {
                post: null
            }
        },
        computed: mapGetters(['sortedPosts']),
        methods: {
            editPost(post) {
                this.post = post
            }
        }
    }
</script>

<style>

</style>
