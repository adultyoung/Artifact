<template>
    <v-container>
        <v-layout align-center column justify-space-around>
            <v-list>
                <v-list-tile
                        :key="channel.id"
                        v-for="channel in channels"
                >
                    <user-link
                            :user="channel"
                            size="36"
                    ></user-link>
                </v-list-tile>
            </v-list>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from '../api/profile'
    import UserLink from '../components/UserLink.vue'

    export default {
        name: "Channels",
        components: {UserLink},
        data() {
            return {
                channels: []
            }
        },
        async beforeMount() {
            const resp = await profileApi.channelsList();
            this.channels = await resp.json();
        }
    }
</script>

<style scoped>

</style>