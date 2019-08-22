<template>
    <v-layout raw wrap fill-height>
        <v-flex>
        <v-flex xs3 mt-5 my-5 class="elevation-1 md-alignment-left" style="background: #FFFFFF">
            <v-form v-model="valid">
                <v-flex>
                    <v-text-field
                            v-model="firstname"
                            :rules="nameRules"
                            :counter="10"
                            label="First name"
                            required
                    ></v-text-field>
                </v-flex>

                <v-flex>
                    <v-text-field
                            v-model="lastname"
                            :rules="nameRules"
                            :counter="10"
                            label="Last name"
                            required
                    ></v-text-field>
                </v-flex>

                <v-flex>
                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>
                </v-flex>
            </v-form>
        </v-flex>
        </v-flex>
            <v-spacer></v-spacer>
            <v-divider vertical></v-divider>
        <v-flex xs3 class="text-xs-center" my-5>
            <v-flex mt-5 class="elevation-1" style="background: #FFFFFF">
                <h2>Sign In</h2>
                <v-flex sm6 offset-sm3 mt-3>
                    <form @submit.prevent="onSubmit">
                        <vue-recaptcha
                                @verify="login"
                                ref="invisibleRecaptcha"
                                @expired="onExpired"
                                size="invisible"
                                :sitekey="sitekey"
                        ></vue-recaptcha>
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
                        <v-flex class="text-xs-center" mt-2>
                            <v-btn type="submit" dark color="teal lighten-1" autofocus>Sign In</v-btn>
                        </v-flex>
                    </form>
                    <v-btn flat href="/auth/oauth">
                        <h3>Google Sign in</h3>
                    </v-btn>
                </v-flex>
            </v-flex>
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
                valid: false,
                firstname: '',
                lastname: '',
                nameRules: [
                    v => !!v || 'Name is required',
                    v => v.length <= 10 || 'Name must be less than 10 characters',
                ],
                email: '',
                emailRules: [
                    v => !!v || 'E-mail is required',
                    v => /.+@.+/.test(v) || 'E-mail must be valid',
                ],
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
