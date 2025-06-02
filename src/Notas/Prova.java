package Notas;

import java.util.ArrayList;
import java.util.List;

public class Prova extends Avaliacao {
    private int nQuestoes;
    private List<AlunoProva> alunosProvas;

    // Construtor da classe Prova
    public Prova(String nome, Data dtAplicacao, double valor, int nQuestoes) {
        super(nome, dtAplicacao, valor);

        // Validação do número de questões
        if (nQuestoes <= 0) {
            throw new IllegalArgumentException("O número de questões da prova deve ser maior que zero.");
        }
        this.nQuestoes = nQuestoes;
        this.alunosProvas = new ArrayList<>();
    }

    // Adiciona um aluno à prova (ou seja, um registro AlunoProva)
    public void adicionarAlunoProva(AlunoProva ap) {
        // Validação do objeto AlunoProva
        if (ap == null) {
            throw new IllegalArgumentException("Não é possível adicionar um registro de AlunoProva nulo.");
        }
        this.alunosProvas.add(ap);
    }

    // Método sobrescrito para calcular a nota total do aluno nesta prova
    @Override
    public double nota(String cpf) {
        // Validação do CPF de entrada
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio para buscar a nota.");
        }

        for (AlunoProva ap : alunosProvas) {
            if (ap.getAluno().getCpf().equals(cpf)) {
                return ap.notaTotal();
            }
        }
        return 0;
    }
}