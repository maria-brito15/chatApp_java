import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
  // deixando o usernameUser global pra poder ser usado globalmente na hora do replace em receberMensagem
  public static String usernameUser;
  public static void main(String[] args) {
    String hostname = "localhost";
    int port = 18126;

    try (Socket socket = new Socket(hostname, port)) {
      System.out.println("Conectado ao Servidor!");

      // leitor do que o usuario digita no terminal
      // System.in -> fluxo de entrada padrão (teclado)
      BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

      // leitor para o que chega pela conexão de rede
      // fluxo de dados que o servidor está enviando
      // o cliente pode ler mensagens que o servidor enviou
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // envia mensagens ao servidor pela rede (socket)
      // escritor para enviar dados pela rede para o servidor
      // autoflush: cada vez que você usa println(), ele envia os dados imediatamente (sem ficar acumulando no buffer)
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

      System.out.print("Nome de Usuario: ");
      usernameUser = console.readLine();
      output.println(usernameUser);

      ReceberMensagemHandler receberHandler = new ReceberMensagemHandler(input);
      Thread receberThread  = new Thread(receberHandler);
      receberThread.start();

      String mensagem;
      while (true) {
          System.out.print("Mensagem: ");
          mensagem = console.readLine();

          if (mensagem == null) {
              break;
          }

          output.println(mensagem);

          if (mensagem.equalsIgnoreCase("/sair")) {
              break;
          } else if (mensagem.equalsIgnoreCase("/ajuda")) {
              continue;
          } else {
              // mostra sua mensagem no mesmo formato que o servidor
              System.out.println("[" + usernameUser + "]: " + mensagem);
          }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
