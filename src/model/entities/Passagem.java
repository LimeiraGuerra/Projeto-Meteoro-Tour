package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Passagem {

    private long numPassagem;
    private double precoPago;
    private String nome;
    private String cpf;
    private String rg;
    private String telefone;
    private boolean seguro;
    private Date dataCompra;
    private Date dataViagem;

    private Viagem viagem;

    public Passagem(String nome, String cpf, String rg, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
    }

    public Passagem() {
    }

    public boolean verificarValidade(){
        return false;
    }

    public long getNumPassagem() {
        return numPassagem;
    }

    public void setNumPassagem(long numPassagem) {
        this.numPassagem = numPassagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isSeguro() {
        return seguro;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Date getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(Date dataViagem) {
        this.dataViagem = dataViagem;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public double getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(double precoPago) {
        this.precoPago = precoPago;
    }

    public String getHorarioSaida(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(this.dataViagem);
    }

    public String getDataViagemStr(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.dataViagem);
    }

    public String getNomeLinha(){
        return viagem.getLinhaName();
    }

    public String getCidadeOrigem(){
        return viagem.getCidadeOrigem();
    }

    public String getCidadeDestino(){
        return viagem.getCidadeDestino();
    }
}

