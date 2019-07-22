<template>
    <v-card>
        <v-flex v-if="type === 'href'" sm6>
            <v-img v-if="post.linkCover" :src="post.linkCover" aspect-ratio="2.75"></v-img>
            <v-card-title>
                <div>
                    <h3>
                        <a :href="post.link">{{post.linkTitle || post.link}}</a>
                    </h3>
                    <div v-if="post.linkDescription">{{post.linkDescription}}</div>
                </div>
            </v-card-title>
        </v-flex>
        <v-flex v-if="type === 'image'" sm6>
            <a :href="post.link">
                <v-img v-if="post.linkCover" :src="post.linkCover" aspect-ratio="2.75"></v-img>
                {{post.link}}
            </a>
        </v-flex>
        <v-flex v-if="type === 'youtube'" sm6>
            <you-tube :src="post.link"></you-tube>
        </v-flex>
    </v-card>
</template>

<script>
    import YouTube from './YouTube.vue'

    export default {
        name: 'Media',
        components: { YouTube },
        props: ['post'],
        data() {
            return {
                type: 'href'
            }
        },
        beforeMount() {
            if (this.post.link.indexOf('youtu') > -1) {
                this.type = 'youtube'
            } else if (this.post.link.match(/\.(jpeg|jpg|gif|png)$/) !== null) {
                this.type = 'image'
            } else {
                this.type = 'href'
            }
        }
    }
</script>

<style scoped>

</style>
