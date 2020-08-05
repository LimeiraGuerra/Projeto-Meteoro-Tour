package model.entities;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

public class Viagem {
    private Date data;
    private String cidadeOrigem, cidadeDestino;

    private Linha linha;
    private List<TrechoLinha> trechosLinha;
    private double valueViagem = 0.0;
    private double valueSeguroViagem = 0.0;
    private Set<String> assentosVendidosViagem = new LinkedHashSet<>();

    public Viagem() {
    }

    public Viagem(Date data, String cidadeOrigem, String cidadeDestino, Linha linha) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.linha = linha;
        this.trechosLinha = linha.generateTrechosViagem(cidadeOrigem, cidadeDestino);
        this.data = dateTimeUnion(data, this.trechosLinha.get(0).getHorarioSaida());
        this.verifyDisponibility();
    }

    private Date dateTimeUnion(Date data, Date time){
        /**
         * Une horario com data, em uma mesma classe tipo Data
         */
        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(data);

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(time);

        calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
        calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
        calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
        calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));
        return calendarA.getTime();
    }

    public void verifyDisponibility(){
        double valueViagem = 0.0;
        double valueSeguroViagem = 0.0;
        for(TrechoLinha tl: trechosLinha){
            valueViagem += tl.getTrecho().getValorTotal();
            valueSeguroViagem += tl.getTrecho().getValorSeguro();
            if (tl.getAssentoTrechoLinha() != null)
                this.assentosVendidosViagem.addAll(tl.getAssentoTrechoLinha().getAssentosVendidos());
        }
        this.setValuesOfViagem(valueViagem, valueSeguroViagem);
    }

    private void setValuesOfViagem(double total, double seguro){
        this.valueViagem = total;
        this.valueSeguroViagem = seguro;
    }

    public Iterator<String> getAssentosVendidosViagem(){
        return assentosVendidosViagem.iterator();
    }

    public void addAssentoVendidoViagem(String sitId){
        this.assentosVendidosViagem.add(sitId);
    }

    public String getLinhaName(){
        return this.getLinha().getNome();
    }

    public Linha getLinha() {
        return linha;
    }

    public String getHorarioSaida(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(data);
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

    public Double getValueViagem() {
        return valueViagem;
    }

    public Double getValueSeguroViagem() {
        return valueSeguroViagem;
    }
}
