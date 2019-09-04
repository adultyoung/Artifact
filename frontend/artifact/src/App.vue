<template>
    <v-app>
        <v-toolbar app flat style="background: #FFFFFF">
            <v-spacer></v-spacer>
            <v-toolbar-title
                    @click="showPosts">Artifact
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn :disabled="$route.path === '/channels'"
                   @click="showChannels"
                   icon v-if="profile">
                <v-icon>search</v-icon>
            </v-btn>
            <v-avatar
                    :disabled="$route.path === '/user'"
                    :size="36"
                    @click="showProfile"
                    v-if="profile && profile.picture"
            >
                <v-btn :disabled="$route.path === '/user'" @click="showProfile"
                       flat
                       icon>
                    <img
                            :alt="profile.username"
                            :src="profile.picture"
                    >
                </v-btn>
            </v-avatar>

            <v-avatar
                    :size="36"
                    v-else
            >
                <v-btn :disabled="$route.path === '/user'" @click="showProfile"
                       flat
                       icon
                       v-if="profile">
                    <v-icon>person</v-icon>
                </v-btn>
            </v-avatar>
            <v-btn @click="logout" icon v-if="profile">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import {mapMutations, mapState} from 'vuex'
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
                this.$store.dispatch('logout').then(res => this.$router.go(0))
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
                            this.addPostMutation(data.body);
                            break;
                        case 'UPDATE':
                            this.updatePostMutation(data.body);
                            break;
                        case 'REMOVE':
                            this.removePostMutation(data.body);
                            break;
                        default:
                            console.error(`Looks like the event type if unknown "${data.eventType}"`)
                    }
                } else if (data.objectType === 'COMMENT') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addCommentMutation(data.body);
                            break;
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
