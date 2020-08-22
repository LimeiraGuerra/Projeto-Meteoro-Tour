package model.usecases;

import database.utils.DAO;
import model.entities.Passagem;
import model.entities.Viagem;

public class VenderPassagensUC {
    private DAO<Passagem, String> daoPassagem;
    private DAO<Viagem, String> daoViagem;

    public VenderPassagensUC(DAO<Passagem, String> daoPassagem,  DAO<Viagem, String> daoViagem){
        this.daoPassagem = daoPassagem;
        this.daoViagem = daoViagem;
    }

    public void saveSale(Passagem p) {
        this.daoPassagem.save(p);
    }
}
