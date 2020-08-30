package model.entities;

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

    public String getFormatedCpf(){
        return cpf.replaceAll("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})","$1\\.$2\\.$3-$4");
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

    @Override
    public String toString() {
        return  "CPF : " + cpf + '\n' +
                "Nome : " + nome + '\n' +
                "RG : " + rg + '\n' +
                "Cargo : " + cargo + '\n';
    }
}
