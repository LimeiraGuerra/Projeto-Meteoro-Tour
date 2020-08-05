package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Trecho {
    private String cidadeOrigem;
    private String cidadeDestino;
    private double quilometragem;
    private int tempoDuracao;
    private double valorPassagem;
    private double taxaEmbarque;
    private double valorSeguro;
    private double valorTotal;

    private List<TrechoLinha> trechoLinha = new ArrayList<>();

    public Trecho() {
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

    public void setTrechoLinha(TrechoLinha trechoL){
        trechoLinha.add(trechoL);
    }

    public void deleteTrechoLinha(TrechoLinha trechoL) {
        trechoLinha.remove(trechoL);
    }

    public void setValorTotal() {
        this.valorTotal = valorPassagem + taxaEmbarque;
    }

    public double getValorTotal() {
        return valorTotal;
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
    public int sizeListTrechoLinha(){
        return trechoLinha.size();
    }
    @Override
    public String toString() {
        return cidadeOrigem + " - " + cidadeDestino;
    }
}
