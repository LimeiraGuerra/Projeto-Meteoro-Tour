package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.Administrador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AdministradorDAO implements DAO<Administrador, String> {

    public Administrador getAdministrador(){
        String sqlAdm = "SELECT * FROM administrador;";
        Administrador administrador = null;
        try(PreparedStatement stmtAdm = ConnectionFactory.createPreparedStatement(sqlAdm)){
            ResultSet rs = stmtAdm.executeQuery();
            while(rs.next()){
                administrador = (new Administrador(rs.getString("senha")));
            }
            ConnectionFactory.closeStatements(stmtAdm);
        }catch (Exception throwables){
            throwables.printStackTrace();
        }
        return administrador;
    }

    @Override
    public void save(Administrador model) {
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


}
