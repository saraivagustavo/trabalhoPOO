package Notas;

public class Professor extends Pessoa{
    private double salario;

    // Construtor da classe Professor
    public Professor(String nome, String cpf, double salario) {
        super(nome, cpf);
        this.salario = salario;
    }

}
