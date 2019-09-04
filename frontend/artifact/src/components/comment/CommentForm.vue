<template>
    <v-layout class="px-3" row>
        <v-text-field
                @keyup.enter="save"
                label="Add comment"
                placeholder="Write something"
                v-model="text"
        />
        <v-btn @click="save">
            Add
        </v-btn>
    </v-layout>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        name: 'CommentForm',
        props: ['postId'],
        data() {
            return {
                text: ''
            }
        },
        methods: {
            ...mapActions(['addCommentAction']),
            async save() {
                await this.addCommentAction({
                    text: this.text,
                    post: {
                        id: this.postId
                    }
                });

                this.text = ''
            }
        }
    }
</script>

<style scoped>

</style>
