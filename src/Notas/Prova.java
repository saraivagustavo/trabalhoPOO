package Notas;

import java.util.ArrayList;
import java.util.List;

public class Prova extends Avaliacao {
    private int nQuestoes;
    private List<AlunoProva> alunosProvas;

    // Construtor da classe Prova
    public Prova(String nome, Data dtAplicacao, double valor, int nQuestoes) {
        super(nome, dtAplicacao, valor);
        this.nQuestoes = nQuestoes;
        this.alunosProvas = new ArrayList<>();
    }

    // Adiciona um aluno à prova
    public void adicionarAlunoProva(AlunoProva ap) {
        alunosProvas.add(ap);
    }

    // Adiciona a nota do aluno na prova
    @Override
    public double nota(String cpf) {
        for (AlunoProva ap : alunosProvas) {
            if (ap.getAluno().getCpf().equals(cpf)) {
                return ap.notaTotal();
            }
        }
        return 0;
    }
}