<template>
    <v-container>
        <v-flex class="d-flex justify-center">
            <v-flex xs6 ma-5 class="elevation-1" style="background: #FFFFFF">
                <v-flex class="text-xs-center" sm6 offset-sm3 mt-3>
                    <h2>Sign Up</h2>
                    <form v-on:submit.prevent="reg" v-model="valid">
                        <v-flex>
                            <v-text-field
                                    v-model="usernameReg"
                                    :rules="nameRules"
                                    :counter="10"
                                    label="Username"
                                    required
                            ></v-text-field>
                        </v-flex>

                        <v-flex>
                            <v-text-field
                                    v-model="passwordReg"
                                    :rules="passwordRules"
                                    :counter="20"
                                    label="Password"
                                    type="password"
                                    required
                            ></v-text-field>
                        </v-flex>

                        <v-flex>
                            <v-text-field
                                    v-model="emailReg"
                                    :rules="emailRules"
                                    label="E-mail"
                                    required
                            ></v-text-field>
                        </v-flex>
                        <v-flex class="text-xs-center" mt-2>
                            <v-btn type="submit" dark color="teal lighten-1" autofocus>Sign Up</v-btn>
                        </v-flex>
                    </form>
                </v-flex>
            </v-flex>
        <v-spacer></v-spacer>
        <v-flex xs6 class="text-xs-center" ma-5>
            <v-flex pt-3 class="elevation-1" style="background: #FFFFFF">
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
                        <img width="20px" alt="Google &quot;G&quot; Logo" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"/>
                        &nbsp;Login with Google
                    </v-btn>
                </v-flex>
            </v-flex>
        </v-flex>
        </v-flex>
    </v-container>
</template>

<script>
    import VueRecaptcha from 'vue-recaptcha';
    import {mapActions, mapState} from 'vuex'

    export default {
        name: 'Auth',
        data() {
            return {
                valid: false,
                usernameReg: '',
                passwordReg: '',
                nameRules: [
                    v => !!v || 'Name is required',
                    v => v.length <= 10 || 'Name must be less than 10 characters',
                ],
                passwordRules: [
                    v => !!v || 'Password is required',
                    v => v.length <= 100 || 'Passsword must be less 20 characters',
                ],
                emailReg: '',
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
            ...mapState(['profile', 'message']),
        },
        methods: {
            ...mapActions(
                [
                    'onExpired'
                ]
            ),
            reg() {
                this.$store.dispatch('registration', {
                    username: this.usernameReg,
                    password: this.passwordReg,
                    email: this.emailReg
                })
            },
            login() {
                this.$store.dispatch('userSignIn', {username: this.username, password: this.password}).then(res => {
                 this.$router.go(0);
                });

            },
            onSubmit() {
                this.$refs.invisibleRecaptcha.execute()
            },
        },
        beforeMount() {
            if (this.profile) {
                this.$router.replace('/')
            }
        }
    }
</script>


<style scoped>

</style>
