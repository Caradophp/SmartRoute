
<script setup>
import { computed } from 'vue'

const props = defineProps({
  title: {
    type: String,
    default: ''
  },
  oper: {
    type: String,
    default: 'inc',
    validator: value => ['inc', 'alt', 'con', 'exc'].includes(value)
  },
  buttons: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['close', 'submit'])

const dialogTitle = computed(() => {
  if (props.title) return props.title

  const titles = {
    inc: 'Novo registro',
    alt: 'Editar registro',
    con: 'Detalhes do registro',
    exc: 'Excluir registro'
  }

  return titles[props.oper]
})

const close = () => emit('close')
const submit = () => emit('submit')
</script>

<template>
  <div class="dialog-overlay" @click.self="close">
    <section
      class="dialog-card"
      role="dialog"
      aria-modal="true"
      :aria-label="dialogTitle"
    >
      <header class="dialog-header">
        <div class="header-content">
          <div class="header-icon">
            <i :class="oper === 'alt' ? 'fa-solid fa-pen' : 'fa-solid fa-plus'"></i>
          </div>

          <div>
            <h2>{{ dialogTitle }}</h2>
            <p>Preencha as informações abaixo.</p>
          </div>
        </div>

        <button
          type="button"
          class="close-icon"
          aria-label="Fechar janela"
          @click="close"
        >
          <i class="fa-solid fa-xmark"></i>
        </button>
      </header>

      <div class="dialog-body">
        <slot />
      </div>

      <footer class="dialog-footer">
        <div class="extra-actions">
          <button
            v-for="(button, index) in buttons"
            :key="index"
            type="button"
            class="extra-button"
            @click="button.event"
          >
            {{ button.label }}
          </button>
        </div>

        <div class="main-actions">
          <button type="button" class="cancel-button" @click="close">
            Cancelar
          </button>

          <button type="button" class="submit-button" @click="submit">
            <i class="fa-solid fa-check"></i>
            {{ oper === 'alt' ? 'Salvar alterações' : 'Cadastrar' }}
          </button>
        </div>
      </footer>
    </section>
  </div>
</template>

<style scoped>
.dialog-overlay {
  position: fixed;
  inset: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background: rgba(9, 32, 72, 0.5);
  backdrop-filter: blur(4px);
  box-sizing: border-box;
}

.dialog-card {
  width: 100%;
  max-width: 580px;
  max-height: min(90vh, 850px);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background: #ffffff;
  border: 1px solid #e1eaf5;
  border-radius: 20px;
  box-shadow: 0 24px 70px rgba(10, 37, 81, 0.22);
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  padding: 24px 28px;
  border-bottom: 1px solid #eaf0f7;
}

.header-content {
  display: flex;
  align-items: center;
  gap: 15px;
}

.header-icon {
  width: 46px;
  height: 46px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 13px;
  background: #eaf2ff;
  color: #1756e8;
  font-size: 18px;
}

.dialog-header h2 {
  margin: 0 0 5px;
  color: #103c7b;
  font-size: 20px;
  font-weight: 700;
}

.dialog-header p {
  margin: 0;
  color: #8293ac;
  font-size: 13px;
}

.close-icon {
  border: none;
  background: transparent;
  color: #8293ac;
  font-size: 20px;
  cursor: pointer;
}

.dialog-body {
  flex: 1;
  min-height: 0;
  overflow-y: auto;
  padding: 26px 28px;
}

.dialog-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 14px;
  padding: 20px 28px;
  border-top: 1px solid #eaf0f7;
  background: #fbfdff;
}

.extra-actions,
.main-actions {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.dialog-footer button {
  min-height: 42px;
  padding: 0 17px;
  border-radius: 10px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
}

.cancel-button,
.extra-button {
  border: 1px solid #d9e4f1;
  background: #ffffff;
  color: #526987;
}

.submit-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  border: none;
  background: #1756e8;
  color: white;
}

.submit-button:hover {
  background: #1045c5;
}

@media (max-width: 600px) {
  .dialog-header,
  .dialog-body,
  .dialog-footer {
    padding: 18px;
  }

  .dialog-footer {
    flex-direction: column;
    align-items: stretch;
  }

  .main-actions {
    width: 100%;
  }

  .main-actions button {
    flex: 1;
  }
}
</style>
