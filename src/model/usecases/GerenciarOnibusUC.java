package model.usecases;


import database.dao.OnibusDAO;
import database.utils.DAO;
import model.entities.Funcionario;
import model.entities.Onibus;

import java.util.List;

public class GerenciarOnibusUC {
    private DAO<Onibus,String> daoOnibus;

    /*public GerenciarOnibusUC(OnibusDAO daoOnibus){
        this.daoOnibus = daoOnibus;
    }*/

    public GerenciarOnibusUC(DAO<Onibus, String> daoOnibus) {
        this.daoOnibus = daoOnibus;
    }

    public List<Onibus> getListOnibus(){
        return daoOnibus.selectAll();
    }

    public void saveOnibus(Onibus onibus){
        daoOnibus.save(onibus);
    }

    public void deleteOnibus(Onibus onibus){
        daoOnibus.delete(onibus);
    }

    public void updateOnibus(Onibus onibus){
        daoOnibus.update(onibus);
    }
}
