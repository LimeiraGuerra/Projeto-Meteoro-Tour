package model.entities;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Linha {

    private long id;
    private String nome;
    private double quilometragemTotal;
    private double valorTotalLinha;

    private ArrayList<TrechoLinha> trechosLinha = new ArrayList<>();;


    public String getNome() {
        return nome;
    }

    public Linha(long id, String nome) {
        this.id = id;
        this.nome = nome;

    }


    public void addTrechoLinha(TrechoLinha trecho){
        trechosLinha.add(trecho);
        setValorTotalLinha();
        setQuilometragem();

    }

    public void setQuilometragem(){
        quilometragemTotal = 0;
        for (TrechoLinha trechoLinha : trechosLinha) {
            quilometragemTotal += trechoLinha.getTrecho().getQuilometragem();
        }
    }

    public  void setValorTotalLinha(){
        valorTotalLinha = 0;
        for (TrechoLinha trecho:trechosLinha) {
            valorTotalLinha+=trecho.getTrecho().getValorTotal();
        }
    }
    public TrechoLinha getTrechoLinha(Trecho trecho){
        for (TrechoLinha trechoLinha : trechosLinha){
            if (trechoLinha.getTrecho().equals(trecho)){
                return trechoLinha;
            }
        }
        return null;
    }
    public List<TrechoLinha> getLisTrechosLinha(){
        return trechosLinha;
    }

    public void removeTrecho(TrechoLinha trecho){
        trechosLinha.remove(trecho);
        setValorTotalLinha();
        setQuilometragem();
    }

    public List<Trecho> getListTrechos(){
        List<Trecho> trechos = new ArrayList<>();
        for (TrechoLinha x: trechosLinha) {
            trechos.add(x.getTrecho());
        }
        return trechos;
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
            if(tl.equals(cidadeDestino)) { break; }
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

    public void setTrechoList(TrechoLinha trechoLinha){
        trechosLinha.add(trechoLinha);
    }
}
