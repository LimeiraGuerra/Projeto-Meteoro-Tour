package model.usecases;


import database.utils.DAOCrud;
import model.entities.Onibus;

import java.util.List;

public class GerenciarOnibusUC {
    private DAOCrud<Onibus,String> daoOnibus;

    /*public GerenciarOnibusUC(OnibusDAO daoOnibus){
        this.daoOnibus = daoOnibus;
    }*/

    public GerenciarOnibusUC(DAOCrud<Onibus, String> daoOnibus) {
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
