package model.usecases;

import database.utils.DAOCrud;
import model.entities.Passagem;

public class DevolverPassagensUC {
    private DAOCrud<Passagem, String> daoPassagem;

    public DevolverPassagensUC(DAOCrud<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public void deletePassagem(Passagem p) {
        this.daoPassagem.delete(p);
    }
}
