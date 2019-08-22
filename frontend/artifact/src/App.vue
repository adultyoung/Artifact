<template>
    <v-app>
            <v-toolbar app flat style="background: #FFFFFF">
            <v-spacer></v-spacer>
            <v-toolbar-title
                    @click="showPosts">Artifact
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn v-if="profile"
                   :disabled="$route.path === '/channels'"
                   icon @click="showChannels">
                <v-icon>search</v-icon>
            </v-btn>
            <v-avatar
                    :disabled="$route.path === '/user'"
                    @click="showProfile"
                    v-if="profile && profile.picture"
                    :size="36"
            >
                <v-btn flat icon
                       :disabled="$route.path === '/user'"
                       @click="showProfile">
                    <img
                            :src="profile.picture"
                            :alt="profile.username"
                    >
                </v-btn>
            </v-avatar>

            <v-avatar
                    v-else
                    :size="36"
            >
                <v-btn flat icon
                       v-if="profile"
                       :disabled="$route.path === '/user'"
                       @click="showProfile">
                    <v-icon>person</v-icon>
                </v-btn>
            </v-avatar>
            <v-btn v-if="profile" icon @click="logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import {addHandler} from './util/ws'

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addPostMutation',
                'updatePostMutation',
                'removePostMutation',
                'addCommentMutation'
            ]),
            logout() {
                this.$store.dispatch('logout')
            },
            showChannels() {
                this.$router.push('/channels')
            },
            showPosts() {
                this.$router.push('/')
            },
            showProfile() {
                this.$router.push('/user')
            }
        },
        created() {
            addHandler(data => {
                if (data.objectType === 'POST') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addPostMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updatePostMutation(data.body)
                            break
                        case 'REMOVE':
                            this.removePostMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body)
                            break
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Looks like the object type if unknown "${data.objectType}"`)
                }
            })
        },
        beforeMount() {
            if (!this.profile) {
                this.$router.replace('/auth')
            }
        }
    }
</script>

<style>

</style>
