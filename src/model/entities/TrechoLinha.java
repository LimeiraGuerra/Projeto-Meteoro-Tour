package model.entities;


import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class TrechoLinha {
    private Date horarioSaida;
    private int ordem;
    private AssentoTrechoLinha assentoTrechoLinha;
    private Trecho trecho;
    private Linha linha;

    public TrechoLinha() {
    }

    public TrechoLinha(int ordem, Date horarioSaida, Trecho trecho, Linha linha) {
        this.horarioSaida = horarioSaida;
        this.ordem = ordem;
        this.trecho = trecho;
        this.linha = linha;
        trecho.setTrechoLinha(this);
        linha.setTrechoList(this);
    }


    public AssentoTrechoLinha getAssentoTrechoLinha() {
        return assentoTrechoLinha;
    }

    public void setAssentoTrechoLinha(AssentoTrechoLinha assentoTrechoLinha) {
        this.assentoTrechoLinha = assentoTrechoLinha;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getTrecho().getCidadeOrigem().equals(o) ||
                this.getTrecho().getCidadeDestino().equals(o)) return true;
        if (!(o instanceof TrechoLinha)) return false;
        TrechoLinha that = (TrechoLinha) o;
        return getOrdem() == that.getOrdem();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrdem());
    }
}
