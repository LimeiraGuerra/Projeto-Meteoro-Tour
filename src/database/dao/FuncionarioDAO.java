package database.dao;

import database.utils.DAO;
import model.entities.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements DAO<Funcionario, String> {
    private static FuncionarioDAO instancia;
    private List<Funcionario> funcs = new ArrayList<>();

    private FuncionarioDAO(){
        funcs.add(new Funcionario("12345","Gabriel", "54321", "estagiario senior"));
        funcs.add(new Funcionario("6789", "Augusto", "9876", "estagiario senior"));
        funcs.add(new Funcionario("1011", "Erika", "1101", "product owner"));

    }

    @Override
    public void save(Funcionario model) {

    }

    @Override
    public void update(Funcionario model) {

    }

    @Override
    public void delete(Funcionario model) {

    }

    @Override
    public Funcionario selectById(String id) {
        return null;
    }

    @Override
    public List<Funcionario> selectByArgs(String... args) {
        return null;
    }

    public static FuncionarioDAO getInstancia(){
        if (instancia == null) return new FuncionarioDAO();
        return instancia;
    }
}
