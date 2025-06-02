package Notas;

public class Pessoa {
    protected String nome;
    protected String cpf;

    // Construtor da classe Pessoa
    public Pessoa(String nome, String cpf) {
        // Validação do nome
        if (nome == null) {
            throw new IllegalArgumentException("O nome da pessoa não pode ser nulo ou vazio.");
        }
        this.nome = nome;

        // Validação do CPF
        if (cpf == null) {
            throw new IllegalArgumentException("O CPF da pessoa não pode ser nulo ou vazio.");
        }
        this.cpf = cpf;
    }

    // Método toString sobrescrito para exibir informações da pessoa
    @Override
    public String toString() {
        return nome + " (CPF: " + this.cpf + ")";
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}