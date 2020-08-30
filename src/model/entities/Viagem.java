package model.entities;

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

    public Viagem(Date data, String cidadeOrigem, String cidadeDestino, double valueViagem, double valueSeguroViagem) {
        this.data = data;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.valueViagem = valueViagem;
        this.valueSeguroViagem = valueSeguroViagem;
    }

    public Viagem(Date data, String cidadeOrigem, String cidadeDestino, Linha linha) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.linha = linha;
        this.trechosLinha = linha.generateTrechosViagem(cidadeOrigem, cidadeDestino);
        if (this.trechosLinha.size() > 0)
            this.data = dateTimeUnion(data, this.trechosLinha.get(0).getHorarioSaida());
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

    public void setAssentosVendidosViagem(List<String> assentosVendidosViagem){
        this.assentosVendidosViagem.addAll(assentosVendidosViagem);
    }

    public void setValuesOfViagem(double total, double seguro){
        this.valueViagem = total;
        this.valueSeguroViagem = seguro;
    }

    public Iterator<String> getAssentosVendidosViagem(){
        return assentosVendidosViagem.iterator();
    }

    public Iterator<TrechoLinha> getTrechosLinha(){ return trechosLinha.iterator(); }

    public void addAssentoVendidoViagem(String sitId){
        this.assentosVendidosViagem.add(sitId);
    }

    public int getDatePlusCorrection(){
        return trechosLinha.get(0).getdPlus();
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

    public String getTrechoLinhaIdsAndSetValues(){
        StringBuilder st = new StringBuilder();
        Iterator<TrechoLinha> itTl = this.getTrechosLinha();
        double valueViagem = 0.0;
        double valueSeguroViagem = 0.0;
        while (true) {
            TrechoLinha tl = itTl.next();
            st.append(tl.getId());
            valueViagem += tl.getTrecho().getValorTotal();
            valueSeguroViagem += tl.getTrecho().getValorSeguro();
            if (itTl.hasNext()) st.append(", ");
            else break;
        }
        this.setValuesOfViagem(valueViagem, valueSeguroViagem);
        return st.toString();
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
