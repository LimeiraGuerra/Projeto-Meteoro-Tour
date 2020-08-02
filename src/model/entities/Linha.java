package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Linha {

    private long id;
    private String nome;
    private ArrayList<TrechoLinha> trechosLinha;

    public Linha(long id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
