package Notas;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner; // Importar Scanner

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
                    // desempate1. Semestre do mais recente para o mais antigo
                    int compareSemestre = Integer.compare(t2.getSemestre(), t1.getSemestre());
                    if (compareSemestre != 0) {
                        return compareSemestre;
                    }
                    // desempate2. Ano do mais recente para o mais antigo
                    int compareAno = Integer.compare(t2.getAno(), t1.getAno());
                    if (compareAno != 0) {
                        return compareAno;
                    }
                    // desempate3. Nome da disciplina crescente
                    int compareNomeDisciplina = t1.getNome().compareToIgnoreCase(t2.getNome());
                    if (compareNomeDisciplina != 0) {
                        return compareNomeDisciplina;
                    }
                    // desempate4. Nome do professor crescente
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

}