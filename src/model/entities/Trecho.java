package model.entities;

import view.util.DataValidator;


public class Trecho {

    private int id;
    private String cidadeOrigem;
    private String cidadeDestino;
    private double quilometragem;
    private int tempoDuracao;
    private double valorPassagem;
    private double taxaEmbarque;
    private double valorSeguro;
    private double valorTotal;
    private TrechoLinha trechoLinha;

    public Trecho() {
    }

    public Trecho(String cidadeOrigem, String cidadeDestino, double quilometragem, int tempoDuracao, double valorPassagem, double taxaEmbarque, double valorSeguro, int id) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.quilometragem = quilometragem;
        this.tempoDuracao = tempoDuracao;
        this.valorPassagem = valorPassagem;
        this.taxaEmbarque = taxaEmbarque;
        this.valorSeguro = valorSeguro;
        this.valorTotal = valorPassagem + taxaEmbarque;
        this.id = id;
    }

    public Trecho(String cidadeOrigem, String cidadeDestino, double quilometragem, int tempoDuracao, double valorPassagem, double taxaEmbarque, double valorSeguro) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.quilometragem = quilometragem;
        this.tempoDuracao = tempoDuracao;
        this.valorPassagem = valorPassagem;
        this.taxaEmbarque = taxaEmbarque;
        this.valorSeguro = valorSeguro;
        this.valorTotal = valorPassagem + taxaEmbarque;
    }

    public TrechoLinha getTrechoLinha() {
        return trechoLinha;
    }

    public void setTrechoLinha(TrechoLinha trechoLinha) {
        this.trechoLinha = trechoLinha;
    }

    public void setValorTotal() {
        this.valorTotal = valorPassagem + taxaEmbarque;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getFormatedValorTotal(){
        return DataValidator.formatCurrencyView(valorTotal);
    }

    public String getCidadeOrigem() {
        return cidadeOrigem;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public String getFormatedQuilometragem(){
        return DataValidator.doubleWithDecimalsFormater(quilometragem).concat(" Km") ;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public int getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(int tempoDuracao) {
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

    @Override
    public String toString() {
        return cidadeOrigem + " - " + cidadeDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trecho)) return false;
        Trecho trecho = (Trecho) o;
        return getId() == trecho.getId();
    }
}
