package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.Trecho;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TrechoDAO implements DAO<Trecho, String> {

    @Override
    public void save(Trecho model) {

        String sqlTrecho = "INSERT INTO Trecho(cidadeOrigem, cidadeDestino, quilometragem, tempoDuracao," +
                " valorPassagem, taxaEmbarque, valorSeguro)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmtTrecho = ConnectionFactory.createPreparedStatement(sqlTrecho)) {
            setStatementTrechoSave(stmtTrecho, model);
            ConnectionFactory.closeStatements(stmtTrecho);
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }

    private void setStatementTrechoSave(PreparedStatement stmt, Trecho t) throws SQLException {
        stmt.setString(1, t.getCidadeOrigem());
        stmt.setString(2, t.getCidadeDestino());
        stmt.setDouble(3, t.getQuilometragem());
        stmt.setInt(4, t.getTempoDuracao());
        stmt.setDouble(5, t.getValorPassagem());
        stmt.setDouble(6, t.getTaxaEmbarque());
        stmt.setDouble(7, t.getValorSeguro());
        stmt.execute();
    }

    private void setStatementTrechoEdit(PreparedStatement stmt, Trecho t) throws SQLException {
        stmt.setDouble(1, t.getQuilometragem());
        stmt.setInt(2, t.getTempoDuracao());
        stmt.setDouble(3, t.getValorPassagem());
        stmt.setDouble(4, t.getTaxaEmbarque());
        stmt.setDouble(5, t.getValorSeguro());
        stmt.setString(6, t.getCidadeOrigem());
        stmt.setString(7, t.getCidadeDestino());
        stmt.execute();
    }

    @Override
    public void update(Trecho model) {
        String sql = "UPDATE trecho set quilometragem = ?, tempoDuracao  = ?,  valorPassagem = ?, taxaEmbarque = ?, valorSeguro = ? where cidadeOrigem = ? and cidadeDestino = ?";
        try (PreparedStatement stmtTrecho = ConnectionFactory.createPreparedStatement(sql)) {
            setStatementTrechoEdit(stmtTrecho, model);
            ConnectionFactory.closeStatements(stmtTrecho);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Trecho model) {
        String sqlDelete = "DELETE FROM trecho where cidadeOrigem = ? and cidadeDestino = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlDelete)) {
            stmt.setString(1, model.getCidadeOrigem());
            stmt.setString(2, model.getCidadeDestino());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Trecho selectById(String id) {
        String sqlSelect = "Select * from trecho where id = ?;";
        Trecho t = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlSelect)) {
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                t = new Trecho(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(1));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return t;
    }

    @Override
    public List<Trecho> selectAll() {
        String sqlSelect = "Select * from trecho;";
        ArrayList<Trecho> trechos = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlSelect)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Trecho t = new Trecho(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(1));
                trechos.add(t);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trechos;
    }

    @Override
    public List<Trecho> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<Trecho> selectByArgs(String... args) {
        String sqlSelect = "Select * from trecho where cidadeOrigem = ? and cidadeDestino = ?;";
        ArrayList<Trecho> trechos = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlSelect)) {
            stmt.setString(1, args[0]);
            stmt.setString(2, args[1]);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Trecho t = new Trecho(rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(1));
                trechos.add(t);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trechos;
    }
}
