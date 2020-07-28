package model.entities;

import java.util.ArrayList;

public class Linha {

    private long id;
    private String nome;
    private ArrayList<Trecho> trechos;

    public Linha(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void addTrecho(Trecho trecho){
        trechos.add(trecho);
        trecho.setLinha(this);
    }

    public Linha() {
    }
}
