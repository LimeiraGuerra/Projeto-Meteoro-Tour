package database.dao;

import database.utils.ConnectionFactory;

import database.utils.DAOCrud;
import model.entities.Onibus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OnibusDAO implements DAOCrud<Onibus,String> {

    @Override
    public void save(Onibus model) {
        //onibus.add(model);
        String sql = "INSERT INTO Onibus (renavam, placa) VALUES (?,?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getRenavam());
            stmt.setString(2,model.getPlaca());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Onibus model) {
        String sql = "UPDATE Onibus SET placa=?  WHERE renavam = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getPlaca());
            stmt.setString(2,model.getRenavam());
            stmt.executeUpdate();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Onibus model) {
        String sql = "DELETE FROM Onibus WHERE renavam = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getRenavam());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Onibus selectById(String id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Onibus> selectAll() {
        List<Onibus> onibus = new ArrayList<>();
        String sql = "SELECT * FROM Onibus";
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                onibus.add(new Onibus(rs.getString("renavam"),rs.getString("placa")));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return onibus;
    }

    @Override
    public List<Onibus> selectAllByKeyword(String key) {
        throw new NotImplementedException();
    }


}
