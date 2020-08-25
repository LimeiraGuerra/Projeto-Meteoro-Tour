package database.dao;

import database.utils.DAOCrud;
import model.entities.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements DAOCrud<Funcionario, String> {
    private static FuncionarioDAO instancia;
    private List<Funcionario> funcionarios = new ArrayList<>();

    private FuncionarioDAO(){
        funcionarios.add(new Funcionario("12345","Gabriel", "54321", "estagiario senior"));
        funcionarios.add(new Funcionario("6789", "Augusto", "9876", "estagiario senior"));
        funcionarios.add(new Funcionario("1011", "Erika", "1101", "product owner"));

    }

    @Override
    public void save(Funcionario model) {
        funcionarios.add(model);
    }

    @Override
    public void update(Funcionario model) {
        funcionarios.set(funcionarios.indexOf(model), model);
    }

    @Override
    public void delete(Funcionario model) {
        funcionarios.remove(model);
    }

    @Override
    public Funcionario selectById(String id) {
        return null;
    }

    @Override
    public List<Funcionario> selectAll() {
        return null;
    }

    @Override
    public List<Funcionario> selectAllByKeyword(String key) {
        return null;
    }

    public List<Funcionario> getListFuncs() {
        return funcionarios;
    }

    public static FuncionarioDAO getInstancia(){
        if (instancia == null) instancia = new FuncionarioDAO();
        return instancia;
    }
}
