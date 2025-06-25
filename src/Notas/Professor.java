package Notas;

public class Professor extends Pessoa implements ITextoFormatavel<Professor> { // Implementa a nova interface
    private double salario;

    // Construtor da classe Professor
    public Professor(String nome, String cpf, double salario) {
        super(nome, cpf);
        if (salario < 0) {
            throw new IllegalArgumentException("O salário do professor não pode ser um valor negativo.");
        }
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toTextFormat() {
        // Define como um Professor será salvo como uma linha de texto
        return getNome() + ";" + getCpf() + ";" + salario;
    }
}