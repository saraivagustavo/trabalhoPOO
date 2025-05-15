package Notas;

import java.util.ArrayList;
import java.util.List;

public class Trabalho extends Avaliacao {
    private int nIntegrantes;
    private List<GrupoTrabalho> grupos;

    // Construtor da classe Trabalho
    public Trabalho(String nome, Data dtAplicacao, double valor, int nIntegrantes) {
        super(nome, dtAplicacao, valor);
        this.nIntegrantes = nIntegrantes;
        this.grupos = new ArrayList<>();
    }

    // Adiciona um grupo de trabalho à avaliação
    public void adicionarGrupo(GrupoTrabalho grupo) {
        grupos.add(grupo);
    }

    // Método sobrescrito para calcular a nota do aluno
    @Override
    public double nota(String cpf) {
        for (GrupoTrabalho grupo : grupos) {
            if (grupo.alunoNoGrupo(cpf)) {
                return grupo.getNota();
            }
        }
        return 0;
    }
}