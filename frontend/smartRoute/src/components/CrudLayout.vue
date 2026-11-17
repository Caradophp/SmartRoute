<script setup>
import { FwbAutocomplete, FwbButton, FwbInput, FwbModal, FwbP, FwbSelect, FwbTextarea } from 'flowbite-vue';
import Dialog from './Dialog.vue';
import DataTable from './grid/DataTable.vue';
import HeaderPanel from './HeaderPanel.vue';
import { onMounted, ref, unref, useTemplateRef } from 'vue';
import Toast from '@/utils/Toast.js';
import apiClient from '@/utils/Axios.js';

const props = defineProps({
    url: {
        type: String,
        required: true
    },
    columns: {
        type: Array,
        required: true
    },
    formFields: {
        type: Array,
        required: true
    },
    jsonData: {
        type: Function,
        required: true
    },
    setFormData: {
        type: Function,
        required: true
    },
    extraButtons: {
        type: Array,
        required: false
    }
})

const showDialog = ref(false);
const showConfirmDialog = ref(false);
const oper = ref('inc');
const currentDataId = ref('');
const searchParam = ref('');
const grid = useTemplateRef('grid');

const openForm = () => showDialog.value = true;
const closeForm = () => {
    props.formFields.map((r) => {
        r.model.value = '';
    })
    showDialog.value = false
};
const doConfirmOn = () => showConfirmDialog.value = true;
const doConfirmOff = () => showConfirmDialog.value = false;

const showInc = () => {
    oper.value = 'inc';
    openForm();
}

const showAlt = (data) => {
    currentDataId.value = data.id;
    oper.value = 'alt';
    props.setFormData(data);
    openForm();
}

const showExc = (id) => {
    currentDataId.value = id;
    oper.value = 'exc';
    doConfirmOn();
}

