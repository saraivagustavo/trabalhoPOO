// Main.java
package Notas;

import java.io.IOException;

/**
 * Classe principal do sistema acadêmico
 * @author Hilario Seibel Junior, Gustavo Saraira Mariano e Pedro Henrique Albani Nunes
 */
public class Main {
    private static final String DATA_FILE_NAME = "dados.txt"; // Arquivo de SAÍDA e ENTRADA de comandos/persistência

    public static void main(String[] args) {
        Entrada io = new Entrada(); // Tenta ler dados.txt, senão System.in para interação
        Sistema s = new Sistema();

        // O carregamento do sistema agora é feito através dos comandos lidos no loop principal,
        // seja de dados.txt ou do teclado. O método carregarSistema() anterior não é mais necessário aqui.

        System.out.println("\n--- Sistema pronto para interação ---");

        String comando = ""; // A opção agora é uma String (comando/tag)
        try {
            while (!comando.equalsIgnoreCase("FIM")) { // O loop continua até o comando "FIM"
                comando = io.menu(); // io.menu() agora lê e retorna a tag/comando
                switch (comando) {
                    case "PROF":
                        io.cadProf(s);
                        break;
                    case "ALU":
                        io.cadAluno(s);
                        break;
                    case "TUR":
                        io.cadTurma(s);
                        break;
                    case "LISTAR": // Nova opção para listar as turmas a qualquer momento
                        s.listarTurmas();
                        break;
                    case "FIM":
                        System.out.println("Comando 'FIM' recebido. Listando informações finais antes de sair...");
                        s.listarTurmas(); // Lista as turmas uma última vez antes de sair
                        break;
                    default:
                        System.out.println("Comando inválido. Tente novamente.");
                        break;
                }
            }
        } catch (IllegalStateException e) {
            // Este catch é para quando o dados.txt termina inesperadamente (EOF) ou um sinal de EOF é recebido no teclado
            System.out.println("Fim da entrada de dados (o programa pode estar lendo de 'dados.txt' ou um sinal de EOF foi recebido): " + e.getMessage());
            // No caso de EOF, listamos as turmas uma última vez antes de encerrar
            s.listarTurmas();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema: " + e.getMessage());
            e.printStackTrace(); // Para depuração
        } finally {
            io.input.close(); // Garante que o scanner seja fechado
        }
    }
}