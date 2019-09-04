<template>
    <v-list two-line>
        <v-subheader>
            Comments
        </v-subheader>

        <template
                v-for="(item, index) in comments"
        >
            <v-divider
                    :key="index"
                    v-if="index > 0"
            ></v-divider>

            <comment-item
                    :comment="item"
                    :key="'item' + index"
            ></comment-item>
        </template>

        <comment-form :post-id="postId"></comment-form>
    </v-list>
</template>

<script>
    import CommentForm from './CommentForm.vue'
    import CommentItem from './CommentItem.vue'

    export default {
        name: 'CommentList',
        components: {CommentForm, CommentItem},
        props: ['comments', 'postId']
    }
</script>

<style scoped>

</style>
