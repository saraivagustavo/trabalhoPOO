package Notas;

public class Pessoa {
    protected String nome;
    protected String cpf;

    // Construtor da classe Pessoa
    public Pessoa(String nome, String cpf) {
        this.nome = nome;
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
