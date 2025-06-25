package Notas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException; // Mantido por segurança, embora nextLine não lance
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
     * Tenta ler de "dados.txt" como a fonte primária de comandos.
     * Se "dados.txt" não for encontrado, lê do teclado (System.in) para interação.
     */
    public Entrada() {
        try {
            this.input = new Scanner(new FileInputStream("dados.txt"));
            this.isSystemIn = false; // Indica que está lendo de um arquivo
            System.out.println("Arquivo 'dados.txt' encontrado. Lendo comandos do arquivo para interação.");
            System.out.println("\n*********************");
        } catch (FileNotFoundException e) {
            this.input = new Scanner(System.in);
            this.isSystemIn = true; // Indica que está lendo do teclado
            System.out.println("Arquivo 'dados.txt' não encontrado. Lendo comandos do teclado para interação.");
        }
    }

    // *******************************************************
    // *************** MÉTODOS DE LEITURA GERAIS ****************
    // *******************************************************

    /**
     * Método auxiliar privado para ler a próxima linha bruta do Scanner,
     * ignorando linhas vazias ou comentários. Lança NoSuchElementException em EOF.
     * @return A linha lida (não vazia e não comentário).
     * @throws NoSuchElementException se não houver mais linhas para ler (EOF).
     */
    private String lerLinhaInterno() throws NoSuchElementException {
        String linha;
        while (true) {
            linha = this.input.nextLine();
            if (linha.isEmpty() || linha.trim().startsWith("#")) {
                continue; // Ignora linhas vazias ou comentários
            }
            return linha;
        }
    }

    /**
     * Faz a leitura de uma linha. Comportamento difere para entrada interativa vs. arquivo.
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return Uma String contendo a linha lida.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     */
    public String lerLinha(String msg) {
        if (this.isSystemIn) { // Só imprime a mensagem se a entrada for via teclado
            System.out.print(msg);
        }
        try {
            return lerLinhaInterno();
        } catch (NoSuchElementException e) {
            // Se o EOF for atingido em um arquivo, ou em System.in se o usuário forçar EOF (Ctrl+D/Z)
            throw new IllegalStateException("Fim inesperado da entrada de dados.", e);
        }
        // InputMismatchException não é lançada por nextLine(), então não precisa de catch aqui.
    }

    /**
     * Faz a leitura de um número inteiro. Comportamento difere para entrada interativa vs. arquivo.
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return O número inteiro lido.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     * @throws IllegalArgumentException se o formato numérico for inválido em leitura de arquivo.
     */
    public int lerInteiro(String msg) {
        String linha;
        try {
            linha = lerLinha(msg); // Usa o método lerLinha principal
        } catch (IllegalStateException e) {
            throw e; // Repassa o EOF de lerLinha
        }

        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            if (this.isSystemIn) {
                System.out.println("Formato inválido! Tente novamente.");
                return lerInteiro(msg); // Tenta novamente (apenas em modo interativo)
            } else {
                // Em modo arquivo, não repete, lança exceção para ser tratada pelo chamador
                throw new IllegalArgumentException("Formato numérico inteiro inválido no arquivo: '" + linha + "'", e);
            }
        }
    }

    /**
     * Faz a leitura de um double. Comportamento difere para entrada interativa vs. arquivo.
     * @param msg Mensagem que será exibida ao usuário (APENAS se isSystemIn for true).
     * @return O número double lido.
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     * @throws IllegalArgumentException se o formato numérico for inválido em leitura de arquivo.
     */
    public double lerDouble(String msg) {
        String linha;
        try {
            linha = lerLinha(msg); // Usa o método lerLinha principal
        } catch (IllegalStateException e) {
            throw e; // Repassa o EOF de lerLinha
        }

        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            if (this.isSystemIn) {
                System.out.println("Formato inválido! Tente novamente.");
                return lerDouble(msg); // Tenta novamente (apenas em modo interativo)
            } else {
                // Em modo arquivo, não repete, lança exceção para ser tratada pelo chamador
                throw new IllegalArgumentException("Formato numérico double inválido no arquivo: '" + linha + "'", e);
            }
        }
    }

    // *******************************************************
    // ************* MÉTODO menu() REESTRUTURADO *************
    // *******************************************************

    /**
     * Lê e retorna a próxima tag/comando do arquivo (dados.txt) ou do teclado.
     * Não exibe um menu numerado.
     * @return A String da tag/comando lida (ex: "PROF", "ALU", "TUR", "LISTAR", "FIM").
     * @throws IllegalStateException se o fim da entrada for alcançado inesperadamente (EOF).
     */
    public String menu() {
        if (this.isSystemIn) {
            System.out.println("\n*********************");
            System.out.println("Digite um comando (PROF, ALU, TUR, LISTAR, FIM):");
            // Nota: lerLinha já imprime "Comando: " se this.isSystemIn for true
        }
        String comando = lerLinha("Comando: "); // Usa o lerLinha refatorado
        return comando.trim().toUpperCase(); // Retorna o comando em maiúsculas para facilitar a comparação
    }

    // *******************************************************
    // *************** MÉTODOS DE CADASTRO (ajustes para lidar com exceções) *******************
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler salário do professor: " + e.getMessage());
            return; // Sai do método se houver erro de leitura no arquivo
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler ano da disciplina: " + e.getMessage());
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler semestre da disciplina: " + e.getMessage());
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
                System.err.println("Erro: Nenhuma aluno(s) válido(s) selecionado(s) para a turma. Turma não adicionada.");
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
                System.err.println("Erro ao ler quantidade de avaliações: " + e.getMessage());
                return;
            }

            Avaliacao[] avaliacoesDaTurma = this.lerAvaliacoes(s, alunosDaTurma, qtdAvaliacoes);
            // Verifica se a quantidade real de avaliações carregadas corresponde à esperada
            int avaliacoesCarregadas = 0;
            if (avaliacoesDaTurma != null) {
                for (Avaliacao aval : avaliacoesDaTurma) {
                    if (aval != null) {
                        avaliacoesCarregadas++;
                    }
                }
            }
            if (avaliacoesCarregadas == 0 && qtdAvaliacoes > 0) { // Se esperava avaliações mas nenhuma foi carregada
                System.err.println("Erro: Nenhuma avaliação válida foi carregada para a turma. Turma não adicionada.");
                return;
            }


            Turma t = new Turma(nome, ano, semestre, prof, alunosDaTurma, avaliacoesDaTurma);
            s.novaTurma(t);
            System.out.println("Turma cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar turma: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Erro ao configurar turma (fim de arquivo inesperado ou formato): " + e.getMessage());
        } catch (RuntimeException e){
            System.err.println("Ocorreu um erro inesperado ao configurar a turma: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // *******************************************************
    // ************ MÉTODOS AUXILIARES DE LEITURA (ajustes para lidar com exceções) ************
    // *******************************************************

    public Professor lerProf(Sistema s) { // Usado em cadTurma para encontrar o professor
        String cpf = this.lerLinha("Digite o CPF do professor: ");
        return s.encontrarProfessor(cpf);
    }

    public Aluno[] lerAlunos(Sistema s) { // Usado em cadTurma para ler alunos da turma
        s.listarAlunos(); // Lista todos os alunos disponíveis no sistema

        int qtd;
        Aluno[] alunosArray = null;

        // Loop para garantir que a quantidade de alunos na disciplina seja válida
        do {
            try {
                qtd = this.lerInteiro("Digite a quantidade de alunos na disciplina: ");
            } catch (IllegalStateException e) {
                System.err.println("Fim do arquivo ao ler quantidade de alunos para a disciplina.");
                return null;
            } catch (IllegalArgumentException e) { // Erro de formato em arquivo
                System.err.println("Erro ao ler quantidade de alunos para a disciplina: " + e.getMessage());
                // Em modo arquivo, retorna null, não tenta novamente
                return null;
            }


            if (qtd < 0) {
                System.out.println("Quantidade de alunos inválida. Deve ser maior ou igual a zero. Tente novamente.");
                // Continua o loop se for interativo, ou sai se for arquivo
                if (!this.isSystemIn) return null; // Sai para arquivo se entrada inválida
            } else if (qtd > s.getAlunos().size()) {
                System.out.println("Quantidade de alunos inválida. Máximo permitido: " + s.getAlunos().size() + ". Por favor, tente novamente.");
                if (!this.isSystemIn) return null; // Sai para arquivo se entrada inválida
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
                System.err.println("Fim do arquivo ao ler matrícula de aluno para a turma.");
                return alunosArray; // Retorna o array parcial
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
                    if (!this.isSystemIn) { // Não repete em arquivo, considera um erro de dados
                        System.err.println("Erro de dados: Aluno duplicado em lista de turma para matrícula '" + matricula + "'");
                        // Pode ser uma estratégia diferente aqui, como pular o aluno ou lançar exceção fatal
                    }
                    i--; // Repete a posição para entrada interativa ou para tentar pular o erro em arquivo
                }
            } else {
                System.out.println("Aluno com matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                if (!this.isSystemIn) { // Não repete em arquivo, considera um erro de dados
                    System.err.println("Erro de dados: Aluno não encontrado para matrícula '" + matricula + "'");
                }
                i--; // Repete a posição
            }
        }
        return alunosArray;
    }

    public AlunoProva lerAlunoProva(Sistema s, Aluno a, int nQuestoes) { // Usado em lerProva
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
                } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
                    System.err.println("Erro ao ler nota da questão " + (i + 1) + " para " + a.getNome() + ": " + e.getMessage());
                    // Em modo arquivo, interrompe a leitura das notas para este AlunoProva
                    throw new RuntimeException("Falha ao ler notas para AlunoProva devido a formato inválido.", e);
                } catch (IllegalStateException e) {
                    System.err.println("Fim do arquivo de entrada inesperado ao ler notas da prova para " + a.getNome() + ": " + e.getMessage());
                    throw new RuntimeException("Falha ao ler notas para AlunoProva devido a EOF.", e);
                }
                ap.adicionarNota(nota);
            }
        } catch (IllegalArgumentException e) { // Erros de validação do construtor AlunoProva ou adicionarNota (nota negativa)
            System.err.println("Erro ao configurar notas da prova para " + (a != null ? a.getNome() : "aluno desconhecido") + ": " + e.getMessage());
            throw new RuntimeException("Falha ao criar AlunoProva ou adicionar notas: " + e.getMessage(), e);
        } catch (RuntimeException e) { // Propaga RunTimeException de leitura de notas (formato/EOF)
            throw e;
        }
        return ap;
    }

    public Prova lerProva(Sistema s, Aluno[] alunos) { // Usado em lerAvaliacoes
        String nome;
        try {
            nome = this.lerLinha("Informe o nome desta prova: ");
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler nome da prova."); return null;
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
                } catch (IllegalArgumentException e) { // Erro de validação do construtor Data
                    System.out.println("Data inválida: " + e.getMessage());
                    if (this.isSystemIn) System.out.println("Por favor, digite a data novamente.");
                    else throw e; // Em arquivo, propaga o erro de data inválida
                }
            } while (!dataValida);
        } catch (IllegalStateException e) { // EOF durante leitura de dia/mes/ano
            System.err.println("Fim do arquivo ao ler data da prova.");
            return null;
        } catch (IllegalArgumentException e) { // Propaga erro de formato de data no arquivo
            System.err.println("Erro ao ler data da prova do arquivo: " + e.getMessage());
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler valor da prova: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler valor da prova.");
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler número de questões da prova: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler número de questões da prova.");
            return null;
        }

        Prova p = new Prova(nome, data, valor, nQuestoes);
        for (Aluno aluno : alunos) {
            try {
                AlunoProva ap = this.lerAlunoProva(s, aluno, nQuestoes);
                if (ap != null) {
                    p.adicionarAlunoProva(ap);
                }
            } catch (RuntimeException e) { // Captura RunTimeException de lerAlunoProva
                System.err.println("Erro ao adicionar notas do aluno " + aluno.getNome() + " na prova: " + e.getMessage());
                System.err.println("Este aluno pode não ter notas registradas para esta prova.");
                // Não retorna null aqui para Prova, apenas registra o erro e continua com os próximos alunos
            }
        }
        return p;
    }

    public GrupoTrabalho lerGrupoTrabalho(Sistema s) { // Usado em lerTrabalho
        GrupoTrabalho grupo = new GrupoTrabalho();
        int qtdAlunosGrupo;
        try {
            do {
                qtdAlunosGrupo = this.lerInteiro("Digite o número de alunos neste grupo: ");
                if (qtdAlunosGrupo <= 0) {
                    System.out.println("Número de alunos no grupo inválido. Deve ser maior que zero. Tente novamente.");
                }
            } while (qtdAlunosGrupo <= 0);
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler quantidade de alunos para o grupo de trabalho: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler quantidade de alunos para o grupo de trabalho.");
            return null;
        }

        for (int i = 0; i < qtdAlunosGrupo; i++) {
            String matricula;
            try {
                matricula = this.lerLinha("Digite a matrícula do aluno para a posição " + (i + 1) + " do grupo: ");
            } catch (IllegalStateException e) {
                System.err.println("Fim do arquivo ao ler matrícula de aluno para o grupo."); return null;
            }

            Aluno a = s.encontrarAluno(matricula);
            if (a != null) {
                try {
                    grupo.adicionarAluno(a);
                } catch (IllegalArgumentException e) { // Aluno duplicado no grupo
                    System.err.println("Erro ao adicionar aluno ao grupo: " + e.getMessage());
                    // Em modo arquivo, pode querer pular este aluno ou invalidar o grupo
                    i--; // Repete a posição
                }
            } else {
                System.out.println("Aluno com a matrícula '" + matricula + "' não encontrado. Por favor, digite uma matrícula válida.");
                if (!this.isSystemIn) { // Em arquivo, apenas registra o erro e tenta a próxima
                    System.err.println("Erro de dados: Aluno não encontrado para matrícula '" + matricula + "' no grupo.");
                }
                i--; // Repete a posição
            }
        }

        double nota;
        try {
            do {
                nota = this.lerDouble("Digite a nota do grupo: ");
                try {
                    grupo.setNota(nota);
                    break;
                } catch (IllegalArgumentException e) { // Nota negativa
                    System.out.println("Erro ao definir nota do grupo: " + e.getMessage());
                }
            } while (true);
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler nota do grupo: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler nota do grupo."); return null;
        }

        return grupo;
    }

    public Trabalho lerTrabalho(Sistema s, Aluno[] alunos) { // Usado em lerAvaliacoes
        String nome;
        try {
            nome = this.lerLinha("Informe o nome deste trabalho: ");
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler nome do trabalho."); return null;
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
                } catch (IllegalArgumentException e) { // Erro de validação do construtor Data
                    System.out.println("Data inválida: " + e.getMessage());
                    if (this.isSystemIn) System.out.println("Por favor, digite a data novamente.");
                    else throw e; // Em arquivo, propaga o erro de data inválida
                }
            } while (!dataValida);
        } catch (IllegalStateException e) { // EOF durante leitura de dia/mes/ano
            System.err.println("Fim do arquivo ao ler data do trabalho.");
            return null;
        } catch (IllegalArgumentException e) { // Propaga erro de formato de data no arquivo
            System.err.println("Erro ao ler data do trabalho do arquivo: " + e.getMessage());
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler valor do trabalho: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler valor do trabalho.");
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler número de integrantes do trabalho: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler número de integrantes do trabalho.");
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
        } catch (IllegalArgumentException e) { // Captura erro de formato em arquivo
            System.err.println("Erro ao ler número de grupos do trabalho: " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.err.println("Fim do arquivo ao ler número de grupos do trabalho.");
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
                    System.err.println("Grupo de trabalho " + (i + 1) + " não foi configurado devido a erro/fim de arquivo.");
                    // Se o grupo não pôde ser lido (EOF ou erro de formato), a avaliação estará incompleta.
                    // Para dados de arquivo, isso é um erro no script, então não repete.
                    // Apenas registra o erro e continua para o próximo grupo (se houver).
                }
            } catch (RuntimeException e) { // Captura RunTimeException de lerGrupoTrabalho (formato/EOF)
                System.err.println("Erro ao configurar grupo de trabalho " + (i + 1) + ": " + e.getMessage());
                System.err.println("Este grupo pode não ter sido adicionado. Por favor, verifique os dados no arquivo.");
            }
        }
        return t;
    }

    public Avaliacao[] lerAvaliacoes(Sistema s, Aluno[] alunos, int nAvaliacoes) { // Usado em cadTurma
        Avaliacao[] avaliacoes = new Avaliacao[nAvaliacoes];

        for (int i = 0; i < nAvaliacoes; i++) {
            if (this.isSystemIn) {
                System.out.println("\nConfigurando Avaliação " + (i + 1) + " de " + nAvaliacoes + ":");
                System.out.println("Escolha um tipo de avaliação: 1) Prova 2) Trabalho");
            }
            String tipoStr;
            try {
                tipoStr = this.lerLinha("Opção: "); // Agora lê a TAG ("PROV" ou "TRAB")
            } catch (IllegalStateException e) {
                System.err.println("Fim do arquivo ao ler tipo de avaliação para Avaliação " + (i + 1) + ". Esta avaliação não será configurada.");
                // Retornar um array parcial; cadTurma deve verificar o tamanho final do array vs. nAvaliacoes
                return avaliacoes;
            }

            // Mapeia a TAG para um inteiro interno para usar a lógica existente
            int tipo = -1;
            if (tipoStr.equalsIgnoreCase("PROV")) {
                tipo = 1;
            } else if (tipoStr.equalsIgnoreCase("TRAB")) {
                tipo = 2;
            } else {
                System.out.println("Opção inválida. Comando esperado 'PROV' ou 'TRAB', mas recebido: '" + tipoStr + "'");
                if (this.isSystemIn) {
                    System.out.println("Por favor, tente novamente esta avaliação.");
                    i--; // Repete a posição para entrada interativa
                } else {
                    System.err.println("Erro de dados no arquivo: Tipo de avaliação desconhecido: '" + tipoStr + "' para Avaliação " + (i + 1));
                    // Em arquivo, um tipo inválido é um erro de script; não repete, talvez invalida a turma
                    return avaliacoes; // Retorna o que conseguiu ler até agora.
                }
                continue; // Continua o loop para re-tentar
            }

            try {
                if (tipo == 1) { // PROV
                    avaliacoes[i] = this.lerProva(s, alunos);
                } else { // TRAB
                    avaliacoes[i] = this.lerTrabalho(s, alunos);
                }
                if (avaliacoes[i] == null) {
                    System.err.println("Avaliação " + (i + 1) + " não foi configurada devido a erro/fim de arquivo/dados incompletos.");
                    if (this.isSystemIn) {
                        System.out.println("Por favor, tente novamente esta avaliação.");
                        i--; // Repete a posição para entrada interativa
                    } else {
                        // Em arquivo, se lerProva/lerTrabalho retornarem null, é um erro de dados.
                        // Podemos quebrar aqui ou tentar continuar. Para um script, é melhor parar.
                        System.err.println("Erro de dados crítico na Avaliação " + (i+1) + " do tipo '" + tipoStr + "'. Interrompendo leitura de avaliações para esta turma.");
                        return avaliacoes; // Retorna avaliações lidas até aqui, as restantes ficam null
                    }
                }
            } catch (RuntimeException e) { // Captura exceções lançadas por lerProva/lerTrabalho (ex: IllegalArgumentException, IllegalStateException)
                System.err.println("Erro ao configurar avaliação " + (i + 1) + ": " + e.getMessage());
                if (this.isSystemIn) {
                    System.out.println("Por favor, tente novamente esta avaliação.");
                    i--;
                } else {
                    System.err.println("Erro de dados crítico em Avaliação " + (i+1) + " do tipo '" + tipoStr + "'. Interrompendo leitura de avaliações para esta turma.");
                    return avaliacoes; // Retorna avaliações lidas até aqui
                }
            }
        }
        return avaliacoes;
    }
}