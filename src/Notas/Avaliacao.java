package Notas;

public class Avaliacao {
    protected String nome;
    protected Data dtAplicacao;
    protected double valor;

    // Construtor da classe Avaliacao
    public Avaliacao(String nome, Data dtAplicacao, double valor) {
        // Validação do nome
        if (nome == null) {
            throw new IllegalArgumentException("O nome da avaliação não pode ser nulo ou vazio.");
        }
        this.nome = nome;

        // Validação da data de aplicação
        if (dtAplicacao == null) {
            throw new IllegalArgumentException("A data de aplicação não pode ser nula.");
        }
        this.dtAplicacao = dtAplicacao;

        // Validação do valor
        if (valor <= 0) { // O valor deve ser positivo
            throw new IllegalArgumentException("O valor da avaliação deve ser maior que zero.");
        }
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