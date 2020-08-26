package model.entities;

import view.util.DataValidator;

public class InfoLinhaTrechoRelatorio {
    private String data, horarioSaida;
    private String nomeLinha, nomeTrecho;
    private int uso;
    private double lucro;

    public InfoLinhaTrechoRelatorio() {
    }

    public InfoLinhaTrechoRelatorio(String data, String horarioSaida, String nomeLinha, String nomeTrecho, int uso, double lucro) {
        this.data = data;
        this.horarioSaida = horarioSaida;
        this.nomeLinha = nomeLinha;
        this.nomeTrecho = nomeTrecho;
        this.uso = uso;
        this.lucro = lucro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
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

    public String getLucroFormated(){
        return DataValidator.formatCurrencyView(lucro);
    }

    public void setLucro(double lucro) {
        this.lucro = lucro;
    }

    @Override
    public String toString() {
        return data + ";" +
                nomeLinha + ";" +
                horarioSaida + ";" +
                nomeTrecho + ";" +
                uso + ";" +
                getLucroFormated() + "\n";
    }
}
