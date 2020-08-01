package model;

public class Funcionario {
    String cpf, nome, rg, cargo;

    public Funcionario(String cpf, String nome, String rg, String cargo) {
        this.cpf = cpf;
        this.nome = nome;
        this.rg = rg;
        this.cargo = cargo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
