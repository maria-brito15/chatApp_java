import java.io.BufferedReader;
import java.io.IOException;

public class ReceberMensagemHandler implements Runnable {
  private BufferedReader input;

  public ReceberMensagemHandler(BufferedReader input) {
    this.input = input;
  }

  @Override
  public void run() {
    try {
      String resposta;

      while ((resposta = input.readLine()) != null) {
        if (resposta.startsWith("[" + Cliente.usernameUser + "]")) {
          resposta = resposta.replaceFirst("\\[" + Cliente.usernameUser + "\\]", "[VOCÊ]");
        }

        System.out.println("\n" + resposta);
        System.out.print("Mensagem: ");
      }
    } catch (IOException e) {
      System.out.println("Conexão Encerrada pelo Servidor.");
      e.printStackTrace();
    }
  }
};