const inc = async () => {
    try {
        const response = await apiClient.post(`http://localhost:8080/${props.url}/create`, props.jsonData());

        if (response.status !== 201 && response.status !== 200) {
            Toast.danger('Erro desconhecido ao inserir registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        closeForm();
        grid.value.reload();
    } catch (error) {
        Toast.error(error)
    }
}

const alt = async () => {
    try {
        const response = await apiClient.put(`http://localhost:8080/${props.url}/alter/${currentDataId.value}`, props.jsonData());

        if (response.status !== 200) {
            Toast.danger('Erro desconhecido ao alterar registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        closeForm();
        grid.value.reload();
    } catch (error) {
        Toast.error(error)
    }
}

const exc = async () => {
    try {
        const response = await apiClient.delete(`http://localhost:8080/${props.url}/delete/${currentDataId.value}`);

        if (response.status !== 200) {
            Toast.danger('Erro desconhecido ao deletar registro. Contate o suporte técnico');
            return;
        }

        Toast.info('Operação realizada com sucesso');
        grid.value.reload();
        doConfirmOff();
    } catch (error) {
        Toast.error(error)
    }
}

const search = () => {
    grid.value?.search(searchParam.value);
}

const eventhandler = () => {
    let operation = oper.value;

    if (operation === 'inc') {
        inc();
    } else if (operation === 'alt') {
        alt();
    } else if (operation === 'exc') {
        exc();
    } else {
        Toast.warning('Operador inválido');
    }
}

onMounted(() => {
});
</script>

<template>
    <main class="main-page">
        <HeaderPanel :novo-click="showInc" :pesquisa-click="search" v-model:inputSearch="searchParam"/>
        <DataTable :url="props.url" :columns="props.columns" :on-edit="showAlt" :on-delete="showExc" ref="grid"/>
        <Dialog
  v-if="showDialog"
  :oper="oper"
  :buttons="oper === 'alt' ? props.extraButtons : []"
  @close="closeForm"
  @submit="eventhandler"
>
            <form>
                <div v-for="formField in props.formFields">
                    <div class="input-content">
                        <label>{{ formField.label }}</label>
                        <FwbAutocomplete
                            v-model="formField.model.value"
                            :options="unref(formField.options)"
                            :search-fields="formField.searchFields"
                            display="nome"
                            placeholder=""
                            :no-results-text="'Sem resultados para sua pesquisa'"
                            v-if="formField.fieldType == 'autocomplete'"
                        />
                        <!-- <FwbInput :type="formField.fieldType" v-model="formField.model.value"/> -->
                        <FwbSelect v-else-if="formField.fieldType == 'select'" :placeholder="'Selecione...'" :options="formField.options" v-model="formField.model.value"/>
                        <FwbTextarea v-else-if="formField.fieldType == 'textarea'" v-model="formField.model.value" />
                        <FwbInput :type="formField.fieldType" v-model="formField.model.value" v-else/>
                    </div>
                </div>
            </form>
        </Dialog>

<div
  v-if="showConfirmDialog"
  class="delete-overlay"
  @click.self="doConfirmOff"
>
  <section
    class="delete-dialog"
    role="alertdialog"
    aria-modal="true"
    aria-labelledby="delete-title"
    aria-describedby="delete-description"
  >
    <button
      type="button"
      class="delete-close"
      aria-label="Fechar"
      @click="doConfirmOff"
    >
      <i class="fa-solid fa-xmark"></i>
    </button>

    <div class="delete-icon">
      <i class="fa-solid fa-trash-can"></i>
    </div>

    <h2 id="delete-title">Excluir registro?</h2>

    <p id="delete-description">
      Tem certeza de que deseja excluir este registro?
      Essa ação não poderá ser desfeita.
    </p>

    <div class="delete-actions">
      <button
        type="button"
        class="delete-cancel"
        @click="doConfirmOff"
      >
        Cancelar
      </button>

      <button
        type="button"
        class="delete-confirm"
        @click="exc"
      >
        <i class="fa-solid fa-trash-can"></i>
        Excluir registro
      </button>
    </div>
  </section>
</div>

    </main>
</template>

<style scoped>

.main-page {
    width: 100%;
    min-width: 0;
    min-height: 0;
    display: flex;
    flex-direction: column;
    align-items: stretch;
    gap: 20px;
    box-sizing: border-box;
    padding: 28px 32px 48px;
}

.input-content {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  gap: 8px;
  margin-bottom: 20px;
}

.input-content label {
  color: #173b69;
  font-size: 14px;
  font-weight: 600;
}

.input-content :deep(input),
.input-content :deep(select),
.input-content :deep(textarea) {
  width: 100%;
  box-sizing: border-box;
  border-radius: 10px;
}

.confirm-footer {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    gap: 10px;
}


.delete-overlay {
  position: fixed;
  inset: 0;
  z-index: 1100;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background: rgba(9, 32, 72, 0.5);
  backdrop-filter: blur(4px);
  box-sizing: border-box;
}

.delete-dialog {
  position: relative;
  width: 100%;
  max-width: 440px;
  padding: 34px 30px 28px;
  background: #ffffff;
  border: 1px solid #e5edf5;
  border-radius: 20px;
  box-shadow: 0 24px 70px rgba(10, 37, 81, 0.22);
  text-align: center;
  box-sizing: border-box;
}

.delete-close {
  position: absolute;
  top: 20px;
  right: 22px;
  border: none;
  background: transparent;
  color: #8192a9;
  font-size: 19px;
  cursor: pointer;
}

.delete-icon {
  width: 66px;
  height: 66px;
  margin: 0 auto 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 19px;
  background: #fff0f1;
  color: #dc3545;
  font-size: 27px;
}

.delete-dialog h2 {
  margin: 0 0 12px;
  color: #103c7b;
  font-size: 22px;
  font-weight: 700;
}

.delete-dialog p {
  margin: 0 auto 28px;
  max-width: 340px;
  color: #71839c;
  font-size: 14px;
  line-height: 1.7;
}

.delete-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
}

.delete-actions button {
  min-height: 46px;
  padding: 0 18px;
  border-radius: 11px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
}

.delete-cancel {
  border: 1px solid #dce6f1;
  background: #ffffff;
  color: #526987;
}

.delete-confirm {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 9px;
  border: none;
  background: #dc3545;
  color: #ffffff;
}

.delete-confirm:hover {
  background: #bd2636;
}

.delete-close:focus-visible,
.delete-actions button:focus-visible {
  outline: 3px solid #9cc2ff;
  outline-offset: 3px;
}

@media (max-width: 440px) {
  .delete-dialog {
    padding: 30px 20px 24px;
  }

  .delete-actions {
    flex-direction: column;
  }

  .delete-actions button {
    width: 100%;
  }
}


</style>