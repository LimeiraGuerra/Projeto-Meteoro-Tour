package database.dao;

import database.utils.DAO;
import model.entities.Passagem;
import model.entities.Viagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PassagemDAO implements DAO<Passagem, String> {
    private Map<String, Passagem> passagens = new HashMap<>();
    private long cont = 0;

    private static PassagemDAO instancia;
    @Override
    public void save(Passagem model) {
        model.setNumPassagem(++cont);
        this.passagens.put(""+cont, model);
    }

    @Override
    public void update(Passagem model) {

    }

    @Override
    public void delete(Passagem model) {
        passagens.remove(""+model.getNumPassagem());
    }

    @Override
    public Passagem selectById(String id) {
        return passagens.containsKey(id) ? passagens.get(id) : null;
    }

    @Override
    public List<Passagem> selectByArgs(String... args) {
        List<Passagem> matchP = new ArrayList<>();
        for(Passagem p : passagens.values())
            if (args[0].equals(p.getCpf())) matchP.add(p);
        return matchP;
    }

    public static PassagemDAO getInstancia(){
        if (instancia == null){
            instancia = new PassagemDAO();
        }
        return instancia;
    }

    private PassagemDAO() {
    }
}
