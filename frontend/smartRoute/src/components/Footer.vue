<script setup>
import { ref } from 'vue';

const showFooter = ref(false)

addEventListener("DOMContentLoaded", () => {
    const url = location.href.split('/');

    if (url[3] != undefined && url[3] != "" && url[3] != 'login') {
        showFooter.value = true;
    } else {
        showFooter.value = false;
    }

});
</script>

<template>
    <div v-if="showFooter" class="footer">
        <span>SmartRoute<sup>@</sup> - Todos os direitos reservados</span>
    </div>
</template>

<style scoped>
.footer {
    height: 100px;
    width: 100vw;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    background-color: rgb(83, 83, 83);
    color: white;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
}
</style>