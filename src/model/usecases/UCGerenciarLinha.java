package model.usecases;

import model.entities.Linha;

import java.util.ArrayList;
import java.util.List;

public class UCGerenciarLinha {

    private List<Linha> linhas = new ArrayList<>();
    private static UCGerenciarLinha instancia;

    public void criarLinha(String nomeLinha){
        Linha linha = new Linha(10 , nomeLinha);
        linhas.add(linha);
    }
    public void adicionarLinha(Linha linha) {
        if (buscarLinha(linha) == null){
            linhas.add(linha);
        }
        else{
            linha.setNome(linha.getNome());
        }

    }
    public void removerLinha(Linha linha){
        linhas.remove(linha);
    }

    public Linha buscarLinha(Linha l){
        for (Linha linha:linhas) {
            if (linha.equals(l)){
                return linha;
            }
        }
        return null;
    }

    public List<Linha> getListLinha() {
        return linhas;
    }

    public void editarLinha(String nomeNovo, Linha l) {
        Linha linha = buscarLinha(l);
        linha.setNome(nomeNovo);
    }

    public static UCGerenciarLinha getInstancia(){
        if (instancia == null){
            instancia = new UCGerenciarLinha();
        }
        return instancia;
    }

    private UCGerenciarLinha() {
    }


}
