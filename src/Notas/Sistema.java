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

    // *******************************************************
    // ************** MÉTODOS DE PROFESSORES *****************
    // *******************************************************

    /**
     * Adiciona um novo professor ao sistema
     */
    public void novoProf(Professor p) {
        this.profs.add(p); // Adiciona o professor à lista de professores
    }

    /**
     * Busca um professor pelo CPF
     */
    public Professor encontrarProfessor(String cpf) {
        for (Professor p : this.profs) {
            if (p.getCpf().equals(cpf)) {
                return p; // Retorna o professor encontrado
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
                System.out.println("* " + p); // Exibe informações do professor
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
     */
    public void novoAluno(Aluno a) {
        this.alunos.add(a); // Adiciona o aluno à lista de alunos
    }

    /**
     * Busca um aluno pela matrícula
     */
    public Aluno encontrarAluno(String matricula) {
        for (Aluno a : this.alunos) {
            if (a.getMat().equals(matricula)) {
                return a; // Retorna o aluno encontrado
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
                System.out.println("* " + a); // Exibe informações do aluno
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
     */
    public void novaTurma(Turma t) {
        this.turmas.add(t); // Adiciona a turma à lista de turmas
    }

    /**
     * Lista todas as turmas e chama o método medias()
     */
    public void listarTurmas() {
        if (!this.turmas.isEmpty()) {
            System.out.println("Turmas cadastradas:");

            for (Turma t : this.turmas) {
                System.out.println("* " + t); // Exibe informações da turma
            }

            System.out.println(); // linha em branco entre listagem e médias

            // Chama o método medias() de cada turma
            for (Turma t : this.turmas) {
                t.medias(); // Calcula e exibe as médias da turma
                System.out.println(); // só pra deixar o print bunitin
            }

        } else {
            System.out.println("Nenhuma turma cadastrada.");
        }
    }
}