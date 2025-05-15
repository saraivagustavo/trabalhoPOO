package Notas;

public class Data {
    private int dia, mes, ano;

    // Construtor
    Data(int d, int m, int a){
        this.dia = d;
        this.mes = m;
        this.ano = a;
    }

    // Método posterior que verifica se a data atual é posterior a outra data (d2)
    public boolean posterior(Data d2){
        if (this.ano > d2.ano){
            return true;
        }
        if (this.ano == d2.ano && this.mes > d2.mes){
            return true;
        }
        if (this.ano == d2.ano && this.mes == d2.mes && this.dia > d2.dia){
            return true;
        }
        else{
            return false;
        }
    }
}
