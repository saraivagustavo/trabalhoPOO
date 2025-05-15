package Notas;

import java.util.ArrayList;
import java.util.List;


public class GrupoTrabalho {
    private double nota;
    private List<Aluno> alunos;

    public GrupoTrabalho() {
        this.alunos = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        if (!alunoNoGrupo(aluno.getCpf())) {
            this.alunos.add(aluno);
        }
    }

    public boolean alunoNoGrupo(String cpf) {
        for (Aluno aluno : this.alunos) {
            if (aluno.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
