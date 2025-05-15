package Notas;

public class Avaliacao {
    protected String nome;
    protected Data dtAplicacao;
    protected double valor;

    // Construtor da classe Avaliacao
    public Avaliacao(String nome, Data dtAplicacao, double valor) {
        this.nome = nome;
        this.dtAplicacao = dtAplicacao;
        this.valor = valor;
    }

    // Método que será sobrescrito nas subclasses
    public double nota(String cpf) {
        return 0; // Valor padrão
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public Data getData() {
        return dtAplicacao;
    }

    public double getValor() {
        return valor;
    }

    @Override // Método toString sobrescrito para exibir informações da avaliação
    public String toString() {
        return "Avaliação: " + this.nome + " (" + this.dtAplicacao + ") - Valor máximo: " + this.valor;
    }
}