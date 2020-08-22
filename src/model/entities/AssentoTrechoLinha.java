package model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssentoTrechoLinha {
    private Date data;
    private TrechoLinha trechoLinha;

    private List<String> assentosVendidos = new ArrayList<>();

    public AssentoTrechoLinha(){}

    //Temporário
    public AssentoTrechoLinha(Date data) {
        this.data = data;
    }
    public List<String> getAssentosVendidos(){
        return assentosVendidos;
    }

    public void addAssentoVendido(String assento){
        this.assentosVendidos.add(assento);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TrechoLinha getTrechoLinha() {
        return trechoLinha;
    }

    public void setTrechoLinha(TrechoLinha trechoLinha) {
        this.trechoLinha = trechoLinha;
    }
}
