package Notas;

public class Aluno extends Pessoa{
    private String matricula;
    public Aluno(String nome, String cpf, String matricula){
        super(nome, cpf);
        this.matricula = matricula;
    }

    // Método toString sobrescrito para exibir informações do aluno
    public String toString(){
        return nome + "(Matrícula: " + this.matricula + ")";
    }

    //Getter que retorna a nota do aluno
    public String getMat(){
        return this.matricula;
    }
}
