package database.dao;

import database.utils.DAO;
import model.entities.TrechoLinha;
import java.util.ArrayList;
import java.util.List;

public class TrechoLinhaDAO implements DAO<TrechoLinha, String> {

    private static TrechoLinhaDAO instancia;
    private List<TrechoLinha> trechosLinhas = new ArrayList<>();

    private TrechoLinhaDAO() {

    }

    @Override
    public void save(TrechoLinha model) {
        trechosLinhas.add(model);
    }

    @Override
    public void update(TrechoLinha model) {
        TrechoLinha tLinha = searchTrechoLinha(model);
        tLinha.setHorarioSaida(model.getHorarioSaida());
    }

    @Override
    public void delete(TrechoLinha model) {
        trechosLinhas.remove(model);
    }

    @Override
    public TrechoLinha selectById(String id) {
        return null;
    }

    @Override
    public List<TrechoLinha> selectByArgs(String... args) {
        return null;
    }

    public TrechoLinha searchTrechoLinha(TrechoLinha trechoLinha){
        return trechosLinhas.contains(trechoLinha) ? trechoLinha : null;
    }
    public List<TrechoLinha> getListTrechoLinha(){
        return trechosLinhas;
    }

    public static TrechoLinhaDAO getInstancia(){
        if (instancia == null){
            instancia = new TrechoLinhaDAO();
        }
        return  instancia;
    }
}
