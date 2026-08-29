# Documentação para rodar o frontend do SmartRoute

Esta documentação foi criada para facilitar a execução do projeto Vue.js pelo grupo, sem precisar conhecer detalhes da estrutura do projeto.

## Requisitos

Antes de rodar o projeto, verifique se o seu computador tem instalado:

- Node.js 18 ou superior
- npm (vem junto com o Node.js)
- Git
- Um navegador moderno (Chrome, Edge, Firefox)

Para verificar se tudo está instalado:

```bash
node -v
npm -v
```

Se os comandos retornarem versões, a instalação está correta.

## 1) Entrar na pasta do frontend

No terminal, navegue até a pasta do projeto:

```bash
cd /home/luciano/faculdade/SmartRoute/frontend/smartRoute
```

## 2) Instalar as dependências

Dentro da pasta do frontend, execute:

```bash
npm install
```

Esse comando instala todas as bibliotecas e pacotes necessários para o projeto funcionar.

Se houver arquivo `package-lock.json`, pode usar também:

```bash
npm ci
```

## 3) Rodar o projeto localmente

Para iniciar o servidor de desenvolvimento:

```bash
npm run dev
```

Depois disso, o terminal mostrará uma URL semelhante a:

```bash
http://localhost:5173
```

Abra essa URL no navegador para visualizar o projeto.

Obs.: se a porta 5173 estiver ocupada, o Vite pode usar outra porta e mostrar o novo endereço no terminal.

## 4) Build para produção

Quando quiser gerar a versão pronta para deploy, rode:

```bash
npm run build
```

Isso cria a pasta `dist` com os arquivos compilados do projeto.

Para testar a build localmente:

```bash
npm run preview
```

## 5) Variáveis de ambiente

Se o projeto utiliza arquivos `.env`, crie uma cópia do exemplo (se existir):

```bash
cp .env.example .env
```

Edite o arquivo `.env` com as informações corretas, como API URL, porta do backend, etc.

## 6) Se ocorrer algum erro

Alguns erros comuns:

- `node_modules` não instalados: execute `npm install`
- Versão do Node antiga: atualize para Node 18+
- Porta em uso: o Vite pode escolher outra porta automaticamente
- Arquivo `.env` faltando: verifique se o projeto precisa de variáveis de ambiente

## 7) Comandos principais

```bash
npm install
npm run dev
npm run build
npm run preview
```

## Dica

Se estiver trabalhando em grupo, vale lembrar que cada pessoa deve rodar o projeto em sua máquina local, e caso o backend também esteja em execução, ele precisa estar acessível pela URL configurada no frontend.

Se quiser, posso também criar uma versão mais detalhada com instruções específicas para o backend e para o fluxo de desenvolvimento do grupo.
