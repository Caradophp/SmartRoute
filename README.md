# 🚀 SmartRoute

Guia rápido para configurar, executar e entender o projeto **SmartRoute**.

Este documento apresenta os pré-requisitos, as instruções de instalação e execução, além de links para a documentação específica de cada módulo do projeto.

---

## 📋 Sumário

- [🔧 Pré-requisitos](#-pré-requisitos)
- [📥 Clonando o repositório](#-clonando-o-repositório)
- [📦 Instalando as dependências](#-instalando-as-dependências)
  - [Frontend](#-frontend)
  - [Backend](#-backend)
- [▶️ Executando o projeto](#️-executando-o-projeto)
  - [Frontend](#-frontend-1)
  - [Backend](#-backend-1)
- [🐳 Docker](#-docker)
- [📚 Documentação](#-documentação)
- [🤝 Contribuição e suporte](#-contribuição-e-suporte)

---

## 🔧 Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

| Ferramenta | Requisito | Finalidade |
| :--- | :--- | :--- |
| **Git** | Versão estável | Clonagem e gerenciamento do repositório |
| **Node.js** | Versão LTS | Execução do frontend |
| **npm** ou **Yarn** | Versão compatível | Gerenciamento de dependências do frontend |
| **Java** | Versão 21 ou superior | Execução do backend |
| **Maven** | Versão compatível | Compilação e gerenciamento de dependências do backend |
| **Docker** | Opcional | Execução de serviços em contêineres |

> 💡 **Observação:** Instale apenas as ferramentas necessárias para a stack que pretende executar. Consulte a documentação específica de cada módulo para verificar requisitos adicionais.

---

## 📥 Clonando o repositório

Clone o repositório e acesse a pasta do projeto:

```bash
git clone https://github.com/Caradophp/SmartRoute.git
cd SmartRoute
```

---

## 📦 Instalando as dependências

### 🎨 Frontend

Acesse o diretório do frontend:

```bash
cd frontend/smartRoute
```

Instale as dependências utilizando **npm**:

```bash
npm install
```

Se preferir utilizar **Yarn**, execute:

```bash
yarn install
```

### ⚙️ Backend

Acesse o diretório do backend a partir da raiz do projeto:

```bash
cd backend/smartRoute
```

Compile o projeto e instale as dependências com **Maven**:

```bash
mvn clean install
```

> 💡 **Dica:** Certifique-se de que o Java 21 ou superior e o Maven estejam instalados e configurados corretamente no ambiente.

---

## ▶️ Executando o projeto

O frontend e o backend devem ser iniciados separadamente, em terminais diferentes.

### 🎨 Frontend

No diretório `frontend/smartRoute`, execute:

```bash
npm run dev
```

O servidor de desenvolvimento será iniciado conforme a configuração do projeto.

### ⚙️ Backend

No diretório `backend/smartRoute`, execute:

```bash
mvn spring-boot:run
```

O Spring Boot iniciará a aplicação backend conforme as configurações do projeto.

> ⚠️ **Importante:** Verifique se as variáveis de ambiente, as configurações do banco de dados e os demais serviços necessários estão configurados antes de iniciar a aplicação.

---

## 🐳 Docker

Se o projeto possuir uma configuração válida do Docker Compose, você poderá utilizar o Docker para construir as imagens e iniciar os serviços.

Execute o comando a partir do diretório que contém o arquivo `docker-compose.yml`:

```bash
docker-compose up --build
```

Para executar os serviços em segundo plano, utilize:

```bash
docker-compose up --build -d
```

Para interromper os serviços, execute:

```bash
docker-compose down
```

> ℹ️ **Nota:** Os comandos acima dependem da existência de um arquivo de configuração do Docker Compose e da definição dos serviços necessários no projeto.

---

## 📚 Documentação

Para obter informações detalhadas sobre arquitetura, configuração e implementação, consulte a documentação específica de cada módulo.

| Arquivo | Descrição |
| :--- | :--- |
| `backend/BANCO.md` | Configuração e informações sobre o banco de dados |
| `backend/BACKEND.md` | Configuração, arquitetura e execução do backend |
| `frontend/FRONTEND.md` | Configuração, instalação e execução do frontend |
| `mobile/MOBILE.md` | Configuração e execução do aplicativo mobile |

> 📌 **Observação:** Os caminhos acima são relativos à raiz do repositório. Caso algum arquivo não esteja disponível, procure outros arquivos `.md` na raiz do projeto ou no diretório `docs`.

---

## 🤝 Contribuição e suporte

Encontrou algum problema durante a instalação ou execução do SmartRoute? Confira as opções abaixo:

- 🐛 **Reportar problemas:** Abra uma Issue no GitHub.
- 📖 **Consultar a documentação:** Verifique os arquivos específicos de cada módulo.
- 💬 **Obter suporte:** Entre em contato com os mantenedores indicados na documentação do projeto.

### Como contribuir

1. Faça um *fork* do repositório.
2. Crie uma *branch* para suas alterações (`git checkout -b feature/minha-feature`).
3. Implemente e teste suas modificações.
4. Envie suas alterações por meio de um *Pull Request*.

---

## 🚀 Sobre o SmartRoute

Obrigado pelo interesse em contribuir com o **SmartRoute**!  
Esperamos que este guia facilite a configuração e a execução do projeto.

**Boa execução e bom desenvolvimento! 🎉**
