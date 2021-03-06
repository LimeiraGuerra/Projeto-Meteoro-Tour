package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import model.entities.Funcionario;
import model.entities.Onibus;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements DAOCrud<Funcionario, String> {

    @Override
    public void save(Funcionario model) {
        String sql = "INSERT INTO Funcionario (cpf, rg, nome, cargo) VALUES (?,?,?,?)";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getCpf());
            stmt.setString(2, model.getRg());
            stmt.setString(3, model.getNome());
            stmt.setString(4, model.getCargo());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Funcionario model) {
        String sql = "UPDATE Funcionario SET rg=?, nome= ?, cargo= ?  WHERE cpf = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getRg());
            stmt.setString(2,model.getNome());
            stmt.setString(3,model.getCargo());
            stmt.setString(4,model.getCpf());
            stmt.executeUpdate();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Funcionario model) {
        String sql = "DELETE FROM Funcionario WHERE cpf = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, model.getCpf());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Funcionario selectById(String id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Funcionario> selectAll() {
        List<Funcionario> funcs= new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                funcs.add(new Funcionario(rs.getString("cpf"),rs.getString("nome"), rs.getString("rg"),rs.getString("cargo")));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return funcs;
    }

    @Override
    public List<Funcionario> selectAllByKeyword(String key) {
        throw new NotImplementedException();
    }


}
