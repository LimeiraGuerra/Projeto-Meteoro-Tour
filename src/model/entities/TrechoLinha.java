package model.entities;

import java.util.Date;
import java.util.Objects;

public class TrechoLinha {
    private long id;
    private Date horarioSaida;
    private int ordem;
    private int dPlus;
    private Trecho trecho;
    private Linha linha;

    public TrechoLinha() {
    }

    public TrechoLinha(long id, Date horarioSaida, int ordem, int dPlus) {
        this.id = id;
        this.horarioSaida = horarioSaida;
        this.ordem = ordem;
        this.dPlus = dPlus;
    }

    public TrechoLinha(int ordem, Date horarioSaida, int dPlus, Trecho trecho, Linha linha, long id) {
        this.horarioSaida = horarioSaida;
        this.ordem = ordem;
        this.dPlus = dPlus;
        this.trecho = trecho;
        this.linha = linha;
        this.id = id;
        trecho.setTrechoLinha(this);
        linha.setTrechoLinha(this);
    }
    public TrechoLinha(int ordem, Date horarioSaida, Trecho trecho, Linha linha) {
        this(ordem, horarioSaida, 0, trecho, linha, 0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Linha getLinha() {
        return linha;
    }

    public void setLinha(Linha linha) {
        this.linha = linha;
    }

    public Trecho getTrecho() {
        return trecho;
    }

    public void setTrecho(Trecho trecho) {
        this.trecho = trecho;
        trecho.setTrechoLinha(this);
    }

    public Date getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(Date horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getdPlus() {
        return dPlus;
    }

    public void setdPlus(int dPlus) {
        this.dPlus = dPlus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrechoLinha)) return false;
        TrechoLinha that = (TrechoLinha) o;
        return getOrdem() == that.getOrdem();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrdem());
    }
}
