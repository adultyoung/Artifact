<template>
    <v-layout mx-5 row>
        <v-flex xs12>
            <v-text-field
                    @keyup.enter="save"
                    label="New post"
                    placeholder="Write something"
                    v-model="text"
            />
        </v-flex>
        <v-flex>
            <v-btn @click="save">
                Save
            </v-btn>
        </v-flex>
    </v-layout>
</template>

<script>
    import {mapActions} from 'vuex'

    export default {
        props: ['postAttr'],
        data() {
            return {
                text: '',
                id: null
            }
        },
        watch: {
            postAttr(newVal, oldVal) {
                this.text = newVal.text;
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addPostAction', 'updatePostAction']),
            save() {
                const post = {
                    id: this.id,
                    text: this.text
                };

                if (this.id) {
                    this.updatePostAction(post)
                } else {
                    this.addPostAction(post)
                }

                this.text = '';
                this.id = null
            }
        }
    }
</script>

<style>

</style>
