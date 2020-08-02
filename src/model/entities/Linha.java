package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Linha {

    private long id;
    private String nome;
    private List<Trecho> trechos = new ArrayList<>();
    private double quilometragemTotal;
    private double valorTotalLinha;

    public Linha(long id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public void addTrecho(Trecho trecho){
        trechos.add(trecho);
        setValorTotalLinha();
        setQuilometragem();

    }

    public void setQuilometragem(){
        quilometragemTotal = 0;
        for (Trecho trecho:trechos) {
            quilometragemTotal+=trecho.getQuilometragem();
        }
    }

    public  void setValorTotalLinha(){
        valorTotalLinha = 0;
        for (Trecho trecho:trechos) {
            valorTotalLinha+=trecho.getValorTotal();
        }
    }
    public List<Trecho> getTrechos(){
        return trechos;
    }

    public void removeTrecho(Trecho trecho){
        trechos.remove(trecho);
        setValorTotalLinha();
        setQuilometragem();
    }
    public Linha() {
    }

    public String getNome() {
        return nome;
    }

    public double getQuilometragemTotal() {
        return quilometragemTotal;
    }

    public double getValorTotalLinha() {
        return valorTotalLinha;
    }

    public void setNome(String nomeNovo) {
        nome = nomeNovo;
    }
}
