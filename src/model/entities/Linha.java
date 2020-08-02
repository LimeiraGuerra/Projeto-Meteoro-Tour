package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Linha {

    private long id;
    private String nome;
    private List<Trecho> trechos = new ArrayList<>();
    private double quilometragemTotal;
    private double valorTotalLinha;

    private ArrayList<TrechoLinha> trechosLinha;


    public String getNome() {
        return nome;
    }

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

    public List<TrechoLinha> generateTrechosViagem(String cidadeOrigem, String cidadeDestino){
        List<TrechoLinha> trechosViagem = new ArrayList<>();
        boolean viagemOrigin = false;
        for(TrechoLinha tl : trechosLinha){
            if(tl.equals(cidadeOrigem) || viagemOrigin){
                viagemOrigin = true;
                trechosViagem.add(tl);
            }
            if(tl.equals(cidadeDestino)) {
                break;
            }
        }
        return trechosViagem;
    }

    public void addAllTrechosLinha(List<TrechoLinha> selectTrechosByLinha) {
        this.trechosLinha = (ArrayList<TrechoLinha>) selectTrechosByLinha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
