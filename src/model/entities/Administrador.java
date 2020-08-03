package model.entities;

public class Administrador extends Vendedor {
    private String nome;
    private String senha;

    public Administrador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Administrador() {

    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String s) {
        senha =s;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
