package model.usecases;


import database.dao.OnibusDAO;
import model.entities.Onibus;

import java.util.List;

public class GerenciarOnibusUC {
    private OnibusDAO daoOnibus;

    public GerenciarOnibusUC(OnibusDAO daoOnibus){
        this.daoOnibus = daoOnibus;
    }

    public List<Onibus> getListOnibus(){
        return daoOnibus.getListOnibus();
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
