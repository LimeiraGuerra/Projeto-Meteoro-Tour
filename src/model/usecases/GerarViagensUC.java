package model.usecases;

import database.utils.DAO;
import model.entities.Viagem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GerarViagensUC {
    private DAO<Viagem, String> daoViagem;

    public GerarViagensUC(DAO<Viagem, String> daoViagem){
        this.daoViagem = daoViagem;
    }

    public List<Viagem> searchForViagens(Date data, String cidadeOrigem, String cidadeDestino){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return this.daoViagem.selectByArgs(cidadeOrigem, cidadeDestino, dateFormat.format(data));
    }
}
