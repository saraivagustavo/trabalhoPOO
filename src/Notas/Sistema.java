package Notas;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Professor> profs;
    private List<Aluno> alunos;
    private List<Turma> turmas;

    public Sistema() {
        this.profs = new ArrayList<>();
        this.alunos = new ArrayList<>();
        this.turmas = new ArrayList<>();
    }

    private boolean existeCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false; // CPFs nulos ou vazios não "existem" para fins de duplicidade
        }
        // Verifica na lista de professores
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return true;
            }
        }
        // Verifica na lista de alunos
        for (Aluno a : this.alunos) {
            if (a.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    // *******************************************************
    // ************** MÉTODOS DE PROFESSORES *****************
    // *******************************************************

    /**
     * Adiciona um novo professor ao sistema
     * @param p O objeto Professor a ser adicionado.
     * @throws IllegalArgumentException se o professor for nulo ou se já existir um professor com o mesmo CPF.
     */
    public void novoProf(Professor p) {
        if (p == null) {
            throw new IllegalArgumentException("Não é possível adicionar um professor nulo ao sistema.");
        }
        if (existeCpf(p.getCpf())) {
            throw new IllegalArgumentException("Já existe uma pessoa (professor ou aluno) cadastrada com este CPF: " + p.getCpf());
        }
        this.profs.add(p);
    }

    /**
     * Busca um professor pelo CPF
     */
    public Professor encontrarProfessor(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            // Decisão: lançar exceção ou retornar null para CPF inválido na busca?
            // Para métodos de busca, retornar null é geralmente mais amigável.
            return null;
        }
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Lista todos os professores cadastrados
     */
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

    /**
     * Adiciona um novo aluno ao sistema
     * @param a O objeto Aluno a ser adicionado.
     * @throws IllegalArgumentException se o aluno for nulo, ou se já existir um aluno com a mesma matrícula ou CPF.
     */
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

    /**
     * Busca um aluno pela matrícula
     */
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

    /**
     * Lista todos os alunos cadastrados
     */
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

    /**
     * Adiciona uma nova turma ao sistema
     * @param t O objeto Turma a ser adicionado.
     * @throws IllegalArgumentException se a turma for nula ou se já existir uma turma com o mesmo nome, ano e semestre.
     */
    public void novaTurma(Turma t) {
        if (t == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma turma nula ao sistema.");
        }
        // Verificação de duplicidade de turma (nome + ano + semestre)
        for (Turma existingTurma : this.turmas) {
            if (existingTurma.getNome().equalsIgnoreCase(t.getNome()) && // Comparação case-insensitive para o nome
                    existingTurma.getAno() == t.getAno() &&
                    existingTurma.getSemestre() == t.getSemestre()) {
                throw new IllegalArgumentException("Já existe uma turma cadastrada com o nome '" + t.getNome() +
                        "' para o ano " + t.getAno() + " e semestre " + t.getSemestre() + ".");
            }
        }
        this.turmas.add(t);
    }

    /**
     * Lista todas as turmas e chama o método medias()
     */
    public void listarTurmas() {
        if (!this.turmas.isEmpty()) {
            System.out.println("Turmas cadastradas:");

            for (Turma t : this.turmas) {
                System.out.println("* " + t.getNome() + " (" + t.getAno() + "/" + t.getSemestre() + ")");
            }

            System.out.println(); // linha em branco entre listagem e médias

            // Chama o método medias() de cada turma
            for (Turma t : this.turmas) {
                try {
                    t.medias(); // Calcula e exibe as médias da turma
                } catch (Exception e) { // Captura qualquer erro ao calcular médias de uma turma
                    System.err.println("Erro ao calcular médias da turma " + t.getNome() + ": " + e.getMessage());
                }
                System.out.println(); // só pra deixar o print bunitin
            }

        } else {
            System.out.println("Nenhuma turma cadastrada.");
        }
    }
}


