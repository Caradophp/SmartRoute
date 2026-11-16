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
    min-height: 80px;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px;
    background-color: #0b2f68;
    color: white;
    text-align: center;
}
</style>