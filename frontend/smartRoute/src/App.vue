<script setup>
import { onMounted, provide, ref } from 'vue';

import "vue3-toastify/dist/index.css";
import Loader from './components/Loader.vue';
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import Toast from './utils/Toast.js';
import { FwbButton, FwbModal } from 'flowbite-vue';
import { LMap, LMarker, LTileLayer } from '@vue-leaflet/vue-leaflet';
import 'leaflet/dist/leaflet.css'
import apiClient from './utils/Axios.js';

const showLoader = ref(false);

const openLoader = () => showLoader.value = true;
const closeLoader = () => showLoader.value = false;
const showEmergencyModal = ref(false);
const emergencia = ref(null)

provide("openLoader", openLoader)
provide("closeLoader", closeLoader)

onMounted(() => {
  const client = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws',
    reconnectDelay: 5000,

    onConnect: function () {
      console.log("WebSocket conectado!");

      client.subscribe('/topic/emergencias', function (message) {
        emergencia.value = JSON.parse(message.body);

        Toast.warning("Nova Emergência");
        showEmergencyModal.value = true;
      });
    },

    onWebSocketError: function (event) {
      console.error("Erro WebSocket:", event);
    },

    onWebSocketClose: function (event) {
      console.error(
        "WebSocket fechado:",
        event.code,
        event.reason
      );
    },

    onStompError: function (frame) {
      console.error(
        "Erro STOMP:",
        frame.headers["message"],
        frame.body
      );
    }
  });

  client.activate();
});

const sendAbulance = async () => {
  try {
    const response = await apiClient.post();

    if (response.status !== 201) {
      Toast.danger('Erro desconhecido. Contate o suporte técnico');
      return;
    }

    Toast.success('Ambulância Acionada')
  } catch (error) {
    Toast.error(error);
  }
}

const setMaskToPhoneNumber = (phoneNumber) => {
  return '(' + phoneNumber[0] + phoneNumber[1] + ') ' + phoneNumber.substring(2, 7) + '-' + phoneNumber.substring(7, 11)
}

</script>

<template>
  <div class="main-container">
    <Loader v-if="showLoader" />
    <Header />
    <RouterView />
    <Footer />
    <FwbModal persistent v-if="showEmergencyModal" size="" position="top-start'">
      <template #header>
        <h2>Nova Emegência</h2>
      </template>
      <template #body>
        <div class="emergency-body">
          <div class="flex justify-between">
            <div>
              <strong>Usuário:</strong> {{ emergencia.username }}
            </div>
            <div>
              <strong>E-mail:</strong> {{ emergencia.useremail }}
            </div>
            <div>
              <strong>Contato:</strong> {{ setMaskToPhoneNumber(emergencia.userphonenumber) }}
            </div>
          </div>

          <div class="map-container">
            <LMap
              :zoom="15"
              :center="[emergencia.latitude, emergencia.longitude]"
              style="height: 100%; width: 100%"
            >
              <LTileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution="&copy; OpenStreetMap contributors"
              />

              <LMarker
                v-if="emergencia && emergencia.latitude != null && emergencia.longitude != null"
                :lat-lng="[emergencia.latitude, emergencia.longitude]"
              />
            </LMap>
          </div>
        </div>
      </template>
      <template #footer>
        <div>
          <FwbButton color="red">Acionar Ambulância</FwbButton>
        </div>
      </template>
    </FwbModal>
  </div>
</template>

<style scoped>
.main-container {
  width: 100vw;
  height: 100vh;
}
.map-container {
  width: 100%;
  height: 450px;
  min-height: 300px;
  position: relative;
}

.map-container :deep(.leaflet-container) {
  width: 100%;
  height: 100%;
  z-index: 1;
}
</style>
