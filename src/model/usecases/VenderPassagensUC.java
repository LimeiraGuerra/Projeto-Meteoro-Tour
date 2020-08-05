package model.usecases;

import database.utils.DAO;
import model.entities.Passagem;

public class VenderPassagensUC {
    private DAO<Passagem, String> daoPassagem;

    public VenderPassagensUC(DAO<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public void saveSale(Passagem p) {
        this.daoPassagem.save(p);
    }
}
