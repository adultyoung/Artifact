<template>
    <v-card
            :flat= true
            :outlined= true
            :width=560
            max-height="1200px"
            class="ma-2">
        <v-card-text primary-title>
            <user-link
                :user="post.author"
                size="48"
            ></user-link>
            <div class="pt-3">
                {{ post.text }}
            </div>
        </v-card-text>
        <media v-if="post.link" :post="post"></media>
        <v-card-actions>
            <v-btn value="Edit" @click="edit" small flat round>Edit</v-btn>
            <v-btn icon @click="del" small>
                <v-icon>delete</v-icon>
            </v-btn>
        </v-card-actions>
        <comment-list
                :comments="post.comments"
                :post-id="post.id"
        ></comment-list>
    </v-card>
</template>

<script>
    import {mapActions} from 'vuex'
    import Media from '../../components/media/Media.vue'
    import CommentList from '../comment/CommentList.vue'
    import UserLink from '../../components/UserLink.vue'

    export default {
        props: ['post', 'editPost'],
        components: {UserLink, CommentList, Media },
        methods: {
            ...mapActions(['removePostAction']),
            edit() {
                this.editPost(this.post)
            },
            del() {
                this.removePostAction(this.post)
            }
        }
    }
</script>

<style>

</style>
