package model.entities;

import java.sql.Time;
import java.util.Date;

public class Viagem {
    private Date data;
    private Time horarioSaida;
    private String cidadeOrigem, cidadeDestino;
    /* todo attrs */
    /* todo methods */


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
