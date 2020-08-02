package model.entities;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Viagem {
    private Date data;
    private Time horarioSaida;
    private String cidadeOrigem, cidadeDestino;

    private Linha linha;
    private List<TrechoLinha> trechosLinha;
    /* todo methods */

    public Viagem() {
    }

    public Viagem(String cidadeOrigem, String cidadeDestino, Linha linha) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.linha = linha;
        this.trechosLinha = linha.generateTrechosViagem(cidadeOrigem, cidadeDestino);
        this.horarioSaida = this.trechosLinha.get(0).getHorarioSaida();
    }

    public Set<String> verifyDisponibility(){
        Set<String> assentosVendidos = new LinkedHashSet<>();
        for(TrechoLinha tl: trechosLinha){
            if (tl.getAssentoTrechoLinha() != null)
                assentosVendidos.addAll(tl.getAssentoTrechoLinha().getAssentosVendidos());
        }
        return assentosVendidos;
    }

    public String getLinhaName(){
        return this.getLinha().getNome();
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(Time horarioSaida) {
        this.horarioSaida = horarioSaida;
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
}
