<template>
    <div id="app">
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>Meh</v-toolbar-title>
            <v-btn flat>
                Messages
            </v-btn>
            <v-spacer></v-spacer>
        </v-toolbar>
        <v-content>
            <router-view></router-view>
        </v-content>
    </v-app>
    </div>
</template>

<script>
    export default {
        name: 'App'
    }
</script>

<style scoped>

</style>