package model.entities;

import view.util.DataValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Linha {

    private long id;
    private String nome;
    private boolean inativo;
    private double quilometragemTotal;
    private double valorTotalLinha;
    private ArrayList<TrechoLinha> trechosLinha = new ArrayList<>();;

    public Linha() {

    }

    public Linha(String nome) {
        this.nome = nome;
    }

    public Linha(long id, String nome, boolean inativo) {
        this.id = id;
        this.nome = nome;
        this.inativo = inativo;
    }

    public Linha(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setTrechoLinha(TrechoLinha tl){
        trechosLinha.add(tl);
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
        for (TrechoLinha trechoLinha:trechosLinha) {
            valorTotalLinha+=trechoLinha.getTrecho().getValorTotal();
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

    public void deleteTrechoLinha(TrechoLinha trecho){
        trechosLinha.remove(trechosLinha.indexOf(trecho));
        setValorTotalLinha();
        setQuilometragem();
    }

    public List<Trecho> getListTrechos(){
        List<Trecho> trechos = new ArrayList<>();
        for (TrechoLinha tL: trechosLinha) {
            Trecho t = tL.getTrecho();
            trechos.add(t);
        }
        return trechos;
    }

    public List<TrechoLinha> getListTrechoLinha(){
        return trechosLinha;
    }

    public List<TrechoLinha> generateTrechosViagem(String cidadeOrigem, String cidadeDestino){
        List<TrechoLinha> trechosViagem = new ArrayList<>();
        boolean viagemOrigin = false;
        for(TrechoLinha tl : trechosLinha){
            tl.setLinha(this);
            if(tl.getTrecho().getCidadeOrigem().equals(cidadeOrigem) || viagemOrigin){
                viagemOrigin = true;
                trechosViagem.add(tl);
            }
            if(tl.getTrecho().getCidadeDestino().equals(cidadeDestino)) { break; }
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

    public String getFormatedQuilometragemTotal() {
        return DataValidator.doubleWithDecimalsFormater(quilometragemTotal).concat(" Km");
    }

    public String getFormatedValorTotalLinha() {
        return DataValidator.formatCurrencyView(valorTotalLinha);
    }

    public boolean isInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public void setNome(String nomeNovo) {
        nome = nomeNovo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Long) return getId() == (long) o;
        if (!(o instanceof Linha)) return false;
        Linha linha = (Linha) o;
        return getId() == linha.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getNome() {
        return nome;
    }
}
