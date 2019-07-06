<template>
    <v-container>
        <v-layout align-center justify-center>
            <message-form :messageAttr="message"></message-form>
        </v-layout>
        <v-layout align-center justify-center>
            <message-row v-for="message in sortedMessages"
                         :key="message.id"
                         :message="message"
                         :editMessage="editMessage">
            </message-row>
        </v-layout>
    </v-container>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import {mapGetters} from 'vuex'

    export default {
        components: {
            MessageRow,
            MessageForm
        },
        computed: mapGetters(['sortedMessages']),
        data() {
            return {
                message: null
            }
        },
        methods: {
            editMessage(message) {
                this.message = message
            }
        }
    }
</script>

<style>

</style>