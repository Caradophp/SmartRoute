SmartRoute

Guia rápido para configurar, executar e entender o projeto SmartRoute.

Este documento apresenta os pré-requisitos, instruções de instalação e execução, além de direcionar para a documentação detalhada de cada parte do projeto.

📋 Sumário

Pré-requisitos

Clonando o repositório

Instalando as dependências

Executando o projeto

Documentação

Contribuição e suporte

🔧 Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas:

Git

Node.js
 — versão LTS

npm ou Yarn

Java 21 ou superior

Docker
 — opcional

Observação: instale apenas as ferramentas necessárias para o stack que você pretende executar. Consulte a documentação específica de cada módulo para mais detalhes.

📥 Clonando o repositório

Clone o repositório e acesse a pasta do projeto:

git clone https://github.com/Caradophp/SmartRoute.git
cd SmartRoute

📦 Instalando as dependências
Frontend

Acesse o diretório do frontend:

cd frontend/smartRoute


Instale as dependências utilizando npm:

npm install


Ou, caso utilize Yarn:

yarn install

Backend

Acesse o diretório do backend:

cd backend/smartRoute


Compile o projeto e instale as dependências com Maven:

mvn clean install

▶️ Executando o projeto
Frontend

No diretório frontend/smartRoute, execute:

npm run dev

Backend

No diretório backend/smartRoute, execute:

mvn spring-boot:run

🐳 Docker

Caso o projeto esteja configurado para execução com Docker Compose:

docker-compose up --build


Nota: os comandos acima são baseados na estrutura atual do projeto. Consulte a documentação específica de cada módulo caso existam configurações ou comandos adicionais.

📚 Documentação

Para obter informações detalhadas sobre arquitetura, configuração e implementação, consulte os arquivos de documentação do projeto:

Arquivo	Descrição
backend/BANCO.md	Configuração e informações do banco de dados
backend/BACKEND.md	Configuração e execução do backend
frontend/FRONTEND.md	Configuração e execução do frontend
mobile/MOBILE.md	Configuração e execução do aplicativo mobile

Caso algum dos arquivos acima não esteja disponível, procure por outros arquivos .md na raiz do projeto ou dentro da pasta docs.

🤝 Contribuição e suporte

Encontrou algum problema durante a instalação ou execução?

Abra uma Issue no repositório;

Consulte os arquivos de documentação específicos de cada módulo;

Entre em contato com os mantenedores indicados na documentação do projeto.

🚀 SmartRoute

Obrigado por contribuir com o projeto!

Boa execução!
