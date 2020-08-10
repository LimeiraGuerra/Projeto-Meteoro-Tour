package model.entities;

public class Onibus {
    String renavam, placa;

    public Onibus(String renavam, String placa) {
        this.renavam = renavam;
        this.placa = placa;
    }

    public String getRenavam() {
        return renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return "RENAVAM : " + renavam + '\n' +
                "Placa : " + placa + '\n';
    }
}
