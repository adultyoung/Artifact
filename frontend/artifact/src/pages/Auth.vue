<template>
    <v-layout column>
        Необходимо авторизоваться через
        <v-btn href="auth/oauth">Google</v-btn>

        <v-flex xs12 class="text-xs-center" mt-5>
            <h3>Sign In</h3>
        </v-flex>
        <v-flex xs12 sm6 offset-sm3 mt-3>
            <form @submit.prevent="onSubmit">
                <vue-recaptcha
                        @verify="login"
                        ref="invisibleRecaptcha"
                        @expired="onExpired"
                        size="invisible"
                        :sitekey="sitekey"
                ></vue-recaptcha>
                <v-layout column>
                    <v-flex>
                        <v-alert error dismissible v-model="alert">
                            {{ error }}
                        </v-alert>
                    </v-flex>
                    <v-flex>
                        <v-text-field
                                name="username"
                                label="Username"
                                id="username"
                                type="text"
                                v-model="username"
                                required></v-text-field>
                    </v-flex>
                    <v-flex>
                        <v-text-field
                                name="password"
                                label="Password"
                                id="password"
                                type="password"
                                v-model="password"
                                required></v-text-field>
                    </v-flex>
                    <v-flex class="text-xs-center" mt-5>
                        <v-btn type="submit" dark color="teal lighten-1" autofocus>Sign In</v-btn>
                    </v-flex>
                </v-layout>
            </form>
        </v-flex>
    </v-layout>
</template>

<script>
    import VueRecaptcha from 'vue-recaptcha';
    import {mapActions} from 'vuex'

    export default {
        name: 'Auth',
        data() {
            return {
                sitekey: '6LcNU64UAAAAAPVy8rcn1wFm-eqWTcNGYMkNpgcQ',
                username: '',
                password: '',
                alert: false
            }
        },
        components: {VueRecaptcha},
        computed: {
            error() {
                return this.$store.getters.getError
            },
            loading() {
                return this.$store.getters.getLoading
            }
        },
        watch: {
            error(value) {
                if (value) {
                    this.alert = true
                }
            },
            alert(value) {
                if (!value) {
                    this.$store.dispatch('setError', false)
                }
            }
        },
        methods: {
            ...mapActions(
                [
                    'onExpired'
                ]
            ),
            login() {
                this.$store.dispatch('userSignIn', {username: this.username, password: this.password}).then(res => {
                    if (res) {
                        this.$router.push('/')
                    }
                })
            },
            onSubmit() {
                this.$refs.invisibleRecaptcha.execute()
            },
        }
    }
</script>


<style scoped>

</style>
