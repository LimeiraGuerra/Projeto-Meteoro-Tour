package database.dao;


import database.utils.DAOCrud;
import model.entities.Onibus;
import java.util.ArrayList;
import java.util.List;

public class OnibusDAO implements DAOCrud<Onibus,String> {
    private static OnibusDAO instancia;
    private List<Onibus> onibus = new ArrayList<>();

    private OnibusDAO(){
        onibus.add(new Onibus("12345678910", "ABC1234"));
        onibus.add(new Onibus("13578976453", "DEF5678"));
        onibus.add(new Onibus("15326476535", "GHI9101"));
        onibus.add(new Onibus("86452333234", "JKL1121"));
    }
    @Override
    public void save(Onibus model) {
        onibus.add(model);
    }

    @Override
    public void update(Onibus model) {
        onibus.set(onibus.indexOf(model), model);
    }

    @Override
    public void delete(Onibus model) {
        onibus.remove(model);
    }

    @Override
    public Onibus selectById(String id) {
        return null;
    }

    @Override
    public List<Onibus> selectAll() {
        return null;
    }

    @Override
    public List<Onibus> selectAllByKeyword(String key) {
        return null;
    }

    public List<Onibus> getListOnibus() {
        return onibus;
    }

    public static OnibusDAO getInstancia(){
        if (instancia == null) instancia = new OnibusDAO();
        return instancia;
    }
}
