<template>
    <v-container>
        <v-flex class="d-flex justify-center">
            <v-flex class="elevation-1" ma-5 style="background: #FFFFFF" xs6>
                <v-flex class="text-xs-center" mt-3 offset-sm3 sm6>
                    <h2>Sign Up</h2>
                    <form v-model="valid" v-on:submit.prevent="reg">
                        <v-flex>
                            <v-text-field
                                    :counter="10"
                                    :rules="nameRules"
                                    label="Username"
                                    required
                                    v-model="usernameReg"
                            ></v-text-field>
                        </v-flex>

                        <v-flex>
                            <v-text-field
                                    :counter="20"
                                    :rules="passwordRules"
                                    label="Password"
                                    required
                                    type="password"
                                    v-model="passwordReg"
                            ></v-text-field>
                        </v-flex>

                        <v-flex>
                            <v-text-field
                                    :rules="emailRules"
                                    label="E-mail"
                                    required
                                    v-model="emailReg"
                            ></v-text-field>
                        </v-flex>
                        <v-flex class="text-xs-center" mt-2>
                            <v-btn autofocus color="teal lighten-1" dark type="submit">Sign Up</v-btn>
                        </v-flex>
                    </form>
                </v-flex>
            </v-flex>
            <v-spacer></v-spacer>
            <v-flex class="text-xs-center" ma-5 xs6>
                <v-flex class="elevation-1" pt-3 style="background: #FFFFFF">
                    <h2>Sign In</h2>
                    <v-flex mt-3 offset-sm3 sm6>
                        <form @submit.prevent="onSubmit">
                            <vue-recaptcha
                                    :sitekey="sitekey"
                                    @expired="onExpired"
                                    @verify="login"
                                    ref="invisibleRecaptcha"
                                    size="invisible"
                            ></vue-recaptcha>
                            <v-flex>
                                <v-text-field
                                        id="username"
                                        label="Username"
                                        name="username"
                                        required
                                        type="text"
                                        v-model="username"></v-text-field>
                            </v-flex>
                            <v-flex>
                                <v-text-field
                                        id="password"
                                        label="Password"
                                        name="password"
                                        required
                                        type="password"
                                        v-model="password"></v-text-field>
                            </v-flex>
                            <v-flex class="text-xs-center" mt-2>
                                <v-btn autofocus color="teal lighten-1" dark type="submit">Sign In</v-btn>
                            </v-flex>
                        </form>
                        <v-btn flat href="/auth/oauth">
                            <img alt="Google &quot;G&quot; Logo" src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"
                                 width="20px"/>
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
