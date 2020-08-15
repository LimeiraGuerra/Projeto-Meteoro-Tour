package database.dao;

import database.utils.DAO;
import model.entities.Administrador;

import java.util.List;

public class AdministradorDAO implements DAO<Administrador, String> {
    private Administrador administrador = new Administrador();
    private static AdministradorDAO instancia;

    private AdministradorDAO() {
        administrador.setSenha("1234");
        administrador.setNome("José");
    }

    @Override
    public void save(Administrador model) {
        administrador = model;
    }

    public Administrador getAdministrador(){
        return administrador;
    }

    @Override
    public void update(Administrador model) {
    }

    @Override
    public void delete(Administrador model) {
    }

    @Override
    public Administrador selectById(String id) {
        return null;
    }

    @Override
    public List<Administrador> selectAll() {
        return null;
    }

    @Override
    public List<Administrador> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<Administrador> selectByArgs(String... args) {
        return null;
    }

    public static AdministradorDAO getInstancia(){
        if (instancia == null){
            instancia = new AdministradorDAO();
        }
        return instancia;
    }
}
