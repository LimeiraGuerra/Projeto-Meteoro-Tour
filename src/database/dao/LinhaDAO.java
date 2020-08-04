package database.dao;

import database.utils.DAO;
import model.entities.Linha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Linha linha = searchLinha(model);
        linha.setNome(model.getNome());
    }

    @Override
    public void delete(Linha model) {
        linhas.remove(model);
    }

    @Override
    public Linha selectById(String id) {
        int num = Integer.parseInt(id);
        Linha result = linhas.get(num);
        return result;
    }

    @Override
    public List<Linha> selectByArgs(String... args) {
        return null;
    }

    public Linha searchLinha(Linha linha){
        for (Linha l: linhas) {
            if (l.equals(linha)){
                return  linha;
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
