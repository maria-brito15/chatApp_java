# ChatApp Server Java

Um sistema de chat multiusuário desenvolvido em Java utilizando sockets TCP para comunicação em rede. O projeto implementa um servidor capaz de gerenciar múltiplos clientes conectados simultaneamente através de threads.

> 🎯 **Primeiro Projeto em Java** - Este é meu primeiro projeto desenvolvido em Java, explorando conceitos fundamentais como programação orientada a objetos, networking com sockets e programação concorrente com threads.

## 📋 Funcionalidades

### Servidor
- Aceita múltiplas conexões simultâneas
- Gerencia clientes através de threads individuais
- Transmite mensagens para todos os clientes conectados (broadcast)
- Comando `/fechar` para encerrar o servidor
- Log de atividades e conexões

### Cliente
- Conecta-se ao servidor via socket TCP
- Interface de linha de comando para envio de mensagens
- Recebe mensagens de outros usuários em tempo real
- Comandos especiais disponíveis

## 🛠️ Comandos Disponíveis

### Para Clientes
- `/ajuda` - Exibe a lista de comandos disponíveis
- `/sair` - Desconecta do servidor

### Para Servidor
- `/fechar` - Encerra o servidor e todas as conexões

## 🚀 Como Executar

### 1. Compilar o Projeto
```bash
javac *.java
```

### 2. Iniciar o Servidor
```bash
java Server
```
O servidor será iniciado na porta `18126` e ficará aguardando conexões.

### 3. Conectar Clientes
Em terminais separados, execute:
```bash
java Cliente
```
Cada cliente será solicitado a inserir um nome de usuário.

## 📁 Estrutura do Projeto

```
├── Server.java              # Classe principal do servidor
├── Cliente.java             # Classe principal do cliente
├── ClientHandler.java       # Manipulador de clientes no servidor
├── ReceberMensagemHandler.java # Manipulador de mensagens recebidas no cliente
└── DesligarHandler.java     # Manipulador de comando de desligamento do servidor
```

## 🔧 Arquitetura

### Servidor (`Server.java`)
- Escuta na porta 18126
- Aceita conexões de múltiplos clientes
- Cria uma thread para cada cliente conectado
- Mantém lista de todos os clientes conectados

### Cliente (`Cliente.java`)
- Conecta-se ao servidor via socket TCP
- Gerencia entrada do usuário e recepção de mensagens
- Utiliza threads para operações simultâneas

### Manipuladores
- **ClientHandler**: Gerencia comunicação individual com cada cliente
- **ReceberMensagemHandler**: Processa mensagens recebidas no cliente
- **DesligarHandler**: Monitora comandos de encerramento do servidor

## 📊 Fluxo de Comunicação

1. **Conexão**: Cliente conecta-se ao servidor e envia nome de usuário
2. **Autenticação**: Servidor registra o cliente e cria thread dedicada
3. **Comunicação**: Mensagens são transmitidas entre clientes via servidor
4. **Broadcast**: Servidor retransmite mensagens para todos os clientes conectados
5. **Desconexão**: Cliente pode sair usando `/sair` ou servidor pode encerrar com `/fechar`

## 🔌 Configuração de Rede

- **Host**: localhost (127.0.0.1)
- **Porta**: 18126
- **Protocolo**: TCP

Para usar em rede local, altere o hostname no `Cliente.java`:
```java
String hostname = "IP_DO_SERVIDOR";
```

## 🎯 Recursos Técnicos

- **Multithreading**: Cada cliente é gerenciado por uma thread separada
- **Socket TCP**: Comunicação confiável entre cliente e servidor
- **BufferedReader/PrintWriter**: Manipulação eficiente de streams
- **Broadcast**: Distribuição de mensagens para todos os clientes

## 📝 Exemplo de Uso

```
# Terminal 1 - Servidor
$ java Server
[SERVER] Servidor Iniciado, Esperando Conexões...
Novo Cliente Conectado! (Username: João-/127.0.0.1)
[João]: Olá pessoal!

# Terminal 2 - Cliente 1
$ java Cliente
Conectado ao Servidor!
Nome de Usuario: João

Mensagem: Olá pessoal!
[VOCÊ]: Olá pessoal!
[Maria]: Oi João!

# Terminal 3 - Cliente 2
$ java Cliente
Conectado ao Servidor!
Nome de Usuario: Maria

[João]: Olá pessoal!
Mensagem: Oi João!
[VOCÊ]: Oi João!
```

## 🛡️ Tratamento de Erros

O sistema inclui tratamento para:
- Perda de conexão com o servidor
- Encerramento inesperado de clientes
- Erros de I/O na comunicação de rede
- Comandos inválidos

## 📚 Tecnologias Utilizadas

- **Java SE**: Linguagem principal
- **Java Socket API**: Comunicação de rede
- **Java Threading**: Programação concorrente
- **Java I/O**: Manipulação de streams
