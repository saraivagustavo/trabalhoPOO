package Notas;

public class Professor extends Pessoa {
    private double salario;

    // Construtor da classe Professor
    public Professor(String nome, String cpf, double salario) {
        super(nome, cpf); // Chama o construtor da classe Pessoa, que já valida nome e cpf.

        // Validação do salário
        if (salario < 0) { // O salário não deve ser negativo
            throw new IllegalArgumentException("O salário do professor não pode ser um valor negativo.");
        }
        this.salario = salario;
    }
}