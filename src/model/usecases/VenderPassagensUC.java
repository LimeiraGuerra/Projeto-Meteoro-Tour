package model.usecases;

import database.utils.DAOCrud;
import model.entities.Passagem;

import java.sql.SQLException;

public class VenderPassagensUC {
    private DAOCrud<Passagem, String> daoPassagem;

    public VenderPassagensUC(DAOCrud<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public void saveSale(Passagem p) throws SQLException {
        this.daoPassagem.save(p);
    }
}
