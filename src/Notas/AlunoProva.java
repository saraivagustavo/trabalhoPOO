package Notas;

import java.util.ArrayList;
import java.util.List;

public class AlunoProva {
    private Aluno aluno;
    private List<Double> notas;

    // Construtor da classe AlunoProva
    public AlunoProva(Aluno aluno) {
        if (aluno == null) {
            // Lançar uma exceção para indicar que o aluno não pode ser nulo
            throw new IllegalArgumentException("O aluno não pode ser nulo para criar um registro de prova.");
        }
        this.aluno = aluno;
        this.notas = new ArrayList<>();
    }

    // Adiciona uma nota ao aluno
    public void adicionarNota(double nota) {
        if (nota < 0) {
            // Lançar uma exceção para indicar que a nota é inválida
            throw new IllegalArgumentException("A nota não pode ser um valor negativo.");
        }
        this.notas.add(nota);
    }

    // Método para calcular a nota total do aluno
    public double notaTotal() {
        double soma = 0;
        for (Double d : notas) {
            soma += d;
        }
        return soma;
    }

    //Getter para o aluno
    public Aluno getAluno() {
        return aluno;
    }
}