package model.entities;

import view.util.DataValidator;

import java.util.Date;
import java.util.Objects;


public class InfoLinhaTrechoRelatorio {
    private String data, horarioSaida;
    private String nomeLinha, cidadeOrigem, cidadeDestino;
    private int uso;
    private double lucro;

    public InfoLinhaTrechoRelatorio() {
    }

    public InfoLinhaTrechoRelatorio(String data, String horarioSaida, String nomeLinha, String cidadeOrigem, String cidadeDestino, int uso, double lucro) {
        this.data = data;
        this.horarioSaida = horarioSaida;
        this.nomeLinha = nomeLinha;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
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
        return cidadeOrigem + " - " + cidadeDestino;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
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
                getNomeTrecho() + ";" +
                uso + ";" +
                getLucroFormated() + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoLinhaTrechoRelatorio)) return false;
        InfoLinhaTrechoRelatorio that = (InfoLinhaTrechoRelatorio) o;
        return Objects.equals(getData(), that.getData()) &&
                Objects.equals(getHorarioSaida(), that.getHorarioSaida()) &&
                Objects.equals(getNomeLinha(), that.getNomeLinha());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getHorarioSaida(), getNomeLinha());
    }
}
