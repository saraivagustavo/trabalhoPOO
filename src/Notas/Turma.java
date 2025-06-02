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
        //Validação do nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da turma não pode ser nulo ou vazio.");
        }
        this.nome = nome;

        //Validação do ano
        if (ano <= 0) { // Ano deve ser positivo
            throw new IllegalArgumentException("O ano da turma deve ser um valor positivo.");
        }
        this.ano = ano;

        //Validação do semestre
        if (semestre != 1 && semestre != 2) {
            throw new IllegalArgumentException("O semestre da turma deve ser 1 ou 2.");
        }
        this.semestre = semestre;

        //Validação do professor
        if (prof == null) {
            throw new IllegalArgumentException("O professor da turma não pode ser nulo.");
        }
        this.prof = prof;

        //Inicialização e validação dos alunos
        this.alunos = new ArrayList<>();
        if (alunosArray != null) {
            for (Aluno aluno : alunosArray) {
                if (aluno == null) {
                    throw new IllegalArgumentException("A lista de alunos contém um aluno nulo. Todos os alunos devem ser válidos.");
                }
                this.alunos.add(aluno);
            }
        }

        //Inicialização e validação das avaliações
        this.avs = new ArrayList<>();
        if (avsArray != null) {
            for (Avaliacao avaliacao : avsArray) {
                if (avaliacao == null) {
                    throw new IllegalArgumentException("A lista de avaliações contém uma avaliação nula. Todas as avaliações devem ser válidas.");
                }
                this.avs.add(avaliacao);
            }
        }
    }

    public int getSemestre() {
        return this.semestre;
    }

    public int getAno() {
        return this.ano;
    }

    public String getNome() {
        return this.nome;
    }

    //Método para calcular a média da turma
    public void medias() {
        System.out.println("Médias da Turma " + this.nome + "(" + this.ano + "/" + this.semestre + "):");

        //Tratamento de divisão por zero
        if (alunos.isEmpty()) {
            System.out.println("Não há alunos nesta turma para calcular as médias.");
            return; // Sai do método se não houver alunos
        }

        double somaMedias = 0;

        for (Aluno aluno : alunos) {
            double mediaAluno = 0;
            System.out.print(aluno.toString() + ": ");

            for (Avaliacao aval : avs) {
                try {
                    double nota = aval.nota(aluno.getCpf());
                    mediaAluno += nota;
                    System.out.print(nota + " ");
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao obter nota para " + aluno.getNome() + " na avaliação " + aval.getNome() + ": " + e.getMessage());
                    System.out.print("0 (Erro) "); // Mostra que houve um erro na nota
                }
            }

            double maxTotal = 0;
            for (Avaliacao aval : avs) {
                if (aval != null) {
                    maxTotal += aval.getValor();
                }
            }

            if (mediaAluno > maxTotal) {
                mediaAluno = maxTotal;
            }

            if (mediaAluno > 100) {
                mediaAluno = 100;
            }

            System.out.println("= " + String.format("%.2f", mediaAluno));
            somaMedias += mediaAluno;
        }

        double mediaTurma = somaMedias / alunos.size();
        System.out.printf("Média da turma: %.2f%n", mediaTurma);
    }
}