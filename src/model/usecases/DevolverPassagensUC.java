package model.usecases;

import database.utils.DAO;
import model.entities.Passagem;

public class DevolverPassagensUC {
    private DAO<Passagem, String> daoPassagem;

    public DevolverPassagensUC(DAO<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public void deletePassagem(Passagem p) {
        this.daoPassagem.delete(p);
    }
}
