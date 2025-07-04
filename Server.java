import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  // declaração de atributo do tipo ServerSocket - é uma classe do pacote java.net que é usada pra escutar as conexões em uma porta especifica
  private ServerSocket serverSocket;
  // lista de clientes do servidor
  private static List<ClientHandler> clients = new ArrayList<>();
  
  // construtor da classe server, recebe um parametro so tipo ServerSocket
  // recebe uma porta (exemplo 1234) e o Server guarda pra usar depois
  // this aponta pro atributo privado que ta sendo setado
  public Server(ServerSocket serverSocket) {
    this.serverSocket = serverSocket;
  };

  // função pra iniciar o servidor
  public void iniciarServidor() {
    System.out.println("[SERVER] Servidor Iniciado, Esperando Conexões...");

    try {
      // enquanto as conexões da porta não estiverem fechadas
      while (!serverSocket.isClosed()) {
        Socket socket;
        socket = serverSocket.accept();

        // socket.getInputStream() -> é o fluxo de bytes que vem da conexão da rede, o que o cliente enviou pela rede
        // new InputStreamReader -> pega os bytes e transforma em caracteres legiveis
        // new BufferedReader -> responsavel por ler os caracteres e oferecer os metodos tipo readLine()
        BufferedReader input;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String usernameUser;
        usernameUser = input.readLine();

        System.out.println("Novo Cliente Conectado! (Username: " + usernameUser + "-" + socket.getInetAddress() + ")");
      
        // criar um objeto da classe ClientHandler
        // passa o socket da conexão como construtor da classe 
        // new ClientHandler(socket) -> cria o manipulador para o cliente
        ClientHandler clientHandler;
        clientHandler = new ClientHandler(socket, usernameUser, clients);
        // adiciona o cliente na lista pra poder enviar msg pra todos os clientes
        clients.add(clientHandler);

        // thread = linha de execução paralela
        // passa esse objeto como construtor do thread
        // new Thread(clientHandler) -> cria uma thread para esse manipulador
        Thread thread;
        thread = new Thread(clientHandler);

        // ao iniciar a thread, o codigo do run() do clientHandler roda em paralelo
        // isso permite que o servidor continue aceitando novas conexões sem ficar travado esperando que essa comunicação termine
        // inicia a thread para rodar o cliente
        thread.start(); // Aqui o método run() do ClientHandler vai rodar em paralelo.
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  // responsavel por fechar o serversocket -> para de escutar novas conexões na porta
  // não recebe mais clientes
  public void fecharServerSocket() {
    try {
      // antes de tentar fechar, ele verifica se o objeto serverSocket existe (não é null), para evitar erros
      if (serverSocket != null) {
        // chama o método close() do ServerSocket, que libera a porta que estava sendo usada e encerra a escuta por conexões
        serverSocket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  public void escutarComandoDesligar() {
    DesligarHandler desligaHandler  = new DesligarHandler(this);
    Thread desligarThread = new Thread(desligaHandler);

    desligarThread.start();
  }

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(18126)) {
      Server server = new Server(serverSocket);

      server.escutarComandoDesligar();
      server.iniciarServidor();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
};