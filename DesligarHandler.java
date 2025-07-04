import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DesligarHandler implements Runnable {
  private Server server;

  public DesligarHandler(Server server) {
    this.server = server;
  }

  @Override
  public void run() {
    BufferedReader inputConsole = new BufferedReader(new InputStreamReader(System.in));
    try {
      String comando;
      while ((comando = inputConsole.readLine()) != null) {
        if (comando.equalsIgnoreCase("/fechar")) {
          System.out.println("[SERVER] Encerrando Servidor...");
          server.fecharServerSocket();
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
