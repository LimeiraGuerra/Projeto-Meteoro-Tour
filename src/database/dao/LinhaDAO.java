package database.dao;

import database.utils.DAO;
import model.entities.Linha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinhaDAO implements DAO<Linha, String> {
    private Map<String, Linha> linhas = new HashMap<>();

    public LinhaDAO() {
        linhas.put("1", new Linha(1L, "Descalvado - Ibaté Curto"));
        linhas.put("2", new Linha(2L, "Descalvado - Ibaté Longo"));
    }

    @Override
    public void save(Linha model) {

    }

    @Override
    public void update(Linha model) {

    }

    @Override
    public void delete(Linha model) {

    }

    @Override
    public Linha selectById(String id) {
        Linha result = linhas.get(id);
        return result;
    }

    @Override
    public List<Linha> selectByArgs(String... args) {
        return null;
    }
}
