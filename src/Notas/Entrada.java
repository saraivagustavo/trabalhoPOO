package Notas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Gustavo Saraiva Mariano e Pedro Henrique Albani Nunes
 */
public class Entrada {
    public Scanner input;
    private boolean isSystemIn;

    /**
     * Construtor da classe Entrada.
     * Tenta ler de "input.txt" para automatizar a entrada de dados do menu.
     * Se não encontrado, lê do teclado (System.in) para interação manual.
     */
    public Entrada() {
        try {
            this.input = new Scanner(new FileInputStream("input.txt"));
            this.isSystemIn = false; // Indica que está lendo de um arquivo (input.txt)
            System.out.println("Lendo entradas de 'input.txt' para automatização do menu.");
        } catch (FileNotFoundException e) {
            this.input = new Scanner(System.in);
            this.isSystemIn = true; // Indica que está lendo do teclado
            System.out.println("Arquivo 'input.txt' não encontrado. Lendo do teclado para interação manual.");
        }
    }

    // *******************************************************
    // *************** MÉTODOS DE LEITURA GERAIS (para interação ou input.txt) ****************
    // *******************************************************

    /**
     * Faz a leitura de uma linha. Comportamento difere para entrada interativa vs. arquivo (input.txt).
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return Uma String contendo a linha lida.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     */
    private String lerLinha(String msg) {
        if (this.isSystemIn) {
            System.out.print(msg);
        }
        try {
            String linha = this.input.nextLine();
            while (linha.isEmpty() || linha.trim().startsWith("#")) {
                if (linha.isEmpty()) {
                    if (this.isSystemIn) System.out.println("Entrada vazia. Por favor, digite algo.");
                } else if (linha.trim().startsWith("#")) {
                    // Linha de comentário, apenas ignora e lê a próxima
                }
                if (this.isSystemIn) System.out.print(msg); // Reprompt se for interativo
                linha = this.input.nextLine();
            }
            return linha;
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("Fim inesperado da entrada de dados.", e);
        }
    }

