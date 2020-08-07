package model.entities;

import java.util.Date;

public class InfoLinhaTrechoRelatorio {
    private Date data;
    private String nomeLinha, nomeTrecho;
    private int uso;
    private double lucro;

    public InfoLinhaTrechoRelatorio() {
    }

    public InfoLinhaTrechoRelatorio(Date data, String nomeLinha, String nomeTrecho, int uso, double lucro) {
        this.data = data;
        this.nomeLinha = nomeLinha;
        this.nomeTrecho = nomeTrecho;
        this.uso = uso;
        this.lucro = lucro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getNomeTrecho() {
        return nomeTrecho;
    }

    public void setNomeTrecho(String nomeTrecho) {
        this.nomeTrecho = nomeTrecho;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int uso) {
        this.uso = uso;
    }

    public double getLucro() {
        return lucro;
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
}
