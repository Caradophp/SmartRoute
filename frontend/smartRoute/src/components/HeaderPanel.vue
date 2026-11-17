
<script setup>
defineProps({
  novoClick: {
    type: Function,
    required: true
  },
  pesquisaClick: {
    type: Function,
    required: true
  },
  inputSearch: {
    type: String,
    required: true
  }
})

const emit = defineEmits(['update:inputSearch'])
</script>

<template>
  <div class="crud-toolbar">
    <div class="search-area">
      <div class="search-field">
        <i class="fa-solid fa-magnifying-glass search-icon"></i>

        <input
          :value="inputSearch"
          type="search"
          placeholder="Pesquisar registros..."
          aria-label="Pesquisar registros"
          @input="emit('update:inputSearch', $event.target.value)"
          @keyup.enter="pesquisaClick"
        />
      </div>

      <button
        type="button"
        class="search-button"
        @click="pesquisaClick"
      >
        <i class="fa-solid fa-magnifying-glass"></i>
        <span>Pesquisar</span>
      </button>
    </div>

    <button
      type="button"
      class="new-button"
      @click="novoClick"
    >
      <i class="fa-solid fa-plus"></i>
      <span>Novo registro</span>
    </button>
  </div>
</template>

<style scoped>
.crud-toolbar {
  width: 100%;
  min-width: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  padding: 22px 26px;
  box-sizing: border-box;
  background: #ffffff;
  border: 1px solid #e3ebf5;
  border-radius: 18px;
  box-shadow: 0 5px 24px rgba(23, 65, 124, 0.05);
}

.search-area {
  display: flex;
  align-items: center;
  flex: 1;
  min-width: 0;
  max-width: 600px;
  gap: 12px;
}

.search-field {
  position: relative;
  flex: 1;
  min-width: 0;
}

.search-field input {
  width: 100%;
  height: 46px;
  padding: 0 16px 0 44px;
  box-sizing: border-box;
  border: 1px solid #dce6f2;
  border-radius: 11px;
  outline: none;
  background: #f7faff;
  color: #173b69;
  font-size: 14px;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.search-field input::placeholder {
  color: #94a3b8;
}

.search-field input:focus {
  border-color: #3478eb;
  box-shadow: 0 0 0 3px rgba(52, 120, 235, 0.1);
  background: #ffffff;
}

.search-icon {
  position: absolute;
  top: 50%;
  left: 16px;
  transform: translateY(-50%);
  color: #8198b8;
  pointer-events: none;
}

.search-button,
.new-button {
  height: 46px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 9px;
  padding: 0 20px;
  border: none;
  border-radius: 11px;
  background: #1756e8;
  color: #ffffff;
  font-size: 14px;
  font-weight: 600;
  white-space: nowrap;
  cursor: pointer;
  transition: background 0.2s, transform 0.2s, box-shadow 0.2s;
}

.search-button:hover,
.new-button:hover {
  background: #1045c5;
  transform: translateY(-1px);
  box-shadow: 0 6px 14px rgba(23, 86, 232, 0.18);
}

.search-button:focus-visible,
.new-button:focus-visible {
  outline: 3px solid #9cc2ff;
  outline-offset: 2px;
}

.new-button {
  background: #123e85;
}

.new-button:hover {
  background: #0b306c;
}

@media (max-width: 760px) {
  .crud-toolbar {
    flex-direction: column;
    align-items: stretch;
    padding: 18px;
    gap: 14px;
  }

  .search-area {
    max-width: none;
    width: 100%;
  }

  .new-button {
    width: 100%;
  }
}

@media (max-width: 440px) {
  .search-area {
    flex-direction: column;
    align-items: stretch;
  }

  .search-button {
    width: 100%;
  }
}
</style>
    