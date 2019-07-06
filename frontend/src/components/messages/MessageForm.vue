<template>
    <v-layout>
        <v-text-field
                label="New message"
                placeholder="Write something"
                v-model="text"/>
        <v-btn @click="save">
            Save
        </v-btn>
    </v-layout>
</template>

<script>
    import {mapActions, mapState} from 'vuex'

    export default {
        computed: mapState(['profile']),
        props: ['messageAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr: function (newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            ...mapActions(['addMessageAction', 'updateMessageAction']),
            save() {
                const message = {
                    id: this.id,
                    text: this.text,
                    userId: this.profile.id
                }

                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>

</style>