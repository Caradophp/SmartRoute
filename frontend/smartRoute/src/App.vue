<script setup>
import { provide, ref } from 'vue';

import "vue3-toastify/dist/index.css";
import Loader from './components/Loader.vue';

const refModel = ref(false);
const showLoader = ref(false);

const openLoader = () => showLoader.value = true;
const closeLoader = () => showLoader.value = false;

const showModal = () => {
  refModel.value = true;
}

provide("openLoader", openLoader)
provide("closeLoader", closeLoader)
</script>

<template>
  <div class="main-container">
    <Loader v-if="showLoader" />
    <RouterView />
  </div>
</template>

<style scoped>
.main-container {
  width: 100vw;
  height: 100vh;
}
</style>
