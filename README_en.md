# ChatApp Server Java

A multi-user chat system developed in Java using TCP sockets for network communication. The project implements a server capable of managing multiple clients connected simultaneously through threads.

> 🎯 **First Java Project** - This is my first project developed in Java, exploring fundamental concepts such as object-oriented programming, networking with sockets, and concurrent programming with threads.

## 📋 Features

### Server
- Accepts multiple simultaneous connections
- Manages clients through individual threads
- Transmits messages to all connected clients (broadcast)
- `/fechar` command to shut down the server
- Activity and connection logging

### Client
- Connects to server via TCP socket
- Command-line interface for sending messages
- Receives messages from other users in real-time
- Special commands available

## 🛠️ Available Commands

### For Clients
- `/ajuda` - Displays the list of available commands
- `/sair` - Disconnects from the server

### For Server
- `/fechar` - Shuts down the server and all connections

## 🚀 How to Run

### 1. Compile the Project
```bash
javac *.java
```

### 2. Start the Server
```bash
java Server
```
The server will be started on port `18126` and will wait for connections.

### 3. Connect Clients
In separate terminals, run:
```bash
java Cliente
```
Each client will be prompted to enter a username.

## 📁 Project Structure

```
├── Server.java              # Main server class
├── Cliente.java             # Main client class
├── ClientHandler.java       # Client handler on the server
├── ReceberMensagemHandler.java # Received message handler on the client
└── DesligarHandler.java     # Server shutdown command handler
```

## 🔧 Architecture

### Server (`Server.java`)
- Listens on port 18126
- Accepts connections from multiple clients
- Creates a thread for each connected client
- Maintains a list of all connected clients

### Client (`Cliente.java`)
- Connects to server via TCP socket
- Manages user input and message reception
- Uses threads for simultaneous operations

### Handlers
- **ClientHandler**: Manages individual communication with each client
- **ReceberMensagemHandler**: Processes messages received on the client
- **DesligarHandler**: Monitors server shutdown commands

## 📊 Communication Flow

1. **Connection**: Client connects to server and sends username
2. **Authentication**: Server registers the client and creates dedicated thread
3. **Communication**: Messages are transmitted between clients via server
4. **Broadcast**: Server retransmits messages to all connected clients
5. **Disconnection**: Client can exit using `/sair` or server can shut down with `/fechar`

## 🔌 Network Configuration

- **Host**: localhost (127.0.0.1)
- **Port**: 18126
- **Protocol**: TCP

To use on local network, change the hostname in `Cliente.java`:
```java
String hostname = "IP_DO_SERVIDOR";
```

## 🎯 Technical Features

- **Multithreading**: Each client is managed by a separate thread
- **TCP Socket**: Reliable communication between client and server
- **BufferedReader/PrintWriter**: Efficient stream handling
- **Broadcast**: Message distribution to all clients

## 📝 Usage Example

```
# Terminal 1 - Server
$ java Server
[SERVER] Servidor Iniciado, Esperando Conexões...
Novo Cliente Conectado! (Username: João-/127.0.0.1)
[João]: Olá pessoal!

# Terminal 2 - Client 1
$ java Cliente
Conectado ao Servidor!
Nome de Usuario: João

Mensagem: Olá pessoal!
[VOCÊ]: Olá pessoal!
[Maria]: Oi João!

# Terminal 3 - Client 2
$ java Cliente
Conectado ao Servidor!
Nome de Usuario: Maria

[João]: Olá pessoal!
Mensagem: Oi João!
[VOCÊ]: Oi João!
```

## 🛡️ Error Handling

The system includes handling for:
- Loss of connection with the server
- Unexpected client shutdowns
- I/O errors in network communication
- Invalid commands

## 📚 Technologies Used

- **Java SE**: Main language
- **Java Socket API**: Network communication
- **Java Threading**: Concurrent programming
- **Java I/O**: Stream manipulation