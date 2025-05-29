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
        int op = io.menu();

        while (op != 0) {
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
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            // Mostra o menu novamente
            op = io.menu();
        }

        // Antes de encerrar, pode listar todos os dados cadastrados (opcional)
        System.out.println("\nResumo final dos cadastros:");
        s.listarProfs();
        System.out.println("\n");
        s.listarAlunos();
        System.out.println("\n");
        s.listarTurmas();

        // Fecha o scanner
        io.input.close();

        // Mensagem de encerramento
        System.out.println("Sistema encerrado. Até mais!");
    }
}