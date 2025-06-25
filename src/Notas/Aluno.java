package Notas;

public class Aluno extends Pessoa implements ITextoFormatavel<Aluno> { // Implementa a nova interface
    private String matricula;

    // Construtor da classe Aluno
    public Aluno(String nome, String cpf, String matricula) {
        super(nome, cpf);
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

    @Override
    public String toString() {
        return nome + "(Matrícula: " + this.matricula + ")";
    }

    public String getMat() {
        return this.matricula;
    }

    @Override
    public String toTextFormat() {
        // Define como um Aluno será salvo como uma linha de texto
        return getNome() + ";" + getCpf() + ";" + matricula;
    }
}