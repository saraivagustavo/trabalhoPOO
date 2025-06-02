package Notas;

public class Aluno extends Pessoa {
    private String matricula;

    // Construtor da classe Aluno
    public Aluno(String nome, String cpf, String matricula) {
        super(nome, cpf); // Chama o construtor da classe Pessoa

        // Validação dos parâmetros no construtor
        if (nome == null) {
            throw new IllegalArgumentException("O nome do aluno não pode ser nulo ou vazio.");
        }
        if (cpf == null) {
            throw new IllegalArgumentException("O CPF do aluno não pode ser nulo ou vazio.");
        }
        if (matricula == null) {
            throw new IllegalArgumentException("A matrícula do aluno não pode ser nula ou vazia.");
        }
        this.matricula = matricula;
    }

    // Método toString sobrescrito para exibir informações do aluno
    @Override
    public String toString() {
        return nome + "(Matrícula: " + this.matricula + ")";
    }

    //Getter que retorna a matrícula do aluno
    public String getMat() {
        return this.matricula;
    }
}