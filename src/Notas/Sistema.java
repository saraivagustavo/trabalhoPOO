package Notas;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    public List<Professor> getProfs() {
        return profs;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void resetSistema() {
        this.profs.clear();
        this.alunos.clear();
        this.turmas.clear();
        System.out.println("Sistema resetado para carregar novos dados.");
    }

    // *******************************************************
    // ************** MÉTODOS DE PROFESSORES *****************
    // *******************************************************
    private boolean existeCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return true;
            }
        }
        for (Aluno a : this.alunos) {
            if (a.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public void novoProf(Professor p) {
        if (p == null) {
            throw new IllegalArgumentException("Não é possível adicionar um professor nulo ao sistema.");
        }
        if (existeCpf(p.getCpf())) {
            throw new IllegalArgumentException("Já existe uma pessoa (professor ou aluno) cadastrada com este CPF: " + p.getCpf());
        }
        this.profs.add(p);
    }

    public Professor encontrarProfessor(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return null;
        }
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    public void listarProfs() {
        if (!this.profs.isEmpty()) {
            System.out.println("Professores cadastrados:");
            for (Professor p : this.profs) {
                System.out.println("* " + p);
            }
        } else {
            System.out.println("Nenhum professor cadastrado até o momento.");
        }
    }

    // *******************************************************
    // **************** MÉTODOS DE ALUNOS ********************
    // *******************************************************

    public void novoAluno(Aluno a) {
        if (a == null) {
            throw new IllegalArgumentException("Não é possível adicionar um aluno nulo ao sistema.");
        }
        if (encontrarAluno(a.getMat()) != null) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com esta matrícula: " + a.getMat());
        }
        if (existeCpf(a.getCpf())) {
            throw new IllegalArgumentException("Já existe uma pessoa (professor ou aluno) cadastrada com este CPF: " + a.getCpf());
        }
        this.alunos.add(a);
    }

    public Aluno encontrarAluno(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }
        for (Aluno a : this.alunos) {
            if (a.getMat().equals(matricula)) {
                return a;
            }
        }
        return null;
    }

    public void listarAlunos() {
        if (!this.alunos.isEmpty()) {
            System.out.println("Alunos cadastrados:");
            for (Aluno a : this.alunos) {
                System.out.println("* " + a);
            }
        } else {
            System.out.println("Nenhum aluno cadastrado até o momento.");
        }
    }

    // *******************************************************
    // **************** MÉTODOS DE TURMAS ********************
    // *******************************************************

    public void novaTurma(Turma t) {
        if (t == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma turma nula ao sistema.");
        }
        for (Turma existingTurma : this.turmas) {
            if (existingTurma.getNome().equalsIgnoreCase(t.getNome()) &&
                    existingTurma.getAno() == t.getAno() &&
                    existingTurma.getSemestre() == t.getSemestre()) {
                throw new IllegalArgumentException("Já existe uma turma cadastrada com o nome '" + t.getNome() +
                        "' para o ano " + t.getAno() + " e semestre " + t.getSemestre() + ".");
            }
        }
        this.turmas.add(t);
    }

    public void listarTurmas() {
        if (!this.turmas.isEmpty()) {
            System.out.println("Turmas cadastradas:");

            List<Turma> turmasOrdenadas = new ArrayList<>(this.turmas);
            Collections.sort(turmasOrdenadas, new Comparator<Turma>() {
                @Override
                public int compare(Turma t1, Turma t2) {
                    int compareSemestre = Integer.compare(t2.getSemestre(), t1.getSemestre());
                    if (compareSemestre != 0) {
                        return compareSemestre;
                    }
                    int compareAno = Integer.compare(t2.getAno(), t1.getAno());
                    if (compareAno != 0) {
                        return compareAno;
                    }
                    int compareNomeDisciplina = t1.getNome().compareToIgnoreCase(t2.getNome());
                    if (compareNomeDisciplina != 0) {
                        return compareNomeDisciplina;
                    }
                    return t1.getProf().getNome().compareToIgnoreCase(t2.getProf().getNome());
                }
            });

            for (Turma t : turmasOrdenadas) {
                System.out.println("* " + t.getNome() + " (" + t.getAno() + "/" + t.getSemestre() + ") - Prof: " + t.getProf().getNome());
            }

            System.out.println();
            for (Turma t : turmasOrdenadas) {
                try {
                    t.medias();
                } catch (Exception e) {
                    System.err.println("Erro ao calcular médias da turma " + t.getNome() + ": " + e.getMessage());
                }
                System.out.println();
            }

        } else {
            System.out.println("Nenhuma turma cadastrada.");
        }
    }


    // *******************************************************
    // ************ MÉTODOS DE PERSISTÊNCIA EM dados.txt ***********
    // *******************************************************

    public void salvarSistema(String fileName, Entrada io) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Professor p : profs) {
                writer.write("PROF"); writer.newLine();
                writer.write(p.getNome()); writer.newLine();
                writer.write(p.getCpf()); writer.newLine();
                writer.write(String.valueOf(p.getSalario())); writer.newLine();
            }

            for (Aluno a : alunos) {
                writer.write("ALU"); writer.newLine();
                writer.write(a.getNome()); writer.newLine();
                writer.write(a.getCpf()); writer.newLine();
                writer.write(a.getMat()); writer.newLine();
            }

            for (Turma t : turmas) {
                writer.write("TUR"); writer.newLine();
                writer.write(t.getNome()); writer.newLine();
                writer.write(String.valueOf(t.getAno())); writer.newLine();
                writer.write(String.valueOf(t.getSemestre())); writer.newLine();
                writer.write(t.getProf().getCpf()); writer.newLine();

                writer.write(String.valueOf(t.getAlunos().size())); writer.newLine();
                for (Aluno alunoTurma : t.getAlunos()) {
                    writer.write(alunoTurma.getMat()); writer.newLine();
                }

                writer.write(String.valueOf(t.getAvs().size())); writer.newLine();
                for (Avaliacao aval : t.getAvs()) {
                    if (aval instanceof Prova) {
                        Prova prova = (Prova) aval;
                        writer.write("PROV"); writer.newLine();
                        writer.write(prova.getNome()); writer.newLine();
                        writer.write(String.valueOf(prova.getData().getDia())); writer.newLine();
                        writer.write(String.valueOf(prova.getData().getMes())); writer.newLine();
                        writer.write(String.valueOf(prova.getData().getAno())); writer.newLine();
                        writer.write(String.valueOf(prova.getValor())); writer.newLine();
                        writer.write(String.valueOf(prova.getNQuestoes())); writer.newLine();

                        for (AlunoProva ap : prova.getAlunosProvas()) {
                            for (Double notaQuestao : ap.getNotas()) {
                                writer.write(String.valueOf(notaQuestao)); writer.newLine();
                            }
                        }

                    } else if (aval instanceof Trabalho) {
                        Trabalho trabalho = (Trabalho) aval;
                        writer.write("TRAB"); writer.newLine();
                        writer.write(trabalho.getNome()); writer.newLine();
                        writer.write(String.valueOf(trabalho.getData().getDia())); writer.newLine();
                        writer.write(String.valueOf(trabalho.getData().getMes())); writer.newLine();
                        writer.write(String.valueOf(trabalho.getData().getAno())); writer.newLine();
                        writer.write(String.valueOf(trabalho.getValor())); writer.newLine();
                        writer.write(String.valueOf(trabalho.getNIntegrantes())); writer.newLine();
                        writer.write(String.valueOf(trabalho.getGrupos().size())); writer.newLine();

                        for (GrupoTrabalho gt : trabalho.getGrupos()) {
                            writer.write(String.valueOf(gt.getAlunos().size())); writer.newLine();
                            for (Aluno alunoGrupo : gt.getAlunos()) {
                                writer.write(alunoGrupo.getMat()); writer.newLine();
                            }
                            writer.write(String.valueOf(gt.getNota())); writer.newLine();
                        }
                    }
                }
            }
            writer.write("FIM"); writer.newLine();
            System.out.println("Sistema salvo com sucesso em '" + fileName + "'!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o sistema em '" + fileName + "': " + e.getMessage());
            throw e;
        }
    }

    // *******************************************************
    // ************ MÉTODOS AUXILIARES DE CONSUMO DE DADOS (PARA carregarSistema) ***********
    // *******************************************************

    /**
     * Consome (lê e descarta) os dados de uma avaliação (Prova ou Trabalho)
     * de um Scanner, sem tentar processá-los, para sincronizar o arquivo.
     * @param fileScanner O Scanner do arquivo.
     * @param io A instância de Entrada para usar os métodos de leitura de arquivo.
     * @param tipoAval O tipo da avaliação ("PROV" ou "TRAB").
     * @param numAlunosTurma O número de alunos na turma (necessário para Prova).
     */
    private void consumeAvaliacaoData(Scanner fileScanner, Entrada io, String tipoAval, Integer numAlunosTurma) {
        io.lerLinhaArquivo(fileScanner); // Nome
        io.lerInteiroArquivo(fileScanner); // Dia
        io.lerInteiroArquivo(fileScanner); // Mes
        io.lerInteiroArquivo(fileScanner); // Ano
        io.lerDoubleArquivo(fileScanner); // Valor

        if (tipoAval != null && tipoAval.equalsIgnoreCase("PROV")) {
            Integer nQuestoes = io.lerInteiroArquivo(fileScanner);
            if (nQuestoes != null && numAlunosTurma != null) {
                for (int k = 0; k < numAlunosTurma * nQuestoes; k++) {
                    io.lerDoubleArquivo(fileScanner); // Consome cada nota
                }
            }
        } else if (tipoAval != null && tipoAval.equalsIgnoreCase("TRAB")) {
            io.lerInteiroArquivo(fileScanner); // nIntegrantes
            Integer nGrupos = io.lerInteiroArquivo(fileScanner);
            if (nGrupos != null) {
                for (int j = 0; j < nGrupos; j++) {
                    Integer qtdAlunosGrupo = io.lerInteiroArquivo(fileScanner);
                    if (qtdAlunosGrupo != null) {
                        for (int k = 0; k < qtdAlunosGrupo; k++) {
                            io.lerLinhaArquivo(fileScanner); // Matrícula do aluno no grupo
                        }
                    }
                    io.lerDoubleArquivo(fileScanner); // Nota do grupo
                }
            }
        }
    }

    /**
     * Consome (lê e descarta) todos os dados restantes de uma Turma do Scanner,
     * para sincronizar o arquivo após um erro de carregamento da turma.
     * @param fileScanner O Scanner do arquivo.
     * @param io A instância de Entrada para usar os métodos de leitura de arquivo.
     */
    private void consumeTurmaData(Scanner fileScanner, Entrada io) {
        Integer numAlunos = io.lerInteiroArquivo(fileScanner); // Consome numAlunos
        if (numAlunos != null) {
            for (int i = 0; i < numAlunos; i++) {
                io.lerLinhaArquivo(fileScanner); // Consome matrículas dos alunos
            }
        }

        Integer numAvs = io.lerInteiroArquivo(fileScanner); // Consome numAvs
        if (numAvs != null) {
            for (int i = 0; i < numAvs; i++) {
                String tipoAval = io.lerLinhaArquivo(fileScanner); // Consome tipo (PROV/TRAB)
                consumeAvaliacaoData(fileScanner, io, tipoAval, numAlunos); // Chama o método auxiliar
            }
        }
    }

    // *******************************************************
    // ************ MÉTODO PARA CARREGAMENTO DE dados.txt - Reimplementado ***********
    // *******************************************************
    public void carregarSistema(String fileName, Entrada io) {
        this.resetSistema(); // Limpa o sistema atual antes de carregar novos dados
        System.out.println("Tentando carregar sistema de '" + fileName + "'...");

        try (Scanner fileScanner = new Scanner(new FileInputStream(fileName))) {
            String tag;
            while ((tag = io.lerLinhaArquivo(fileScanner)) != null) {
                if (tag.equalsIgnoreCase("PROF")) {
                    String nome = io.lerLinhaArquivo(fileScanner);
                    String cpf = io.lerLinhaArquivo(fileScanner);
                    Double salario = io.lerDoubleArquivo(fileScanner);
                    if (nome != null && cpf != null && salario != null) {
                        try {
                            novoProf(new Professor(nome, cpf, salario));
                        } catch (IllegalArgumentException e) {
                            System.err.println("Erro ao carregar professor do arquivo: " + e.getMessage());
                        }
                    } else {
                        System.err.println("Dados incompletos para PROF no arquivo. Pulando para a próxima tag.");
                    }
                } else if (tag.equalsIgnoreCase("ALU")) {
                    String nome = io.lerLinhaArquivo(fileScanner);
                    String cpf = io.lerLinhaArquivo(fileScanner);
                    String matricula = io.lerLinhaArquivo(fileScanner);
                    if (nome != null && cpf != null && matricula != null) {
                        try {
                            novoAluno(new Aluno(nome, cpf, matricula));
                        } catch (IllegalArgumentException e) {
                            System.err.println("Erro ao carregar aluno do arquivo: " + e.getMessage());
                        }
                    } else {
                        System.err.println("Dados incompletos para ALU no arquivo. Pulando para a próxima tag.");
                    }
                } else if (tag.equalsIgnoreCase("TUR")) {
                    String nomeTurma = io.lerLinhaArquivo(fileScanner);
                    Integer ano = io.lerInteiroArquivo(fileScanner);
                    Integer semestre = io.lerInteiroArquivo(fileScanner);
                    String cpfProfessor = io.lerLinhaArquivo(fileScanner);

                    if (nomeTurma == null || ano == null || semestre == null || cpfProfessor == null) {
                        System.err.println("Dados incompletos para TUR (informações básicas) no arquivo. Pulando para a próxima tag.");
                        continue;
                    }

                    Professor profTurma = encontrarProfessor(cpfProfessor);
                    if (profTurma == null) {
                        System.err.println("Professor com CPF " + cpfProfessor + " não encontrado para a turma " + nomeTurma + ". Turma não será carregada. Consumindo dados restantes da turma.");
                        consumeTurmaData(fileScanner, io); // Consume o resto dos dados da turma
                        continue;
                    }

                    List<Aluno> alunosTurma = new ArrayList<>();
                    Integer numAlunos = io.lerInteiroArquivo(fileScanner);
                    if (numAlunos != null) {
                        for (int i = 0; i < numAlunos; i++) {
                            String matAluno = io.lerLinhaArquivo(fileScanner);
                            Aluno alunoAdd = encontrarAluno(matAluno);
                            if (alunoAdd != null) {
                                alunosTurma.add(alunoAdd);
                            } else {
                                System.err.println("Aluno com matrícula " + matAluno + " não encontrado para turma " + nomeTurma + ". Ignorando este aluno.");
                            }
                        }
                    } else {
                        System.err.println("Número de alunos da turma " + nomeTurma + " não encontrado no arquivo. Pulando o restante da turma.");
                        continue;
                    }

                    List<Avaliacao> avaliacoesTurma = new ArrayList<>();
                    Integer numAvs = io.lerInteiroArquivo(fileScanner);
                    if (numAvs != null) {
                        for (int i = 0; i < numAvs; i++) {
                            String tipoAval = io.lerLinhaArquivo(fileScanner);
                            // Renomeadas as variáveis para evalDia, evalMes, evalAno
                            String nomeAval = io.lerLinhaArquivo(fileScanner);
                            Integer evalDia = io.lerInteiroArquivo(fileScanner);
                            Integer evalMes = io.lerInteiroArquivo(fileScanner);
                            Integer evalAno = io.lerInteiroArquivo(fileScanner);
                            Double valor = io.lerDoubleArquivo(fileScanner);

                            if (tipoAval == null || nomeAval == null || evalDia == null || evalMes == null || evalAno == null || valor == null) {
                                System.err.println("Dados básicos de avaliação incompletos para turma " + nomeTurma + ", avaliação " + (i + 1) + ". Consumindo dados restantes da avaliação.");
                                consumeAvaliacaoData(fileScanner, io, tipoAval, numAlunos);
                                continue;
                            }
                            Data dataAval = new Data(evalDia, evalMes, evalAno); // Usando evalDia, evalMes, evalAno

                            if (tipoAval.equalsIgnoreCase("PROV")) {
                                Integer nQuestoes = io.lerInteiroArquivo(fileScanner);
                                if (nQuestoes == null) {
                                    System.err.println("Número de questões da prova não encontrado para turma " + nomeTurma + ", avaliação " + nomeAval + ". Consumindo dados restantes da avaliação.");
                                    consumeAvaliacaoData(fileScanner, io, tipoAval, numAlunos);
                                    continue;
                                }
                                Prova prova = new Prova(nomeAval, dataAval, valor, nQuestoes);
                                for (Aluno alunoProva : alunosTurma) {
                                    AlunoProva ap = new AlunoProva(alunoProva);
                                    for (int q = 0; q < nQuestoes; q++) {
                                        Double notaQuestao = io.lerDoubleArquivo(fileScanner);
                                        if (notaQuestao != null) {
                                            ap.adicionarNota(notaQuestao);
                                        } else {
                                            System.err.println("Nota de questão incompleta para aluno " + alunoProva.getNome() + " na prova " + nomeAval + ". Consumindo notas restantes da avaliação.");
                                            for(int remainingQ = q; remainingQ < nQuestoes; remainingQ++) {
                                                io.lerDoubleArquivo(fileScanner); // Consome para sincronizar
                                            }
                                            break;
                                        }
                                    }
                                    prova.adicionarAlunoProva(ap);
                                }
                                avaliacoesTurma.add(prova);

                            } else if (tipoAval.equalsIgnoreCase("TRAB")) {
                                Integer nIntegrantes = io.lerInteiroArquivo(fileScanner);
                                Integer nGrupos = io.lerInteiroArquivo(fileScanner);
                                if (nIntegrantes == null || nGrupos == null) {
                                    System.err.println("Dados de trabalho incompletos para turma " + nomeTurma + ", avaliação " + nomeAval + ". Consumindo dados restantes da avaliação.");
                                    consumeAvaliacaoData(fileScanner, io, tipoAval, numAlunos);
                                    continue;
                                }
                                Trabalho trabalho = new Trabalho(nomeAval, dataAval, valor, nIntegrantes);
                                for (int g = 0; g < nGrupos; g++) {
                                    GrupoTrabalho gt = new GrupoTrabalho();
                                    Integer qtdAlunosGrupo = io.lerInteiroArquivo(fileScanner);
                                    if (qtdAlunosGrupo == null) {
                                        System.err.println("Quantidade de alunos de grupo incompleta para trabalho " + nomeAval + ", grupo " + (g+1) + ". Consumindo notas restantes do grupo.");
                                        io.lerDoubleArquivo(fileScanner); // Consome a nota do grupo
                                        break;
                                    }
                                    for (int k = 0; k < qtdAlunosGrupo; k++) {
                                        String matAlunoGrupo = io.lerLinhaArquivo(fileScanner);
                                        Aluno alunoGrupo = encontrarAluno(matAlunoGrupo);
                                        if (alunoGrupo != null) {
                                            gt.adicionarAluno(alunoGrupo);
                                        } else {
                                            System.err.println("Aluno " + matAlunoGrupo + " do grupo não encontrado para trabalho " + nomeAval + ". Ignorando.");
                                        }
                                    }
                                    Double notaGrupo = io.lerDoubleArquivo(fileScanner);
                                    if (notaGrupo != null) {
                                        gt.setNota(notaGrupo);
                                    } else {
                                        System.err.println("Nota de grupo incompleta para trabalho " + nomeAval + ", grupo " + (g+1) + ". Ignorando.");
                                    }
                                    trabalho.adicionarGrupo(gt);
                                }
                                avaliacoesTurma.add(trabalho);
                            } else { // Tipo de avaliação desconhecido
                                System.err.println("Tipo de avaliação desconhecido no arquivo: '" + tipoAval + "'. Consumindo dados restantes da avaliação.");
                                consumeAvaliacaoData(fileScanner, io, tipoAval, numAlunos); // Tenta consumir o resto
                                continue; // Pula para a próxima avaliação
                            }
                        }
                    } else {
                        System.err.println("Número de avaliações da turma " + nomeTurma + " não encontrado no arquivo. Pulando o restante da turma.");
                        continue;
                    }

                    try {
                        Turma t = new Turma(nomeTurma, ano, semestre, profTurma, alunosTurma.toArray(new Aluno[0]), avaliacoesTurma.toArray(new Avaliacao[0]));
                        novaTurma(t);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erro ao carregar turma do arquivo: " + e.getMessage());
                    }

                } else if (tag.equalsIgnoreCase("FIM")) {
                    System.out.println("Fim do arquivo de dados.");
                    break;
                } else {
                    System.err.println("Tag desconhecida no arquivo de dados: '" + tag + "'. Pulando para a próxima tag principal.");
                }
            }
            System.out.println("Sistema carregado com sucesso de '" + fileName + "'!");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados '" + fileName + "' não encontrado. Iniciando sistema vazio.");
        } catch (IOException e) {
            System.err.println("Erro de leitura ao carregar o sistema de '" + fileName + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado ao carregar o sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}