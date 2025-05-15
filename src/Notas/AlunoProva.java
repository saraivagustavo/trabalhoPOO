package Notas;


import java.util.ArrayList;
import java.util.List;

public class AlunoProva {
    private Aluno aluno;
    private List<Double> notas;

    // Construtor da classe AlunoProva
    public AlunoProva(Aluno aluno) {
        this.aluno = aluno;
        this.notas = new ArrayList<>();
    }

    // Adiciona uma nota ao aluno
    public void adicionarNota(double nota) {
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