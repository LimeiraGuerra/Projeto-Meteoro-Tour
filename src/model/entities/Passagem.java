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
    private String assentoId;

    private Viagem viagem;

    public Passagem(long numPassagem, double precoPago, String nome, String cpf, String rg,
                    String telefone, boolean seguro, Date dataCompra, String assentoId) {
        this.numPassagem = numPassagem;
        this.precoPago = precoPago;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.seguro = seguro;
        this.dataCompra = dataCompra;
        this.assentoId = assentoId;
    }

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

    public String getAssentoId() {
        return assentoId;
    }

    public void setAssentoId(String assentoId) {
        this.assentoId = assentoId;
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
        return this.viagem.getHorarioSaida();
    }

    public String getDataViagemStr(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(this.getViagem().getData());
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

