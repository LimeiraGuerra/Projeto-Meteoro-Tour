package database.dao;

import database.utils.DAO;
import model.entities.Passagem;
import model.entities.Viagem;

import java.util.List;

public class PassagemDAO implements DAO<Passagem, String> {
    @Override
    public void save(Passagem model) {

    }

    @Override
    public void update(Passagem model) {

    }

    @Override
    public void delete(Passagem model) {

    }

    @Override
    public Passagem selectById(String id) {
        /* todo */
        /* Retorno pra teste */
        Viagem testeViagem = new Viagem();
        testeViagem.setCidadeOrigem("Descalvado");
        testeViagem.setCidadeDestino("São Carlos");
        Passagem testePassagem = new Passagem();
        testePassagem.setViagem(testeViagem);
        testePassagem.setNumPassagem(1234);
        if (id.equals(""+testePassagem.getNumPassagem())){
            return testePassagem;
        }
        return null;
    }

    @Override
    public List<Passagem> selectByArgs() {
        return null;
    }
}
