package model.usecases;

import database.utils.DAOCrud;
import model.entities.Passagem;

import java.util.List;

public class ConsultarPassagensVendidasUC {
    private DAOCrud<Passagem, String> daoPassagem;

    public ConsultarPassagensVendidasUC(DAOCrud<Passagem, String> daoPassagem){
        this.daoPassagem = daoPassagem;
    }

    public Passagem searchPassagensByNumber(String numeroPassagem){
        return this.daoPassagem.selectById(numeroPassagem);
    }

    public List<Passagem> searchPassagensByCpf(String cpf){
        return this.daoPassagem.selectAllByKeyword(cpf);
    }
}
