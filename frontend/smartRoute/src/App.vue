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
const showQuestion = ref(true);
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

const checkIsNecessary = async () => {
  try {

    const response = await apiClient.get(`http://localhost:8080/emergencias/is-necessary?id=${emergencia.value.userId}&longitude=${emergencia.value.longitude}&latitude=${emergencia.value.latitude}`);

    if ((response.status == 200) == false) {
      Toast.danger("Erro desconhecido. Contate o suporte técnico");
      return;
    }

    const data = await response.data;

    if (data.lenght == 0) {
      await sendAbulance();
    } else {
      showQuestion.value = true;
    }

  } catch (error) {
    Toast.error(error);
  }
}

const sendAbulance = async () => {
  try {
    const response = await apiClient.post(`http://localhost:8080/emergencias/ambulance`, {
      id: emergencia.value.id,
      latitude: emergencia.value.latitude,
      longitude: emergencia.value.longitude,
      username: emergencia.value.username,
      useremail: emergencia.value.useremail,
      userphonenumber: emergencia.value.userphonenumber
    });

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

const close = () => showQuestion.value = false;

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
          <FwbButton color="red" @click="checkIsNecessary">Acionar Ambulância</FwbButton>
        </div>
      </template>
    </FwbModal>
    <FwbModal persistent v-if="showQuestion">
      <template #header>
        <h1>Confirme</h1>
      </template>
      <template #body>
        <span>Já existe uma ambulância há caminho. Deseja enviar outra?</span>
      </template>
      <template #footer>
        <div class="question">
          <FwbButton color="red" @click="close">Não</FwbButton>
          <FwbButton color="blue" @click="sendAbulance">Sim</FwbButton>
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

.question {
  width: 100%;
  display: flex;
  justify-content: end;
  gap: 10px;
}
</style>
