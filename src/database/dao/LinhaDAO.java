package database.dao;

import database.utils.DAO;
import model.entities.Linha;

import java.util.ArrayList;
import java.util.List;

public class LinhaDAO implements DAO<Linha, String> {
    private static LinhaDAO instancia;
    private List<Linha> linhas = new ArrayList<>();

    private LinhaDAO() {
        linhas.add(new Linha(1L, "Descalvado - Ibaté Curto"));
        linhas.add(new Linha(2L, "Descalvado - Ibaté Longo"));
    }

    @Override
    public void save(Linha model) {
        linhas.add(model);
    }

    @Override
    public void update(Linha model) {
        int index = linhas.indexOf(model);
        linhas.remove(index);
        linhas.add(index, model);
    }

    @Override
    public void delete(Linha model) {
        linhas.remove(model);
    }

    @Override
    public Linha selectById(String id) {
        Long num = Long.parseLong(id);
        for (Linha l : linhas){
            if (l.equals(num)) return l;
        }
        return null;
    }

    @Override
    public List<Linha> selectByArgs(String... args) {
        return null;
    }

    public Linha searchLinha(Linha linha){
        return linhas.contains(linha) ? linha : null;
    }

    //Melhor verificar se existe na tabela da view do que fazer um metodo de dao
    public Linha searchLinhaNome(String nome){
        for (Linha linha: linhas) {
            if(linha.getNome().equals(nome)){
                return linha;
            }
        }
        return null;
    }

    public List<Linha> getListLinha(){
        return linhas;
    }

    public static LinhaDAO getInstancia(){
        if (instancia == null){
            instancia = new LinhaDAO();
        }
        return  instancia;
    }
}
