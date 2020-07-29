package model.usecases;

import database.dao.PassagemDAO;
import database.utils.DAO;
import model.entities.Passagem;

public class ConsultarPassagensVendidasUC {
    private DAO<Passagem, String> daoPassagem;

    public ConsultarPassagensVendidasUC(){
        this.daoPassagem = new PassagemDAO();
    }

    public Passagem searchViagensByNumber(String numeroPassagem){
        return daoPassagem.selectById(numeroPassagem);
    }
}
