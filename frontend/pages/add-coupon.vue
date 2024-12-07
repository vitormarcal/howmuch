<template>
  <div class="ocr-page">
    <nuxt-link to="/" class="home-link">Início</nuxt-link>
    <h1 class="page-title">Enviar Imagem para OCR</h1>

    <form @submit.prevent="handleSubmit" class="upload-form">
      <input
          type="file"
          accept="image/*"
          @change="handleFileChange"
          ref="imageInput"
          class="file-input"
      />
      <button type="submit" class="submit-button">Enviar</button>
    </form>

    <div v-if="ocrResult" class="ocr-result">
      <h2 class="result-title">Resultado do OCR:</h2>

      <div class="editable-fields">
        <label for="store">Loja:</label>
        <input id="store" v-model="editableResult.store" type="text" class="input-field" />

        <label for="cnpj">CNPJ:</label>
        <input id="cnpj" v-model="editableResult.cnpj" type="text" class="input-field" />

        <label for="date_time">Data e Hora:</label>
        <input id="date_time" v-model="editableResult.date_time" type="text" class="input-field" />

        <label for="access_key">Chave de Acesso:</label>
        <input id="access_key" v-model="editableResult.access_key" type="text" class="input-field" />
      </div>

      <table class="items-table">
        <thead>
        <tr>
          <th>Descrição</th>
          <th>Quantidade</th>
          <th>Preço Unitário</th>
          <th>Preço Total</th>
          <th>ID</th>
          <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in editableResult.items" :key="index">
          <td><input v-model="item.description" type="text" class="input-field" /></td>
          <td><input v-model.number="item.quantity" type="number" class="input-field" /></td>
          <td><input v-model.number="item.unit_price" type="number" step="0.01" class="input-field" /></td>
          <td><input v-model.number="item.total_price" type="number" step="0.01" class="input-field" /></td>
          <td><input v-model="item.id" type="text" class="input-field" /></td>
          <td>
            <button @click="removeItem(index)">Remover</button>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="buttons">
        <button @click="addNewItem" class="add-item-button">Adicionar Novo Item</button>
        <button @click="handleSave" class="save-button">Salvar Alterações</button>
      </div>
    </div>

    <div v-if="errorMessage" class="error-message">
      <p>{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const image = ref(null);
const ocrResult = ref(null);
const editableResult = ref(null);
const errorMessage = ref(null);

const handleSubmit = async () => {
  if (!image.value) {
    errorMessage.value = "Por favor, selecione uma imagem.";
    return;
  }

  const formData = new FormData();
  formData.append('image', image.value);

  try {
    const response = await $fetch('http://localhost:8080/api/ocr/recognize', {
      method: 'POST',
      body: formData,
    });

    ocrResult.value = response;
    editableResult.value = JSON.parse(JSON.stringify(response));
    sortItemsById();
    errorMessage.value = null;
  } catch (error) {
    errorMessage.value = "Erro ao processar a imagem. Tente novamente.";
    console.error(error);
  }
};

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    image.value = file;
    errorMessage.value = null;
  }
};

const handleSave = async () => {
  sortItemsById();
  console.log("Dados salvos:", editableResult.value);
  ocrResult.value = JSON.parse(JSON.stringify(editableResult.value));

  try {
    await $fetch('http://localhost:8080/api/coupons', {
      method: 'POST',
      body: JSON.stringify(editableResult.value),
    });
  } catch (error) {
    errorMessage.value = "Erro ao salvar o cupom. Tente novamente.";
    console.error(error);
  }
};

const addNewItem = () => {
  editableResult.value.items.push({
    description: "",
    quantity: 0,
    unit_price: 0.0,
    total_price: 0.0,
    id: String(editableResult.value.items.length + 1),
  });
  sortItemsById();
};

// Função para remover um item
const removeItem = (index) => {
  editableResult.value.items.splice(index, 1); // Remove o item do array
  sortItemsById(); // Ordenar itens após remover um item
};

const sortItemsById = () => {
  editableResult.value.items.sort((a, b) => parseInt(a.id) - parseInt(b.id));
};
</script>

<style scoped>
/* Container geral */
.ocr-page {
  font-family: 'Arial', sans-serif;
  background-color: #f4f4f9;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.home-link {
  display: inline-block;
  padding: 10px 20px;
  margin-bottom: 20px;
  background-color: #4CAF50;
  color: white;
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background-color 0.3s;
}

.home-link:hover {
  background-color: #45a049;
}

/* Título da página */
.page-title {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

/* Estilo do formulário de upload */
.upload-form {
  margin-bottom: 20px;
}

.file-input {
  padding: 12px 20px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 8px;
  width: 100%;
  margin-bottom: 10px;
}

.submit-button {
  padding: 12px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #45a049;
}

/* Exibição do resultado do OCR */
.ocr-result {
  margin-top: 20px;
}

/* Campos editáveis */
.editable-fields {
  margin-bottom: 20px;
}

.editable-fields label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}

.input-field {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
  box-sizing: border-box;
}

/* Tabela de itens */
.items-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.items-table th, .items-table td {
  padding: 12px;
  text-align: left;
}

.items-table th {
  background-color: #4CAF50;
  color: white;
}

.items-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.items-table tr:hover {
  background-color: #f1f1f1;
}

.items-table td {
  border-bottom: 1px solid #ddd;
}

/* Botões de ação */
.buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.add-item-button, .save-button {
  padding: 12px 20px;
  font-size: 16px;
  border-radius: 8px;
  cursor: pointer;
}

.add-item-button {
  background-color: #008CBA;
  color: white;
}

.add-item-button:hover {
  background-color: #007bb5;
}

.save-button {
  background-color: #4CAF50;
  color: white;
}

.save-button:hover {
  background-color: #45a049;
}

/* Mensagem de erro */
.error-message {
  color: red;
  text-align: center;
  font-size: 16px;
  margin-top: 20px;
}

/* Estilos para o botão remover */
.items-table button {
  padding: 6px 12px;
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.items-table button:hover {
  background-color: #d32f2f;
  transform: scale(1.05); /* Efeito de aumento no hover */
}

.items-table button:focus {
  outline: none;
}

.items-table button:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}
</style>
