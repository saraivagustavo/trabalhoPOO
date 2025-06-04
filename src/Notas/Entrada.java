package Notas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Entrada {
    private int contAluno = 0; // Contador de alunos
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
        try {
            System.out.print(msg);
            String linha = this.input.nextLine();

            while (linha.isEmpty() || linha.charAt(0) == '#') {
                if (linha.isEmpty()) {
                    System.out.println("Entrada vazia. Por favor, digite algo.");
                }
                System.out.print(msg);
                linha = this.input.nextLine();
            }
            return linha;
        } catch(InputMismatchException e){
            System.out.println("Input errado! Tente novamente.");
            return lerLinha(msg);
        } catch(StringIndexOutOfBoundsException e){
            System.out.println("Entrada vazia! Tente novamente.");
            return lerLinha(msg);
        }
    }


    /**
     * Faz a leitura de um número inteiro
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para int
     */
    private int lerInteiro(String msg) {
        try {
            String linha = this.lerLinha(msg);
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido! Tente novamente.");
            return lerInteiro(msg);
        }
    }

    /**
     * Faz a leitura de um double
     * @param msg: Mensagem que será exibida ao usuário
     * @return O número digitado pelo usuário convertido para double
     */
    private double lerDouble(String msg) {
        try {
            String linha = this.lerLinha(msg);
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido! Tente novamente.");
            return lerDouble(msg);
        }
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

        while (salario < 0) {
            System.out.println("Salário inválido. Deve ser um valor positivo.");
            salario = this.lerDouble("Digite o salário do professor: R$");
        }
        try {
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p); // Este método agora verifica duplicidade e lança IAE
            System.out.println("Professor cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
        }
    }

    public void cadAluno(Sistema s) {
        s.listarAlunos();

        String nome = this.lerLinha("Digite o nome do aluno: ");
        String cpf = this.lerLinha("Digite o cpf do aluno: ");
        String matricula = this.lerLinha("Digite a matrícula do aluno: ");

        try {
            Aluno a = new Aluno(nome, cpf, matricula);
            s.novoAluno(a);
            contAluno++; // Incrementa contador apenas se a conseguir adicionar o mano
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    public void cadTurma(Sistema s) {
        s.listarTurmas();

        String nome = this.lerLinha("Digite o nome da disciplina: ");
        int ano;
        int semestre;
        do {
            ano = this.lerInteiro("Digite o ano da disciplina: ");
            if (ano <= 0) {
                System.out.println("Ano inválido. Deve ser um valor positivo. Tente novamente.");
            }
        } while (ano <= 0);
        do {
            semestre = this.lerInteiro("Digite o semestre da disciplina (1 ou 2): ");
            if (semestre != 1 && semestre != 2) {
                System.out.println("Semestre inválido. Deve ser 1 ou 2. Tente novamente.");
            }
        } while (semestre != 1 && semestre != 2);

        String cpfProfessor = this.lerLinha("Digite o CPF do professor: ");
        Professor prof = s.encontrarProfessor(cpfProfessor);

        if (prof == null) {
            System.out.println("Erro: CPF do professor não encontrado. Turma não adicionada.");
            return; // Sai se o professor não for encontrado
        }

        try {
            Aluno[] alunos = this.lerAlunos(s);
            int qtdAvaliacoes;
            do {
                qtdAvaliacoes = this.lerInteiro("Digite a quantidade de avaliações na disciplina: ");
                if (qtdAvaliacoes < 0) { // Pode ser 0, se não houver avaliações ainda
                    System.out.println("Quantidade de avaliações inválida. Deve ser maior ou igual a zero. Tente novamente.");
                }
            } while (qtdAvaliacoes < 0);

            Avaliacao[] avaliacoes = this.lerAvaliacoes(s, alunos, qtdAvaliacoes);


            Turma t = new Turma(nome, ano, semestre, prof, alunos, avaliacoes);
            s.novaTurma(t); // Este método agora verifica duplicidade de turma e lança IAE
            System.out.println("Turma cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
        } catch (RuntimeException e){
            System.out.println("Ocorreu um erro inesperado ao configurar a turma: " + e.getMessage());
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
        s.listarAlunos(); // Lista todos os alunos disponíveis no sistema

        int qtd;
        boolean inputValido = false;
        Aluno[] alunos = null;

        // Loop para garantir que a quantidade de alunos na disciplina seja válida
        do {
            qtd = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");

            // Verifica se a quantidade digitada é menor ou igual à quantidade total de alunos no sistema
            if (qtd >= 0 && qtd <= contAluno) {
                alunos = new Aluno[qtd];
                inputValido = true;
            } else {
                System.out.println("Quantidade de alunos inválida. Máximo permitido: " + contAluno + ". Por favor, tente novamente.");
            }
        } while (!inputValido);

        // Loop para preencher o array de alunos com base nas matrículas
        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinha("Digite a matrícula do aluno para a posição " + (i + 1) + ": ");
            Aluno a = s.encontrarAluno(matricula);

            if (a != null) {
                // Verifica se o aluno já foi adicionado a este array para evitar duplicidade na turma
                boolean alunoJaAdicionado = false;
                for (int j = 0; j < i; j++) {
                    if (alunos[j] != null && alunos[j].getMat().equals(a.getMat())) {
                        alunoJaAdicionado = true;
                        break;
                    }
                }
                if (!alunoJaAdicionado) {
                    alunos[i] = a;
                } else {
                    System.out.println("Aluno com matrícula '" + matricula + "' já foi adicionado a esta turma. Por favor, digite uma matrícula diferente.");
                    i--; // Decrementa 'i' para tentar novamente a mesma posição
                }
            } else {
                System.out.println("Aluno com matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                i--; // Decrementa 'i' para tentar novamente a mesma posição
            }
        }

        return alunos;
    }


    public AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) {
        AlunoProva ap = null;
        try {
            ap = new AlunoProva(a);
            System.out.println("Notas de " + a.getNome() + ":");
            for (int i = 0; i < nQuestoes; i++) {
                double nota;
                do {
                    nota = this.lerDouble("Nota na questão " + (i + 1) + ": ");
                    if (nota < 0) {
                        System.out.println("Nota inválida. A nota deve ser um valor positivo. Tente novamente.");
                    }
                } while (nota < 0); // Repete enquanto a nota for inválida

                ap.adicionarNota(nota);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao configurar notas da prova para " + (a != null ? a.getNome() : "aluno desconhecido") + ": " + e.getMessage());
            throw new RuntimeException("Falha ao criar AlunoProva ou adicionar notas: " + e.getMessage(), e);
        }
        return ap;
    }

    public Prova lerProva(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Informe o nome desta prova: ");

        Data data = null;
        boolean dataValida = false;
        do {
            try {
                int dia = this.lerInteiro("Digite o dia da prova: ");
                int mes = this.lerInteiro("Digite o mês da prova: ");
                int ano = this.lerInteiro("Digite o ano da prova: ");
                data = new Data(dia, mes, ano);
                dataValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Data inválida: " + e.getMessage());
                System.out.println("Por favor, digite a data novamente.");
            }
        } while (!dataValida);

        double valor;
        do {
            valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
            if (valor <= 0) { // Valor deve ser positivo
                System.out.println("Valor inválido. Deve ser um número positivo. Tente novamente.");
            }
        } while (valor <= 0);

        int nQuestoes;
        do {
            nQuestoes = this.lerInteiro("Digite o número de questões: ");
            if (nQuestoes <= 0) {
                System.out.println("Número de questões inválido. Deve ser maior que zero. Tente novamente.");
            }
        } while (nQuestoes <= 0);

        Prova p = new Prova(nome, data, valor, nQuestoes);
        for (Aluno aluno : alunos) {
            try {
                AlunoProva ap = this.lerAlunoProva(s, aluno, nQuestoes);
                p.adicionarAlunoProva(ap); // Adicionar AlunoProva pode lançar IAE se ap for null
            } catch (RuntimeException e) {
                System.out.println("Erro ao adicionar notas do aluno " + aluno.getNome() + " na prova: " + e.getMessage());
                System.out.println("Este aluno pode não ter notas registradas para esta prova.");
            }
        }

        return p;
    }

    public GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        GrupoTrabalho grupo = new GrupoTrabalho();
        int qtd;
        do {
            qtd = this.lerInteiro("Digite o número de alunos neste grupo: ");
            if (qtd <= 0) {
                System.out.println("Número de alunos no grupo inválido. Deve ser maior que zero. Tente novamente.");
            }
        } while (qtd <= 0);

        for (int i = 0; i < qtd; i++) {
            String matricula = this.lerLinha("Digite a matrícula do aluno para a posição " + (i + 1) + " do grupo: ");
            Aluno a = s.encontrarAluno(matricula);
            if (a != null) {
                try {
                    grupo.adicionarAluno(a);
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao adicionar aluno ao grupo: " + e.getMessage());
                    i--; //Serve pra tentar novamente a mesma posição do grupo
                }
            } else {
                System.out.println("Aluno com a matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                i--;//Mesma lógica de tentar novamente a mesma posição do grupo
            }
        }

        double nota;
        do {
            nota = this.lerDouble("Digite a nota do grupo: ");
            try {
                grupo.setNota(nota);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao definir nota do grupo: " + e.getMessage());
            }
        } while (true);

        return grupo;
    }

    public Trabalho lerTrabalho(Sistema s, Aluno[] alunos) {
        String nome = this.lerLinha("Informe o nome deste trabalho: ");

        Data data = null;
        boolean dataValida = false;
        do {
            try {
                int dia = this.lerInteiro("Digite o dia do trabalho: ");
                int mes = this.lerInteiro("Digite o mês do trabalho: ");
                int ano = this.lerInteiro("Digite o ano do trabalho: ");
                data = new Data(dia, mes, ano); // Pode lançar IllegalArgumentException
                dataValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Data inválida: " + e.getMessage());
                System.out.println("Por favor, digite a data novamente.");
            }
        } while (!dataValida);

        double valor;
        do {
            valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
            if (valor <= 0) {
                System.out.println("Valor inválido. Deve ser um número positivo. Tente novamente.");
            }
        } while (valor <= 0);

        int nIntegrantes;
        do {
            nIntegrantes = this.lerInteiro("Digite o número máximo de integrantes: ");
            if (nIntegrantes <= 0) {
                System.out.println("Número de integrantes inválido. Deve ser maior que zero. Tente novamente.");
            }
        } while (nIntegrantes <= 0);

        int nGrupos;
        do {
            nGrupos = this.lerInteiro("Digite o número de grupos: ");
            if (nGrupos <= 0) {
                System.out.println("Número de grupos inválido. Deve ser maior que zero. Tente novamente.");
            }
        } while (nGrupos <= 0);

        Trabalho t = new Trabalho(nome, data, valor, nIntegrantes);
        for (int i = 0; i < nGrupos; i++) {
            System.out.println("Configurando Grupo " + (i + 1) + ":");
            try {
                GrupoTrabalho grupo = this.lerGrupoTrabalho(s);
                t.adicionarGrupo(grupo);
            } catch (RuntimeException e) {
                System.out.println("Erro ao configurar grupo de trabalho: " + e.getMessage());
                System.out.println("Este grupo pode não ter sido adicionado. Por favor, reconfigure a avaliação se necessário.");
            }
        }
        return t;
    }

    public Avaliacao[] lerAvaliacoes(Sistema s, Aluno[] alunos, int nAvaliacoes) {
        Avaliacao[] avaliacoes = new Avaliacao[nAvaliacoes];

        for (int i = 0; i < nAvaliacoes; i++) {
            System.out.println("\nConfigurando Avaliação " + (i + 1) + " de " + nAvaliacoes + ":");
            System.out.println("Escolha um tipo de avaliação: 1) Prova 2) Trabalho");
            int tipo = this.lerInteiro("Opção: ");
            while (tipo < 1 || tipo > 2) {
                tipo = this.lerInteiro("Opção inválida. Digite 1 para Prova ou 2 para Trabalho: ");
            }

            try {
                if (tipo == 1) {
                    avaliacoes[i] = this.lerProva(s, alunos);
                } else {
                    avaliacoes[i] = this.lerTrabalho(s, alunos);
                }
            } catch (RuntimeException e) { // Captura exceções lançadas por lerProva/lerTrabalho
                System.out.println("Erro ao configurar avaliação: " + e.getMessage());
                System.out.println("Por favor, tente novamente esta avaliação.");
                i--; // Decrementa 'i' para permitir que o usuário tente novamente esta avaliação
            }
        }

        return avaliacoes;
    }
}