    /**
     * Faz a leitura de um número inteiro. Comportamento difere para entrada interativa vs. arquivo (input.txt).
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return O número inteiro lido.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     * @throws IllegalArgumentException se o formato numérico for inválido em leitura de arquivo.
     */
    public int lerInteiro(String msg) {
        String linha;
        try {
            linha = lerLinha(msg);
        } catch (IllegalStateException e) {
            throw e; // Repassa o EOF
        }

        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            if (this.isSystemIn) {
                System.out.println("Formato inválido! Tente novamente.");
                return lerInteiro(msg); // Tenta novamente (apenas em modo interativo)
            } else {
                // Em input.txt, não repete, lança exceção para ser tratada pelo chamador
                throw new IllegalArgumentException("Formato numérico inteiro inválido em 'input.txt': '" + linha + "'", e);
            }
        }
    }

    /**
     * Faz a leitura de um double. Comportamento difere para entrada interativa vs. arquivo (input.txt).
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return O número double lido.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     * @throws IllegalArgumentException se o formato numérico for inválido em leitura de arquivo.
     */
    public double lerDouble(String msg) {
        String linha;
        try {
            linha = lerLinha(msg);
        } catch (IllegalStateException e) {
            throw e; // Repassa o EOF
        }

        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            if (this.isSystemIn) {
                System.out.println("Formato inválido! Tente novamente.");
                return lerDouble(msg); // Tenta novamente (apenas em modo interativo)
            } else {
                // Em input.txt, não repete, lança exceção para ser tratada pelo chamador
                throw new IllegalArgumentException("Formato numérico double inválido em 'input.txt': '" + linha + "'", e);
            }
        }
    }

    // *******************************************************
    // ******* MÉTODOS DE LEITURA PARA CARREGAMENTO DE ARQUIVO (dados.txt) - Reintroduzidos ********
    // *******************************************************

    /**
     * Faz a leitura de uma linha de um Scanner fornecido (para leitura de dados.txt sem prompts).
     * Ignora linhas em branco ou que começam com '#'.
     * @param sc O Scanner a ser utilizado para leitura.
     * @return Uma String contendo a linha lida, ou null se o fim do arquivo for atingido.
     */
    public String lerLinhaArquivo(Scanner sc) {
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.isEmpty() || linha.trim().startsWith("#")) {
                continue; // Ignora linhas vazias ou comentários
            }
            return linha;
        }
        return null; // Fim do arquivo
    }

    /**
     * Faz a leitura de um inteiro de um Scanner fornecido (para leitura de dados.txt sem prompts).
     * @param sc O Scanner a ser utilizado para leitura.
     * @return O número inteiro lido, ou null se o fim do arquivo for atingido ou formato inválido.
     */
    public Integer lerInteiroArquivo(Scanner sc) {
        String linha = lerLinhaArquivo(sc);
        if (linha == null) return null;
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            System.err.println("Erro: Formato de número inteiro inválido ao ler do arquivo 'dados.txt': '" + linha + "'");
            return null;
        }
    }

    /**
     * Faz a leitura de um double de um Scanner fornecido (para leitura de dados.txt sem prompts).
     * @param sc O Scanner a ser utilizado para leitura.
     * @return O número double lido, ou null se o fim do arquivo for atingido ou formato inválido.
     */
    public Double lerDoubleArquivo(Scanner sc) {
        String linha = lerLinhaArquivo(sc);
        if (linha == null) return null;
        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            System.err.println("Erro: Formato de número double inválido ao ler do arquivo 'dados.txt': '" + linha + "'");
            return null;
        }
    }

    // *******************************************************
    // ************* MÉTODO menu() - Revertido para numérico *************
    // *******************************************************

    /**
     * Imprime o menu principal, lê a opção escolhida pelo usuário e retorna a opção selecionada.
     * @return Inteiro contendo a opção escolhida pelo usuário.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     */
    public int menu() {
        String msg = "\n*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastrar professor:\n" +
                "2) Cadastrar aluno:\n" +
                "3) Cadastrar turma:\n" +
                "4) Listar turmas:\n" +
                "0) Sair\n" +
                "Opção: "; // Adicionado "Opção: " para prompt claro

        int op = -1;
        try {
            op = this.lerInteiro(msg);
        } catch (IllegalStateException e) {
            System.out.println("Fim da entrada de dados. O programa encerrará.");
            return 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de formato na opção do menu em 'input.txt': " + e.getMessage());
            return -1; // Retorna -1 para tentar novamente no loop do Main
        }

        while (op < 0 || op > 4) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }

        return op;
    }

    // *******************************************************
    // *************** MÉTODOS DE CADASTRO (ajustes para exceções de input.txt) *******************
    // *******************************************************

    public void cadProf(Sistema s) {
        s.listarProfs();
        String nome = this.lerLinha("Digite o nome do professor: ");
        String cpf = this.lerLinha("Digite o cpf do professor: ");
        double salario;
        try {
            salario = this.lerDouble("Digite o salário do professor: R$");
            while (salario < 0) {
                System.out.println("Salário inválido. Deve ser um valor positivo. Tente novamente.");
                salario = this.lerDouble("Digite o salário do professor: R$");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler salário do professor de 'input.txt': " + e.getMessage());
            return;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler salário do professor: " + e.getMessage());
            return;
        }

        try {
            Professor p = new Professor(nome, cpf, salario);
            s.novoProf(p);
            System.out.println("Professor cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar professor: " + e.getMessage());
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
            System.out.println("Aluno cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    public void cadTurma(Sistema s) {
        s.listarTurmas();
        String nome = this.lerLinha("Digite o nome da disciplina: ");
        int ano;
        try {
            do {
                ano = this.lerInteiro("Digite o ano da disciplina: ");
                if (ano <= 0) {
                    System.out.println("Ano inválido. Deve ser um valor positivo. Tente novamente.");
                }
            } while (ano <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler ano da disciplina de 'input.txt': " + e.getMessage());
            return;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler ano da disciplina: " + e.getMessage());
            return;
        }

        int semestre;
        try {
            do {
                semestre = this.lerInteiro("Digite o semestre da disciplina (1 ou 2): ");
                if (semestre != 1 && semestre != 2) {
                    System.out.println("Semestre inválido. Deve ser 1 ou 2. Tente novamente.");
                }
            } while (semestre != 1 && semestre != 2);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler semestre da disciplina de 'input.txt': " + e.getMessage());
            return;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler semestre da disciplina: " + e.getMessage());
            return;
        }


        String cpfProfessor = this.lerLinha("Digite o CPF do professor: ");
        Professor prof = s.encontrarProfessor(cpfProfessor);

        if (prof == null) {
            System.err.println("Erro: CPF do professor " + cpfProfessor + " não encontrado. Turma não adicionada.");
            return;
        }

        try {
            Aluno[] alunosDaTurma = this.lerAlunos(s);
            if (alunosDaTurma == null || alunosDaTurma.length == 0) {
                System.err.println("Erro: Nenhum aluno(s) válido(s) selecionado(s) para a turma. Turma não adicionada.");
                return;
            }

            int qtdAvaliacoes;
            try {
                do {
                    qtdAvaliacoes = this.lerInteiro("Digite a quantidade de avaliações na disciplina: ");
                    if (qtdAvaliacoes < 0) {
                        System.out.println("Quantidade de avaliações inválida. Deve ser maior ou igual a zero. Tente novamente.");
                    }
                } while (qtdAvaliacoes < 0);
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao ler quantidade de avaliações de 'input.txt': " + e.getMessage());
                return;
            } catch (IllegalStateException e) {
                System.err.println("Fim de 'input.txt' inesperado ao ler quantidade de avaliações: " + e.getMessage());
                return;
            }

            Avaliacao[] avaliacoesDaTurma = this.lerAvaliacoes(s, alunosDaTurma, qtdAvaliacoes);
            int avaliacoesCarregadas = 0;
            if (avaliacoesDaTurma != null) {
                for (Avaliacao aval : avaliacoesDaTurma) {
                    if (aval != null) {
                        avaliacoesCarregadas++;
                    }
                }
            }
            if (avaliacoesCarregadas == 0 && qtdAvaliacoes > 0) {
                System.err.println("Erro: Nenhuma avaliação válida foi carregada para a turma. Turma não adicionada.");
                return;
            }


            Turma t = new Turma(nome, ano, semestre, prof, alunosDaTurma, avaliacoesDaTurma);
            s.novaTurma(t);
            System.out.println("Turma cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar turma: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Erro ao configurar turma (fim de input.txt inesperado ou formato): " + e.getMessage());
        } catch (RuntimeException e){
            System.err.println("Ocorreu um erro inesperado ao configurar a turma: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // *******************************************************
    // ************ MÉTODOS AUXILIARES DE LEITURA (ajustes para exceções de input.txt) ************
    // *******************************************************

    public Professor lerProf(Sistema s) {
        String cpf = this.lerLinha("Digite o CPF do professor: ");
        return s.encontrarProfessor(cpf);
    }

    public Aluno[] lerAlunos(Sistema s) {
        s.listarAlunos();

        int qtd;
        Aluno[] alunosArray = null;

        do {
            try {
                qtd = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");
            } catch (IllegalStateException e) {
                System.err.println("Fim de 'input.txt' inesperado ao ler quantidade de alunos para a disciplina.");
                return null;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro ao ler quantidade de alunos para a disciplina de 'input.txt': " + e.getMessage());
                return null;
            }


            if (qtd < 0) {
                System.out.println("Quantidade de alunos inválida. Deve ser maior ou igual a zero. Tente novamente.");
                if (!this.isSystemIn) return null;
            } else if (qtd > s.getAlunos().size()) {
                System.out.println("Quantidade de alunos inválida. Máximo permitido: " + s.getAlunos().size() + ". Por favor, tente novamente.");
                if (!this.isSystemIn) return null;
            } else {
                alunosArray = new Aluno[qtd];
                break;
            }
        } while (true);

        for (int i = 0; i < qtd; i++) {
            String matricula;
            try {
                matricula = this.lerLinha("Digite a matrícula do aluno para a posição " + (i + 1) + ": ");
            } catch (IllegalStateException e) {
                System.err.println("Fim de 'input.txt' inesperado ao ler matrícula de aluno para a turma.");
                return alunosArray;
            }
            Aluno a = s.encontrarAluno(matricula);

            if (a != null) {
                boolean alunoJaAdicionado = false;
                for (int j = 0; j < i; j++) {
                    if (alunosArray[j] != null && alunosArray[j].getMat().equals(a.getMat())) {
                        alunoJaAdicionado = true;
                        break;
                    }
                }
                if (!alunoJaAdicionado) {
                    alunosArray[i] = a;
                } else {
                    System.out.println("Aluno com matrícula '" + matricula + "' já foi adicionado a esta turma. Por favor, digite uma matrícula diferente.");
                    if (!this.isSystemIn) {
                        System.err.println("Erro de dados em 'input.txt': Aluno duplicado em lista de turma para matrícula '" + matricula + "'");
                    }
                    i--;
                }
            } else {
                System.out.println("Aluno com matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                if (!this.isSystemIn) {
                    System.err.println("Erro de dados em 'input.txt': Aluno não encontrado para matrícula '" + matricula + "'");
                }
                i--;
            }
        }
        return alunosArray;
    }

    public AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) {
        AlunoProva ap = null;
        try {
            ap = new AlunoProva(a);
            if (this.isSystemIn) System.out.println("Notas de " + a.getNome() + ":");
            for (int i = 0; i < nQuestoes; i++) {
                double nota;
                try {
                    do {
                        nota = this.lerDouble("Nota na questão " + (i + 1) + ": ");
                        if (nota < 0) {
                            System.out.println("Nota inválida. A nota deve ser um valor positivo. Tente novamente.");
                        }
                    } while (nota < 0);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro ao ler nota da questão " + (i + 1) + " para " + a.getNome() + " de 'input.txt': " + e.getMessage());
                    throw new RuntimeException("Falha ao ler notas para AlunoProva devido a formato inválido.", e);
                } catch (IllegalStateException e) {
                    System.err.println("Fim de 'input.txt' inesperado ao ler notas da prova para " + a.getNome() + ": " + e.getMessage());
                    throw new RuntimeException("Falha ao ler notas para AlunoProva devido a EOF.", e);
                }
                ap.adicionarNota(nota);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao configurar notas da prova para " + (a != null ? a.getNome() : "aluno desconhecido") + ": " + e.getMessage());
            throw new RuntimeException("Falha ao criar AlunoProva ou adicionar notas: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw e;
        }
        return ap;
    }

    public Prova lerProva(Sistema s, Aluno[] alunos) {
        String nome;
        try {
            nome = this.lerLinha("Informe o nome desta prova: ");
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler nome da prova."); return null;
        }

        Data data = null;
        boolean dataValida = false;
        try {
            do {
                try {
                    int dia = this.lerInteiro("Digite o dia da prova: ");
                    int mes = this.lerInteiro("Digite o mês da prova: ");
                    int ano = this.lerInteiro("Digite o ano da prova: ");
                    data = new Data(dia, mes, ano);
                    dataValida = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Data inválida: " + e.getMessage());
                    if (this.isSystemIn) System.out.println("Por favor, digite a data novamente.");
                    else throw new RuntimeException("Erro de dados em 'input.txt' ao ler data da prova.", e);
                }
            } while (!dataValida);
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler data da prova.");
            return null;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return null;
        }


        double valor;
        try {
            do {
                valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
                if (valor <= 0) {
                    System.out.println("Valor inválido. Deve ser um número positivo. Tente novamente.");
                }
            } while (valor <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler valor da prova de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler valor da prova.");
            return null;
        }


        int nQuestoes;
        try {
            do {
                nQuestoes = this.lerInteiro("Digite o número de questões: ");
                if (nQuestoes <= 0) {
                    System.out.println("Número de questões inválido. Deve ser maior que zero. Tente novamente.");
                }
            } while (nQuestoes <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler número de questões da prova de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler número de questões da prova.");
            return null;
        }

        Prova p = new Prova(nome, data, valor, nQuestoes);
        for (Aluno aluno : alunos) {
            try {
                AlunoProva ap = this.lerAlunoProva(s, aluno, nQuestoes);
                if (ap != null) {
                    p.adicionarAlunoProva(ap);
                }
            } catch (RuntimeException e) {
                System.err.println("Erro ao adicionar notas do aluno " + aluno.getNome() + " na prova: " + e.getMessage());
                System.err.println("Este aluno pode não ter notas registradas para esta prova.");
            }
        }
        return p;
    }

    public GrupoTrabalho lerGrupoTrabalho(Sistema s) {
        GrupoTrabalho grupo = new GrupoTrabalho();
        int qtdAlunosGrupo;
        try {
            do {
                qtdAlunosGrupo = this.lerInteiro("Digite o número de alunos neste grupo: ");
                if (qtdAlunosGrupo <= 0) {
                    System.out.println("Número de alunos no grupo inválido. Deve ser maior que zero. Tente novamente.");
                }
            } while (qtdAlunosGrupo <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler quantidade de alunos para o grupo de trabalho de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler quantidade de alunos para o grupo de trabalho.");
            return null;
        }

        for (int i = 0; i < qtdAlunosGrupo; i++) {
            String matricula;
            try {
                matricula = this.lerLinha("Digite a matrícula do aluno para a posição " + (i + 1) + " do grupo: ");
            } catch (IllegalStateException e) {
                System.err.println("Fim de 'input.txt' inesperado ao ler matrícula de aluno para o grupo."); return null;
            }

            Aluno a = s.encontrarAluno(matricula);
            if (a != null) {
                try {
                    grupo.adicionarAluno(a);
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro ao adicionar aluno ao grupo: " + e.getMessage());
                    i--;
                }
            } else {
                System.out.println("Aluno com a matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                if (!this.isSystemIn) {
                    System.err.println("Erro de dados em 'input.txt': Aluno não encontrado para matrícula '" + matricula + "' no grupo.");
                }
                i--;
            }
        }

        double nota;
        try {
            do {
                nota = this.lerDouble("Digite a nota do grupo: ");
                try {
                    grupo.setNota(nota);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao definir nota do grupo: " + e.getMessage());
                }
            } while (true);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler nota do grupo de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler nota do grupo."); return null;
        }

        return grupo;
    }

    public Trabalho lerTrabalho(Sistema s, Aluno[] alunos) {
        String nome;
        try {
            nome = this.lerLinha("Informe o nome deste trabalho: ");
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler nome do trabalho."); return null;
        }

        Data data = null;
        boolean dataValida = false;
        try {
            do {
                try {
                    int dia = this.lerInteiro("Digite o dia do trabalho: ");
                    int mes = this.lerInteiro("Digite o mês do trabalho: ");
                    int ano = this.lerInteiro("Digite o ano do trabalho: ");
                    data = new Data(dia, mes, ano);
                    dataValida = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Data inválida: " + e.getMessage());
                    if (this.isSystemIn) System.out.println("Por favor, digite a data novamente.");
                    else throw new RuntimeException("Erro de dados em 'input.txt' ao ler data do trabalho.", e);
                }
            } while (!dataValida);
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler data do trabalho.");
            return null;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return null;
        }

        double valor;
        try {
            do {
                valor = this.lerDouble("Digite o valor máximo desta avaliação: ");
                if (valor <= 0) {
                    System.out.println("Valor inválido. Deve ser um número positivo. Tente novamente.");
                }
            } while (valor <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler valor do trabalho de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler valor do trabalho.");
            return null;
        }

        int nIntegrantes;
        try {
            do {
                nIntegrantes = this.lerInteiro("Digite o número máximo de integrantes: ");
                if (nIntegrantes <= 0) {
                    System.out.println("Número de integrantes inválido. Deve ser maior que zero. Tente novamente.");
                }
            } while (nIntegrantes <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler número de integrantes do trabalho de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler número de integrantes do trabalho.");
            return null;
        }

        int nGrupos;
        try {
            do {
                nGrupos = this.lerInteiro("Digite o número de grupos: ");
                if (nGrupos <= 0) {
                    System.out.println("Número de grupos inválido. Deve ser maior que zero. Tente novamente.");
                }
            } while (nGrupos <= 0);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao ler número de grupos do trabalho de 'input.txt': " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim de 'input.txt' inesperado ao ler número de grupos do trabalho.");
            return null;
        }

        Trabalho t = new Trabalho(nome, data, valor, nIntegrantes);
        for (int i = 0; i < nGrupos; i++) {
            if (this.isSystemIn) System.out.println("Configurando Grupo " + (i + 1) + ":");
            try {
                GrupoTrabalho grupo = this.lerGrupoTrabalho(s);
                if (grupo != null) {
                    t.adicionarGrupo(grupo);
                } else {
                    System.err.println("Grupo de trabalho " + (i + 1) + " não foi configurado devido a erro/fim de 'input.txt'.");
                }
            } catch (RuntimeException e) {
                System.err.println("Erro ao configurar grupo de trabalho " + (i + 1) + ": " + e.getMessage());
                System.err.println("Este grupo pode não ter sido adicionado. Por favor, verifique os dados.");
            }
        }
        return t;
    }

    public Avaliacao[] lerAvaliacoes(Sistema s, Aluno[] alunos, int nAvaliacoes) {
        Avaliacao[] avaliacoes = new Avaliacao[nAvaliacoes];

        for (int i = 0; i < nAvaliacoes; i++) {
            if (this.isSystemIn) {
                System.out.println("\nConfigurando Avaliação " + (i + 1) + " de " + nAvaliacoes + ":");
                System.out.println("Escolha um tipo de avaliação: 1) Prova 2) Trabalho");
            }
            int tipo;
            try {
                tipo = this.lerInteiro("Opção: ");
            } catch (IllegalStateException e) {
                System.err.println("Fim de 'input.txt' inesperado ao ler tipo de avaliação para Avaliação " + (i + 1) + ". Esta avaliação não será configurada.");
                return avaliacoes;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro de formato ao ler tipo de avaliação em 'input.txt' para Avaliação " + (i + 1) + ": " + e.getMessage());
                if (this.isSystemIn) {
                    System.out.println("Por favor, tente novamente esta avaliação.");
                    i--;
                } else {
                    return avaliacoes;
                }
                continue;
            }


            while (tipo < 1 || tipo > 2) {
                System.out.println("Opção inválida. Digite 1 para Prova ou 2 para Trabalho: ");
                try {
                    tipo = this.lerInteiro("Opção: ");
                } catch (IllegalStateException e) {
                    System.err.println("Fim de 'input.txt' inesperado ao reler tipo de avaliação. Esta avaliação não será configurada.");
                    return avaliacoes;
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro de formato ao reler tipo de avaliação em 'input.txt': " + e.getMessage());
                    if (!this.isSystemIn) {
                        return avaliacoes;
                    }
                }
            }

            try {
                if (tipo == 1) {
                    avaliacoes[i] = this.lerProva(s, alunos);
                } else {
                    avaliacoes[i] = this.lerTrabalho(s, alunos);
                }
                if (avaliacoes[i] == null) {
                    System.err.println("Avaliação " + (i + 1) + " não foi configurada devido a erro/fim de 'input.txt'/dados incompletos.");
                    if (this.isSystemIn) {
                        System.out.println("Por favor, tente novamente esta avaliação.");
                        i--;
                    } else {
                        return avaliacoes;
                    }
                }
            } catch (RuntimeException e) {
                System.err.println("Erro ao configurar avaliação " + (i + 1) + ": " + e.getMessage());
                if (this.isSystemIn) {
                    System.out.println("Por favor, tente novamente esta avaliação.");
                    i--;
                } else {
                    return avaliacoes;
                }
            }
        }
        return avaliacoes;
    }
}