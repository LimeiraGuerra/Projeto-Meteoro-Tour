package database.dao;

import model.entities.Linha;
import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import database.utils.DAOSelects;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LinhaDAO implements DAOCrud<Linha, String>, DAOSelects<Linha, String> {

    @Override
    public void save(Linha model) {
        String sqlLinha = "INSERT INTO LINHA(nome, inativo) VALUES(?, ?);";
        try (PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlLinha)) {
            stmtLinha.setString(1, model.getNome());
            stmtLinha.setBoolean(2, model.isInativo());
            stmtLinha.execute();
            ConnectionFactory.closeStatements(stmtLinha);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Linha model) {
        String sqlEdite = "UPDATE linha set nome = ?, inativo = ? where id = ?;";
        try(PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlEdite)){
            stmtLinha.setString(1, model.getNome());
            stmtLinha.setBoolean(2, model.isInativo());
            stmtLinha.setLong(3, model.getId());
            stmtLinha.execute();
            ConnectionFactory.closeStatements(stmtLinha);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Linha model) {
        String sqlEdite = "Delete from linha where id = ?;";
        try(Statement stmtConfig = ConnectionFactory.createStatement();
                PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlEdite)){
            stmtConfig.execute("PRAGMA foreign_keys=on;");
            stmtLinha.setLong(1, model.getId());
            stmtLinha.execute();
            ConnectionFactory.closeStatements(stmtLinha);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Linha selectById(String id) {
        String sql = "SELECT * FROM LINHA WHERE id = (SELECT idLinha FROM vPassagensVendidas where idLinha = ?) and inativo = 0;";
        Linha linha = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                linha = new Linha(rs.getLong(1), rs.getString(2), rs.getBoolean(3));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linha;
    }

    @Override
    public List<Linha> selectAll() {
        String sql = "Select * from linha where inativo = 0;";
        List<Linha> linhas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Linha linha = new Linha(rs. getLong(1), rs.getString(2), rs.getBoolean(3));
                linhas.add(linha);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linhas;
    }

    @Override
    public List<Linha> selectByInterval(String ini, String end) {
        String sql = "SELECT * FROM vLinhaByCidades WHERE idLinha IN (\n"
                + "SELECT tl.idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeOrigem = ? AND idLinha IN (\n"
                + "SELECT idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeDestino = ? and inativo = 0));";
        List<Linha> linhas = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            setKeysCidades(stmt, ini, end);
            linhas = setResultLinhas(stmt.executeQuery());
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linhas;
    }

    private List<Linha> setResultLinhas(ResultSet rs) throws SQLException {
        List<Linha> linhas = new ArrayList<>();
        while (rs.next())
            linhas.add(new Linha(rs.getLong("idLinha"), rs.getString("nomeLinha"), rs.getBoolean("inativo")));
        return linhas;
    }

    private void setKeysCidades(PreparedStatement stmt, String cidadeOrigem, String cidadeDestino)
            throws SQLException {
        stmt.setString(1, cidadeOrigem);
        stmt.setString(2, cidadeDestino);
    }

    @Override
    public List<String> selectStringForAutoComplete() {
        String sql = "SELECT nome FROM Linha;";
        List<String> names = new ArrayList<>();
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
                names.add(rs.getString(1));
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return names;
    }

    @Override
    public List<Linha> selectByParent(String parent) {
        throw new NotImplementedException();
    }

    @Override
    public List<Linha> selectAllByKeyword(String key) {
        throw new NotImplementedException();
    }

}
