package Notas;

import java.util.ArrayList;
import java.util.List;

public class AlunoProva implements ICalculavel {
    private Aluno aluno;
    private List<Double> notas;

    // Construtor da classe AlunoProva
    public AlunoProva(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo para criar um registro de prova.");
        }
        this.aluno = aluno;
        this.notas = new ArrayList<>();
    }

    // Adiciona uma nota ao aluno
    public void adicionarNota(double nota) {
        if (nota < 0) {
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

    // Implementação do método da interface ICalculavel
    @Override
    public double getValorCalculado() {
        return notaTotal();
    }

    //Getter para o aluno
    public Aluno getAluno() {
        return aluno;
    }

    //Getter para a lista de notas
    public List<Double> getNotas() {
        return notas;
    }
}