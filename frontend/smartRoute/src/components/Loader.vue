<script setup>
import { ref } from 'vue'

const isLoading = ref(true)

</script>

<template>
  <div class="container" v-if="isLoading" >
      <div class="spinner"></div>
      <p>Carregando...</p>
  </div>
</template>

<style scoped>

.container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.7);
    color: white;
    z-index: 9999;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f3f3f3;
  border-top: 5px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>