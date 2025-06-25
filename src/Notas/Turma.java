package Notas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Turma {
    private String nome;
    private int ano;
    private int semestre;
    protected Professor prof; // Alterado para protected para acesso em subclasses se necessário, mantendo compatibilidade
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

    public Professor getProf() {
        return prof;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public List<Avaliacao> getAvs() {
        return avs;
    }

    // Método para calcular a média da turma
    public void medias() {
        System.out.println("Médias da Turma " + this.nome + "(" + this.ano + "/" + this.semestre + "):");

        if (alunos.isEmpty()) {
            System.out.println("Não há alunos nesta turma para calcular as médias.");
            return;
        }

        // Mapa para armazenar a média final de cada aluno
        Map<Aluno, Double> mediasPorAluno = new HashMap<>();
        double somaMediasGerais = 0; // Para a média da turma

        for (Aluno aluno : alunos) {
            double mediaAlunoAtual = 0;
            for (Avaliacao aval : avs) {
                try {
                    double nota = aval.nota(aluno.getCpf());
                    mediaAlunoAtual += nota;
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro ao obter nota para " + aluno.getNome() + " na avaliação " + aval.getNome() + ": " + e.getMessage());
                }
            }

            double maxTotalAvaliacoes = 0;
            for (Avaliacao aval : avs) {
                if (aval != null) {
                    maxTotalAvaliacoes += aval.getValor();
                }
            }

            // Garante que a média do aluno não exceda o valor máximo das avaliações ou 100
            if (mediaAlunoAtual > maxTotalAvaliacoes) {
                mediaAlunoAtual = maxTotalAvaliacoes;
            }
            if (mediaAlunoAtual > 100) {
                mediaAlunoAtual = 100;
            }

            mediasPorAluno.put(aluno, mediaAlunoAtual); // Armazena a média final no mapa
            somaMediasGerais += mediaAlunoAtual; // Soma para calcular a média geral da turma
        }

        // Cria uma lista mutável de alunos para ser ordenada
        List<Aluno> alunosOrdenados = new ArrayList<>(this.alunos);

        // Ordena a lista de alunos usando um Comparator que consulta o mapa de médias
        Collections.sort(alunosOrdenados, new Comparator<Aluno>() {
            @Override
            public int compare(Aluno a1, Aluno a2) {
                // Obtém as médias finais do mapa
                Double media1 = mediasPorAluno.getOrDefault(a1, 0.0);
                Double media2 = mediasPorAluno.getOrDefault(a2, 0.0);

                // 1. Nota final da maior para a menor (decrescente)
                int compareNota = Double.compare(media2, media1); // media2 vs media1 para ordem decrescente
                if (compareNota != 0) {
                    return compareNota;
                }

                // 2. Nome do aluno em ordem crescente
                int compareNome = a1.getNome().compareToIgnoreCase(a2.getNome());
                if (compareNome != 0) {
                    return compareNome;
                }

                // 3. Matrícula do aluno em ordem crescente
                return a1.getMat().compareToIgnoreCase(a2.getMat());
            }
        });

        // Itera sobre a lista de alunos ordenados para exibir os resultados
        for (Aluno aluno : alunosOrdenados) {
            System.out.print(aluno.toString() + ": ");

            for (Avaliacao aval : avs) {
                try {
                    double nota = aval.nota(aluno.getCpf());
                    System.out.print(String.format("%.1f", nota) + " ");
                } catch (IllegalArgumentException e) {
                    System.out.print("0 (Erro) "); // Exibe 0 (Erro) se a nota não puder ser obtida
                }
            }
            // Obtém a média final calculada do mapa para impressão
            System.out.println(String.format("= %.2f", mediasPorAluno.getOrDefault(aluno, 0.0)));
        }

        double mediaTurma = somaMediasGerais / alunos.size();
        System.out.printf("Média da turma: %.2f%n", mediaTurma);
    }
}