<script setup>
import {
  FwbNavbar,
  FwbNavbarCollapse,
  FwbNavbarLink,
  FwbNavbarLogo,
} from 'flowbite-vue'
import { ref } from 'vue';

const showHeader = ref(false)

addEventListener("DOMContentLoaded", () => {
    const url = location.href.split('/');

    if (url[3] != undefined && url[3] != "" && url[3] != 'login') {
        showHeader.value = true;
    } else {
        showHeader.value = false;
    }

});

</script>
<template>
  <fwb-navbar solid v-if="showHeader">
    <template #logo>
      <fwb-navbar-logo image-url="/home/luciano/faculdade/SmartRoute/frontend/smartRoute/src/assets/img/logo.png" link="#">
        SmartRoute
      </fwb-navbar-logo>
    </template>
    <template #default="{isShowMenu}">
      <fwb-navbar-collapse :is-show-menu="isShowMenu">
        <fwb-navbar-link is-active link="#">
          Home
        </fwb-navbar-link>
        <fwb-navbar-link link="#">
          Ambulâncias
        </fwb-navbar-link>
        <fwb-navbar-link link="#">
          Agentes de Saúde
        </fwb-navbar-link>
        <fwb-navbar-link link="#">
          Áreas
        </fwb-navbar-link>
      </fwb-navbar-collapse>
    </template>
  </fwb-navbar>
</template>