<template>
    <b-button variant="primary" @click="navigateTo()">{{ message }}</b-button>
</template>

<script>
export default {
    name: 'Button',
    props: {
        path: String,
        message: String
    },
    methods: {
        navigateTo() {
            this.$router.push(this.path);
        }
    },
}
</script>

<style>

</style>