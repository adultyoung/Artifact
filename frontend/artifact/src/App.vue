<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Artifact</v-toolbar-title>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/'"
                   @click="showPosts">
                Posts
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn flat
                   v-if="profile"
                   :disabled="$route.path === '/user'"
                   @click="showProfile">
                {{profile.username}}
            </v-btn>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import { addHandler } from './util/ws'

    export default {
        computed: mapState(['profile']),
        methods: {
            ...mapMutations([
                'addPostMutation',
                'updatePostMutation',
                'removePostMutation',
                'addCommentMutation'
            ]),
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
