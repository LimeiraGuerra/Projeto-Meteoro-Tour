package model.entities;

public class Trecho {
    private String cidadeOrigem;
    private String cidadeDestino;
    private double quilometragem;
    private double tempoDuracao;
    private double valorPassagem;
    private double taxaEmbarque;
    private double valorSeguro;
    private double valorTotal;

    public Trecho() {
    }

    public Trecho(String cidadeOrigem, String cidadeDestino, double quilometragem, double tempoDuracao, double valorPassagem, double taxaEmbarque, double valorSeguro) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.quilometragem = quilometragem;
        this.tempoDuracao = tempoDuracao;
        this.valorPassagem = valorPassagem;
        this.taxaEmbarque = taxaEmbarque;
        this.valorSeguro = valorSeguro;
        this.valorTotal = valorPassagem + taxaEmbarque  + valorSeguro;
    }


    public void setValorTotal() {
        this.valorTotal = valorPassagem + valorSeguro + taxaEmbarque;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public double getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(double tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    public double getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(double valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public double getTaxaEmbarque() {
        return taxaEmbarque;
    }

    public void setTaxaEmbarque(double taxaEmbarque) {
        this.taxaEmbarque = taxaEmbarque;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }
}
