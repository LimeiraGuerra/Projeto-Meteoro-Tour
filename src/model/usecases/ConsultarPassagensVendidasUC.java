package model.usecases;

import database.dao.PassagemDAO;
import database.utils.DAO;
import model.entities.Passagem;

import java.util.List;

public class ConsultarPassagensVendidasUC {
    private DAO<Passagem, String> daoPassagem;

    public ConsultarPassagensVendidasUC(DAO<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public Passagem searchPassagensByNumber(String numeroPassagem){
        return this.daoPassagem.selectById(numeroPassagem);
    }

    public List<Passagem> searchPassagensByCpf(String cpf){
        return this.daoPassagem.selectAllByArg(cpf);
    }
}
