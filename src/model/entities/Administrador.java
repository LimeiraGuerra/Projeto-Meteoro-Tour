package model.entities;

public class Administrador extends Vendedor {
    private String senha;

    public Administrador(String senha) {
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
}
