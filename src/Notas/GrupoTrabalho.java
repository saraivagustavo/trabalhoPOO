package Notas;

import java.util.ArrayList;
import java.util.List;

public class GrupoTrabalho implements ICalculavel { // Implementa a interface ICalculavel
    private double nota;
    private List<Aluno> alunos;

    public GrupoTrabalho() {
        this.alunos = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno) {
        // Validação do parâmetro 'aluno'
        if (aluno == null) {
            throw new IllegalArgumentException("Não é possível adicionar um aluno nulo ao grupo de trabalho.");
        }
        if (!alunoNoGrupo(aluno.getCpf())) {
            this.alunos.add(aluno);
        } else {
            throw new IllegalArgumentException("Aluno com CPF " + aluno.getCpf() + " já está presente neste grupo de trabalho.");
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

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setNota(double nota) {
        // Validação da nota
        if (nota < 0) {
            throw new IllegalArgumentException("A nota do grupo de trabalho não pode ser um valor negativo.");
        }
        this.nota = nota;
    }

    @Override
    public double getValorCalculado() {
        return nota;
    }
}