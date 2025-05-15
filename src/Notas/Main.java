package Notas;

/**
 * Classe principal do sistema acadêmico
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Main {
    public static void main(String[] args) {
        // Cria a entrada
        Entrada io = new Entrada();

        // Cria o sistema para armazenar professores, alunos e turmas
        Sistema s = new Sistema();

        // Exibe o menu até que o usuário escolha a opção 0 (Sair)
        int op = io.menu();

        while (op != 0) {
            switch (op) {
                case 1:
                    io.cadProf(s); // Cadastra um novo professor
                    break;
                case 2:
                    io.cadAluno(s); // Cadastra um novo aluno
                    break;
                case 3:
                    io.cadTurma(s); // Cadastra uma nova turma
                    break;
                case 4:
                    s.listarTurmas(); // Lista todas as turmas cadastradas
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            // Mostra o menu novamente
            op = io.menu();
        }

        // Fecha o scanner
        io.input.close();

        // Mensagem de encerramento
        System.out.println("Sistema encerrado. Até mais!");
    }
}