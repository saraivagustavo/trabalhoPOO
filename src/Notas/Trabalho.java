package Notas;

import java.util.ArrayList;
import java.util.List;

public class Trabalho extends Avaliacao {
    private int nIntegrantes;
    private List<GrupoTrabalho> grupos;

    // Construtor da classe Trabalho
    public Trabalho(String nome, Data dtAplicacao, double valor, int nIntegrantes) {
        super(nome, dtAplicacao, valor);

        // Validação do número de integrantes
        if (nIntegrantes <= 0) {
            throw new IllegalArgumentException("O número de integrantes do trabalho deve ser maior que zero.");
        }
        this.nIntegrantes = nIntegrantes;
        this.grupos = new ArrayList<>();
    }

    // Adiciona um grupo de trabalho à avaliação
    public void adicionarGrupo(GrupoTrabalho grupo) {
        // Validação do grupo
        if (grupo == null) {
            throw new IllegalArgumentException("Não é possível adicionar um grupo de trabalho nulo.");
        }
        this.grupos.add(grupo);
    }

    // Método sobrescrito para calcular a nota do aluno
    @Override
    public double nota(String cpf) {
        // Validação do CPF de entrada
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio para buscar a nota.");
        }

        for (GrupoTrabalho grupo : grupos) {
            if (grupo.alunoNoGrupo(cpf)) {
                return grupo.getNota();
            }
        }
        return 0;
    }
}