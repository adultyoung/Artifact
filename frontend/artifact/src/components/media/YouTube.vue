<template>
    <iframe
            :src="targetSrc"
            allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
            frameborder="0"
            height="315"
            width="560"
    ></iframe>
</template>

<script>
    export default {
        name: 'YouTube',
        props: ['src'],
        computed: {
            targetSrc() {
                let parts = this.src.split('/');
                return `https://www.youtube.com/embed/${parts[parts.length - 1]}`
            }
        }
    }
</script>

<style scoped>

</style>
