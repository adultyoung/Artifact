<template>
    <div>
       <div v-if="!profile">необходимо авторизоваться через <a href="/login">Google</a></div>
       <div v-else>
           <div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>
       </div>
    </div>
</template>

<script>
    export default {
        name: "App",
        data() {
            return {
                profile: frontendData.profile
            }
        }
    }
</script>

<style scoped>

</style>