import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

// essa classe está implementando a interface Runnable
// Runnable é uma interface do Java que define um contrato: ela exige que você escreva o método run()
// isso significa que a classe ClientHandler pode ser executada em uma thread separada, porque o método run() é o código que a thread executa.
// o implements runnable é necessário porque você quer que a classe possa ser executada em uma thread.
public class ClientHandler implements Runnable {
  private Socket socket;
  private BufferedReader input;
  private PrintWriter output;
  private String usernameUser;
  private List<ClientHandler> clients;

  // construtor
  public ClientHandler(Socket socket, String usernameUser, List<ClientHandler> clients) {
    this.socket = socket;
    this.usernameUser = usernameUser;
    this.clients = clients;

    try {
      // input -> msg do cliente
      // output -> enviar msgs ao cliente (server)
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  };

  // envia a msg pra todos no servidor exceto o que enviou a msg
  private void broadcastMensagem(String msg) {
    for (ClientHandler client : clients) {
      if (client != this) { 
        client.output.println(msg);
      }
    }
  };

  @Override
  public void run() {
    try {
      String msg;

      while ((msg = input.readLine()) != null) {
        System.out.println("[" + usernameUser + "]: " + msg);

        if (msg.equalsIgnoreCase("/sair")) {
          // output -> cliente
          output.println("Você Saiu do Servidor.");
          // system -> servidor
          System.out.println("[SERVER] " + usernameUser + " Saiu do Servidor.");
          break;
        } else if (msg.equalsIgnoreCase("/ajuda")) {
          // System é as mensagens que aparece para o servidor
          System.out.println("[SERVER] " + usernameUser + " pediu ajuda.");
          // output é msg enviadas pelo servidor para o usuario
          output.println("Comandos Disponíveis:");
          output.println("/ajuda - Mostra Esta Mensagem de Ajuda");
          output.println("/sair - Sair do Chat");
          continue;
        } else {
          broadcastMensagem("[" + usernameUser + "]: " + msg);
        }
      }
    } catch (IOException e) {
      // mostra o caminho exato no código onde o erro ocorreu
      e.printStackTrace();
      // o bloco finally sempre sera executado 
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  };
};