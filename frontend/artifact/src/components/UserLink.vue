<template>
    <v-flex ma-2>
        <router-link
        :to="`/user/${user.id}`"
    >
        <v-avatar
                v-if="user && user.picture"
                :size="`${size || 36}px`"
        >
            <img
                    :src="user.picture"
                    :alt="user.name"
            >
        </v-avatar>

        <v-avatar
                v-else
                :size="`${size || 36}px`"
        >
            <v-icon>account_circle</v-icon>
        </v-avatar>
            &nbsp;
        <span>{{ userName }}</span>
    </router-link>
    </v-flex>

</template>

<script>
    export default {
        name: 'UserLink',
        props: ['user', 'size'],
        computed: {
            userName() {
                return this.user ? this.user.username : 'unknown'
            }
        },
    }
</script>

<style scoped>

</style>
