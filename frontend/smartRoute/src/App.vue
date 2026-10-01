<script setup>
import { ref } from 'vue';

import "vue3-toastify/dist/index.css";

  const refModel = ref(false);

  const showModal = () => {
    refModel.value = true;
  }
</script>

<template>
  <RouterView />
</template>

<style scoped></style>
