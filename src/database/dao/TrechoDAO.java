package database.dao;

import database.utils.DAOCrud;
import model.entities.Trecho;

import java.util.*;

public class TrechoDAO implements DAOCrud<Trecho, String> {
    private static TrechoDAO instancia;
    private List<Trecho> trechos = new ArrayList<>();

    private TrechoDAO(){
        trechos.add(new Trecho("Descalvado", "São Carlos", 40.0, 30, 10.0, 1.0, 0.2));
        trechos.add(new Trecho("São Carlos", "Araraquara", 30.0, 20, 7.0, 0.8, 0.2));
        trechos.add(new Trecho("São Carlos", "Ibaté", 15.0, 5, 5.0, 0.5, 0.2));
        trechos.add(new Trecho("Araraquara", "Ibaté", 20.0, 15, 8.0, 1.0, 0.2));

    }

    @Override
    public void save(Trecho model) {
        trechos.add(model);
    }

    @Override
    public void update(Trecho model) {
        int index = trechos.indexOf(model);
        Trecho trecho = trechos.get(index);
        if (trecho != null){
            trecho.setTaxaEmbarque(model.getTaxaEmbarque());
            trecho.setQuilometragem(model.getQuilometragem());
            trecho.setTempoDuracao(model.getTempoDuracao());
            trecho.setValorSeguro(model.getValorSeguro());
            trecho.setValorPassagem(model.getValorPassagem());
        }
        trechos.remove(index);
        trechos.add(index, trecho);
    }

    @Override
    public void delete(Trecho model) {
        trechos.remove(model);
    }

    @Override
    public Trecho selectById(String id) {
        int num = Integer.parseInt(id);
        return trechos.get(num);
    }

    @Override
    public List<Trecho> selectAll() {
        return null;
    }

    @Override
    public List<Trecho> selectAllByKeyword(String key) {
        return null;
    }

    public  Trecho searchTrecho(Trecho trecho){
        return trechos.contains(trecho) ? trecho : null;
    }

    public List<Trecho> getListTrechos() {
        return trechos;
    }

    public static TrechoDAO getInstancia(){
        if (instancia == null){
            instancia = new TrechoDAO();
        }
        return instancia;
    }

}
