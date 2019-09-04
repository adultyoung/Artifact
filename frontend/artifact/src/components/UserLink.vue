<template>
    <v-flex ma-2>
        <router-link
                :to="`/user/${user.id}`"
        >
            <v-avatar
                    :size="`${size || 36}px`"
                    v-if="user && user.picture"
            >
                <img
                        :alt="user.name"
                        :src="user.picture"
                >
            </v-avatar>

            <v-avatar
                    :size="`${size || 36}px`"
                    v-else
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
