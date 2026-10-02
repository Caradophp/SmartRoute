# SmartRoute
Guia rápido para rodar o projeto e referência aos arquivos Markdown que explicam o projeto em detalhes.
1) Pré-requisitos
- Git
- Node.js (versão LTS) e npm ou yarn — se for projeto JavaScript/TypeScript
- Java 21 ou Superior
- Docker (opcional)
Observação: instale apenas as ferramentas necessárias ao stack do projeto (ver arquivos de documentação abaixo).
2) Clonar o repositório
```bash
git clone https://github.com/Caradophp/SmartRoute.git
cd SmartRoute
```
3) Instalação das dependências
- Configuração do frontend:
```bash
cd frontend/smartRoute

npm install
# ou
yarn install
```
- Configuração do Java:
```bash
cd backend/smartRoute

mvn clean install
```

4) Como rodar
- Desenvolvimento (exemplos genéricos):
```bash
# Para frontend
npm run dev

# Para backend
mvn spring-boot:run

# Usando Docker
docker-compose up --build

5) Documentação detalhada
Consulte os outros arquivos Markdown do projeto para entender arquitetura, uso e detalhes de implementação:
- ./backend/BANCO.md — Configuração do banco de dados
- ./backend/BACKEND.md — Configuração do backend
- ./frontend/FRONTEND.md — Configuração do frontend
- ./mobile/MOBILE.md — Configuração do aplicativo mobile

Se algum desses arquivos não existir, procure por arquivos .md na raiz ou na pasta docs para a documentação específica do projeto.
6) Contatos
Para dúvidas sobre execução, abra uma issue no repositório ou contate os mantenedores listados nos arquivos de documentação.

Boa execução!
# SmartRoute

