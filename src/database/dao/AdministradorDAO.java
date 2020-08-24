package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.Administrador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AdministradorDAO implements DAO<Administrador, String> {

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
