package Notas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Turma {
    private String nome;
    private int ano;
    private int semestre;
    private Professor prof;
    private List<Aluno> alunos;
    private List<Avaliacao> avs;


    //Construtor da classe Turma
    public Turma(String nome, int ano, int semestre, Professor prof, Aluno[] alunosArray, Avaliacao[] avsArray) {
        this.nome = nome;
        this.ano = ano;
        this.semestre = semestre;
        this.prof = prof;

        this.alunos = new ArrayList<>();
        if (alunosArray != null) {
            this.alunos.addAll(Arrays.asList(alunosArray));
        }

        this.avs = new ArrayList<>();
        if (avsArray != null) {
            this.avs.addAll(Arrays.asList(avsArray));
        }
    }

    public String getNome() {
        return this.nome;
    }

    //Método para calcular a média da turma
    public void medias() {
        System.out.println("Médias da Turma " + this.nome + "(" + this.ano + "/" + this.semestre + "):");

        double somaMedias = 0;


        for (Aluno aluno : alunos) { // Itera sobre cada aluno
            double mediaAluno = 0;
            System.out.print(aluno.toString() + ": ");

            for (Avaliacao aval : avs) { // Itera sobre cada avaliação
                double nota = aval.nota(aluno.getCpf());
                mediaAluno += nota;
                System.out.print(nota + " ");
            }

            //Soma máxima possível do aluno
            double maxTotal = 0;
            for (Avaliacao aval : avs) {
                maxTotal += aval.getValor();
            }

            //Garantir que a média não ultrapasse o máximo total
            if (mediaAluno > maxTotal) {
                mediaAluno = maxTotal;
            }

            //Garantir que a média não passe de 100 pontos
            if (mediaAluno > 100) {
                mediaAluno = 100;
            }

            System.out.println("= " + mediaAluno);
            somaMedias += mediaAluno;
        }

        double mediaTurma = somaMedias / alunos.size(); // Calcula a média da turma
        System.out.printf("Média da turma: %.2f%n", mediaTurma);
    }
}
