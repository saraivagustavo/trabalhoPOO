// Main.java
package Notas;

import java.io.IOException;

/**
 * Classe principal do sistema acadêmico
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Main {
    private static final String DATA_FILE_NAME = "dados.txt"; // Arquivo de SAÍDA e ENTRADA de persistência

    public static void main(String[] args) {
        Entrada io = new Entrada(); // Tenta ler input.txt, senão System.in para interação
        Sistema s = new Sistema();

        // Faz o pré-carregamento dos dados salvos no dados.txt ao iniciar o código.
        // Este é um requisito do professor.
        s.carregarSistema(DATA_FILE_NAME, io);

        System.out.println("\n--- Sistema pronto para interação ---");

        int op = -1; // A opção agora é um inteiro
        try {
            while (op != 0) { // O loop continua até a opção 0 (Sair)
                op = io.menu(); // io.menu() agora lê e retorna a opção numérica
                switch (op) {
                    case 1: // Cadastrar professor
                        io.cadProf(s);
                        break;
                    case 2: // Cadastrar aluno
                        io.cadAluno(s);
                        break;
                    case 3: // Cadastrar turma
                        io.cadTurma(s);
                        break;
                    case 4: // Listar turmas
                        s.listarTurmas();
                        break;
                    case 0: // Sair
                        System.out.println("Saindo do sistema...");
                        // A listagem final já é feita no finally
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
                // Salva o sistema após cada operação de cadastro (e também se o arquivo input.txt tiver sido lido)
                if (op >=1 && op <= 3) { // Salva após cadastrar professor, aluno ou turma
                    try {
                        s.salvarSistema(DATA_FILE_NAME, io);
                        System.out.println("Alterações salvas em '" + DATA_FILE_NAME + "'.");
                    } catch (IOException e) {
                        System.err.println("Erro ao salvar alterações no arquivo: " + e.getMessage());
                    }
                }
            }
        } catch (IllegalStateException e) {
            // Este catch é para quando o input.txt termina ou um sinal de EOF é recebido
            System.out.println("Fim da entrada de dados (o programa pode estar lendo de 'input.txt' ou um sinal de EOF foi recebido): " + e.getMessage());
            // No caso de EOF, listamos as turmas uma última vez antes de encerrar
            s.listarTurmas(); // Exibe listagem final mesmo em EOF
        } catch (IllegalArgumentException e) { // Captura erros de formato de input.txt na opção do menu
            System.err.println("Erro na leitura da opção do menu: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema: " + e.getMessage());
            e.printStackTrace(); // Para depuração
        } finally {
            io.input.close(); // Garante que o scanner seja fechado

            // Salva o sistema uma última vez ao sair, garantindo que o estado final seja persistido
            try {
                s.salvarSistema(DATA_FILE_NAME, io);
                System.out.println("Estado final do sistema salvo em '" + DATA_FILE_NAME + "'.");
            } catch (IOException e) {
                System.err.println("Erro ao salvar o estado final do sistema: " + e.getMessage());
            }
            System.out.println("Sistema encerrado. Até mais!");
        }
    }
}