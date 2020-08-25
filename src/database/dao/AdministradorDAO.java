package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import model.entities.Administrador;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdministradorDAO implements DAOCrud<Administrador, String> {

    @Override
    public void save(Administrador model) {
        throw new NotImplementedException();
    }

    @Override
    public void update(Administrador model) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Administrador model) {
        throw new NotImplementedException();
    }

    @Override
    public Administrador selectById(String id) {
        String sql = "SELECT * FROM administrador WHERE senha = ?";
        Administrador adm = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                adm = new Administrador(rs.getString("senha"));
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adm;
    }

    @Override
    public List<Administrador> selectAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Administrador> selectAllByKeyword(String key) {
        throw new NotImplementedException();
    }
}
