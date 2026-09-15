# SmartRoute Mobile - Setup com Expo

Este guia explica como preparar o ambiente para rodar o app mobile do projeto SmartRoute usando Expo no Android.

## 1. Requisitos

Antes de começar, verifique se você tem instalado:

- Node.js 18+ recomendado
- npm ou yarn
- Git
- Android Studio
- Java JDK 21 ou superior
- Android SDK

## 2. Instalar o Node.js e o npm

Se ainda não tiver o Node.js instalado, baixe a versão LTS em:

- https://nodejs.org/

Depois confirme a instalação:

```bash
node -v
npm -v
```

Se tudo estiver certo, continue.

## 3. Instalar o Java JDK

O Android Studio geralmente pede o JDK para compilar projetos Android.

Instale o JDK 17, por exemplo:

- Ubuntu/Debian:

```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

- macOS com Homebrew:

```bash
brew install openjdk@21
```

- Windows: baixe do site da Oracle ou Adoptium e siga a instalação.

Confirme:

```bash
java -version
```

## 4. Instalar o Android Studio

Baixe e instale o Android Studio:

- https://developer.android.com/studio

Durante a instalação, marque as opções:

- Android SDK
- Android SDK Platform
- Android Virtual Device (AVD)
- Intel HAXM / Android Emulator Hypervisor Driver (quando disponível)

## 5. Configurar o Android SDK

Após instalar o Android Studio:

1. Abra o Android Studio
2. Vá em "More Actions" > "SDK Manager"
3. Na aba "SDK Platforms", instale:
   - Android 13 (Tiramisu) ou a versão mais recente suportada
4. Na aba "SDK Tools", instale:
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
   - Android Emulator
   - Intel x86 Emulator Accelerator (HAXM) ou Android Emulator Hypervisor Driver

## 6. Configurar as variáveis de ambiente

### Linux/macOS

Abra o terminal e edite o arquivo de perfil:

```bash
nano ~/.bashrc
# ou ~/.zshrc se estiver usando zsh
```

Adicione:

```bash
export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/emulator
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/tools/bin
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

Depois recarregue o terminal:

```bash
source ~/.bashrc
```

### Windows

Adicione no Path as pastas:

- C:\Users\SEU_USUARIO\AppData\Local\Android\Sdk\platform-tools
- C:\Users\SEU_USUARIO\AppData\Local\Android\Sdk\emulator

Também configure a variável:

```powershell
ANDROID_HOME=C:\Users\SEU_USUARIO\AppData\Local\Android\Sdk
```

## 7. Criar ou abrir um emulador Android

No Android Studio:

1. Vá em "Device Manager"
2. Clique em "Create Device"
3. Escolha um modelo de celular
4. Selecione uma imagem do sistema (x86_64 ou arm64)
5. Clique em "Next" e depois em "Finish"

Depois inicie o emulador.

Você também pode verificar se o Android está acessível pelo ADB:

```bash
adb devices
```

Se o emulador aparecer como device, o ambiente está funcionando.

## 8. Acessar o projeto

Entre na pasta do app mobile:

```bash
cd /home/luciano/faculdade/SmartRoute/mobile
```

## 9. Instalar dependências

Dentro da pasta do app, execute:

```bash
npm install
```

Se o projeto usa Expo, também pode ser necessário instalar o Expo CLI globalmente:

```bash
npm install -g expo-cli
```

Mas em projetos modernos, muitas vezes basta usar:

```bash
npx expo start
```

## 10. Rodar o app no emulador

### Opção 1: Usando Expo Go

Inicie o Metro Bundler:

```bash
npx expo start
```

Depois:

- Abra o app Expo Go no Android
- Escaneie o QR code exibido no terminal
- O app será carregado no emulador ou no aparelho conectado

### Opção 2: Rodar diretamente no emulador Android

Se quiser abrir diretamente no emulador:

```bash
npx expo start --android
```

Isso inicia o app no emulador configurado pelo Android Studio.

### Opção 3: Rodar em modo nativo (mais avançado)

Se o projeto tiver suporte a build nativa:

```bash
npx expo run:android
```

Esse comando pode instalar o app no emulador ou no dispositivo conectado e compilar o código nativo.

## 11. Se o app não abrir

Verifique estes pontos:

- O emulador está ligado e funcionando
- ADB está acessível:

```bash
adb devices
```

- O projeto foi instalado corretamente:

```bash
npm install
```

- O Java e o Android SDK estão configurados corretamente
- O computador tem recursos suficientes para iniciar o emulador

## 12. Comandos úteis

```bash
# iniciar o app
npx expo start

# iniciar no Android
npx expo start --android

# verificar dispositivos conectados
adb devices

# limpar cache do Expo
npx expo start --clear
```

## 13. Dicas finais

- Use um emulador com arquitetura compatível com o ambiente do seu computador
- Mantenha o Android Studio e o SDK atualizados
- Se o projeto for muito grande, rode o Metro em um terminal separado e use o emulador já aberto
- Para desenvolvimento mais ágil, prefira usar o Expo Go quando possível

## 14. Resumo rápido

Fluxo básico:

```bash
cd /home/luciano/faculdade/SmartRoute/mobile/smartRoute
npm install
npx expo start --android
```

Se o emulador estiver funcionando e o ambiente estiver configurado corretamente, o app deve abrir em poucos segundos.

Se quiser, posso também criar uma versão deste guia com comandos específicos para Linux, Windows ou macOS.
