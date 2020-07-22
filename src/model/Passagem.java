package model;

import java.util.Date;

public class Passagem {

    private long numPassagem;
    private double precoTotal;
    private String nome;
    private String cpf;
    private String telefone;
    private boolean seguro;
    private String formaPagamento;
    private Date dataCompra;
    private Date dataViagem;

    public boolean verificarValidade(){
        return false;
    }
}
