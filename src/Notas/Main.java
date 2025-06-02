// Main.java
package Notas;

/**
 * Classe principal do sistema acadêmico
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Main {
    public static void main(String[] args) {
        // Cria a entrada e o sistema
        Entrada io = new Entrada();
        Sistema s = new Sistema();

        // Exibe o menu até que o usuário escolha a opção 0 (Sair)
        int op = -1; // Inicializa com um valor que não seja 0
        try {
            while (op != 0) {
                op = io.menu(); // Chama o menu
                switch (op) {
                    case 1:
                        io.cadProf(s);
                        break;
                    case 2:
                        io.cadAluno(s);
                        break;
                    case 3:
                        io.cadTurma(s);
                        break;
                    case 4:
                        s.listarTurmas();
                        break;
                    case 0: // Opção de sair
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema: " + e.getMessage());
        }

            // Fecha o scanner
            io.input.close(); // Garante que o scanner seja fechado, mesmo se ocorrer um erro

            // Mensagem de encerramento
            System.out.println("Sistema encerrado. Até mais!");
        }
}
