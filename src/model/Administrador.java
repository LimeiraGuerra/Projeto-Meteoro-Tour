package model;

public class Administrador extends Vendedor {
    private String nome;
    private String senha;

    public Administrador(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
