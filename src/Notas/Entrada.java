package Notas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Entrada {
    public Scanner input;

    /**
     * Construtor da classe Entrada
     * Se houver um arquivo input.txt, define que o Scanner vai ler deste arquivo.
     * Se o arquivo não existir, define que o Scanner vai ler da entrada padrão (teclado)
     */
    public Entrada() {
        try {
            this.input = new Scanner(new FileInputStream("input.txt"));
        } catch (FileNotFoundException e) {
            this.input = new Scanner(System.in);
        }
    }

    // *******************************************************
    // *************** MÉTODOS PRIVADOS GERAIS ****************
    // *******************************************************

    /**
     * Faz a leitura de uma linha inteira
     * Ignora linhas começando com #, que vão indicar comentários no arquivo de entrada:
     * @param msg: Mensagem que será exibida ao usuário
     * @return Uma String contendo a linha que foi lida
     */
    private String lerLinha(String msg) {
        System.out.print(msg);
        String linha = this.input.nextLine();

        while (linha.charAt(0) == '#') linha = this.input.nextLine();
        return linha;
    }

    /**
     * Faz a leitura de um número inteiro
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        String linha = this.lerLinha(msg);
        return Integer.parseInt(linha);
    }

    /**
     * Faz a leitura de um double
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        String linha = this.lerLinha(msg);
        return Double.parseDouble(linha);
    }

    // *******************************************************
    // ************* MÉTODOS DO MENU E OPÇÕES ***************
    // *******************************************************

    /**
     * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
     * @return Inteiro contendo a opção escolhida pelo usuário
     */
    public int menu() {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor:\n" +
                "2) Cadastrar aluno:\n" +
                "3) Cadastrar turma:\n" +
                "4) Listar turmas:\n" +
                "0) Sair\n";

        int op = this.lerInteiro(msg);

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    // *******************************************************
    // *************** MÉTODOS DE CADASTRO *******************
    // *******************************************************

    /**
     * Lê os dados de um novo Professor e cadastra-o no sistema.
     * @param s: Um objeto da classe Sistema
     */
    public void cadProf(Sistema s) {
        s.listarProfs();

        String nome = this.lerLinha("Digite o nome do professor: ");
        String cpf = this.lerLinha("Digite o cpf do professor: ");
        double salario = this.lerDouble("Digite o salário do professor: R$");

        if (s.encontrarProfessor(cpf) == null) { // Garantindo que o CPF não esteja duplicado.
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
        } else {
            System.out.println("Erro: CPF duplicado. Professor não adicionado.");
        }
    }

    public void cadAluno(Sistema s) {
        s.listarAlunos();

        String nome = this.lerLinha("Digite o nome do aluno: ");
        String cpf = this.lerLinha("Digite o cpf do aluno: ");
        String matricula = this.lerLinha("Digite a matrícula do aluno: ");

        if (s.encontrarAluno(matricula) == null) { // Garantindo que a matrícula não esteja duplicada.
            Aluno a = new Aluno(nome, cpf, matricula);
            s.novoAluno(a);
        } else {
            System.out.println("Erro: Matrícula duplicada. Aluno não adicionado.");
        }
    }

    public void cadTurma(Sistema s) {
        s.listarTurmas();

        String nome = this.lerLinha("Digite o nome da disciplina: ");
        int ano = this.lerInteiro("Digite o ano da disciplina: ");
        int semestre = this.lerInteiro("Digite o semestre da disciplina: ");
        String cpfProfessor = this.lerLinha("Digite o CPF do professor: ");

        Professor prof = s.encontrarProfessor(cpfProfessor);

        if (prof != null) {
            // Ler alunos
            Aluno[] alunos = this.lerAlunos(s);

            // Ler avaliações
            int qtdAvaliacoes = this.lerInteiro("Digite a quantidade de avaliações na disciplina: ");
            Avaliacao[] avaliacoes = this.lerAvaliacoes(s, alunos, qtdAvaliacoes);

            // Criar a turma e adicionar ao sistema
            Turma t = new Turma(nome, ano, semestre, prof, alunos, avaliacoes);
            s.novaTurma(t);
        } else {
            System.out.println("Erro: CPF do professor não encontrado. Turma não adicionada.");
        }
    }

    // *******************************************************
    // ************ MÉTODOS AUXILIARES DE LEITURA ************
    // *******************************************************

    public Professor lerProf(Sistema s) {
        String cpf = this.lerLinha("Digite o CPF do professor: ");
        return s.encontrarProfessor(cpf);
    }

    public Aluno[] lerAlunos(Sistema s) {
        int qtd = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");
        Aluno[] alunos = new Aluno[qtd];

        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinha("Digite a matrícula do aluno: ");
            Aluno a = s.encontrarAluno(matricula);
            if (a != null) {
                alunos[i] = a;
            } else {
                System.out.println("Aluno não encontrado.");
                i--; // tentar novamente
            }
        }

        return alunos;
    }

    public AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) {
        AlunoProva ap = new AlunoProva(a);

        System.out.println("Notas de " + a.getNome() + ":");
        for (int i = 0; i < nQuestoes; i++) {
            double nota = this.lerDouble("Nota na questão " + (i + 1) + ": ");
            ap.adicionarNota(nota);
        }

        return ap;
    }

    public Prova lerProva(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Informe o nome desta prova: ");
        int dia = this.lerInteiro("Digite o dia da prova: ");
        int mes = this.lerInteiro("Digite o mês da prova: ");
        int ano = this.lerInteiro("Digite o ano da prova: ");
        Data data = new Data(dia, mes, ano);
        double valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int nQuestoes = this.lerInteiro("Digite o número de questões: ");

        Prova p = new Prova(nome, data, valor, nQuestoes);

        for (Aluno aluno : alunos) {
            AlunoProva ap = this.lerAlunoProva(s, aluno, nQuestoes);
            p.adicionarAlunoProva(ap);
        }

        return p;
    }

    public GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        GrupoTrabalho grupo = new GrupoTrabalho();

        int qtd = this.lerInteiro("Digite o número de alunos neste grupo: ");
        for (int i = 0; i < qtd; i++) {
            String cpf = this.lerLinha("Digite o CPF do aluno: ");
            Aluno a = s.encontrarAluno(cpf);
            if (a != null) {
                grupo.adicionarAluno(a);
            } else {
                System.out.println("Aluno não encontrado.");
                i--;
            }
        }

        double nota = this.lerDouble("Digite a nota do grupo: ");
        grupo.setNota(nota);

        return grupo;
    }

    public Trabalho lerTrabalho(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Informe o nome deste trabalho: ");
        int dia = this.lerInteiro("Digite o dia do trabalho: ");
        int mes = this.lerInteiro("Digite o mês do trabalho: ");
        int ano = this.lerInteiro("Digite o ano do trabalho: ");
        Data data = new Data(dia, mes, ano);
        double valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
        int nIntegrantes = this.lerInteiro("Digite o número máximo de integrantes: ");
        int nGrupos = this.lerInteiro("Digite o número de grupos: ");

        Trabalho t = new Trabalho(nome, data, valor, nIntegrantes);

        for (int i = 0; i < nGrupos; i++) {
            GrupoTrabalho grupo = this.lerGrupoTrabalho(s);
            t.adicionarGrupo(grupo);
        }

        return t;
    }

    public Avaliacao[] lerAvaliacoes(Sistema s, Aluno[] alunos, int nAvaliacoes) {
        Avaliacao[] avaliacoes = new Avaliacao[nAvaliacoes];

        for (int i = 0; i < nAvaliacoes; i++) {
            System.out.println("Escolha um tipo de avaliação: 1) Prova 2) Trabalho");
            int tipo = this.lerInteiro("Opção: ");
            while (tipo < 1 || tipo > 2) {
                tipo = this.lerInteiro("Opção inválida. Digite 1 para Prova ou 2 para Trabalho: ");
            }

            if (tipo == 1) {
                avaliacoes[i] = this.lerProva(s, alunos);
            } else {
                avaliacoes[i] = this.lerTrabalho(s, alunos);
            }
        }

        return avaliacoes;
    }
}