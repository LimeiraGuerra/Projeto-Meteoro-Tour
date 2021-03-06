package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import model.entities.Linha;
import model.entities.Passagem;
import model.entities.TrechoLinha;
import model.entities.Viagem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PassagemDAO implements DAOCrud<Passagem, String> {

    @Override
    public void save(Passagem model) {
        String sqlPassagem = "INSERT INTO Passagem (nomeCliente, cpfCliente, rgCliente, telefoneCliente," +
                "desconto, seguro, dataCompra) VALUES (?, ?, ?, ?, ?, ?, datetime(?));";
        String sqlViagem = "INSERT INTO Viagem (idPassagem, data, cidadeOrigem, cidadeDestino, idLinha, nomeLinha) " +
                "VALUES (?, datetime(?), ?, ?, ?, ?);";
        String sqlAssento = "INSERT INTO AssentoTrechoLinha (data, idTrechoLinha, idPassagem, idAssento, " +
                "precoPago, precoTrecho, seguroTrecho) VALUES (datetime(?, '+'||?||' day'), ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement stmtPassagem = ConnectionFactory.createPreparedStatement(sqlPassagem);
            PreparedStatement stmtViagem = ConnectionFactory.createPreparedStatement(sqlViagem);
            PreparedStatement stmtAssento = ConnectionFactory.createPreparedStatement(sqlAssento)) {
            ConnectionFactory.getConnection().setAutoCommit(false);
            this.setKeysAndExecuteStatementPassagem(stmtPassagem, model);
            ResultSet rs = stmtPassagem.getGeneratedKeys();
            if (rs.next()){
                model.setNumPassagem(rs.getLong(1));
                this.setKeysAndExecuteStatementViagem(stmtViagem, model);
                this.setBatchForAssento(stmtAssento, model);
                ConnectionFactory.getConnection().commit();
            }else ConnectionFactory.executeRollBack();
            ConnectionFactory.closeStatements(stmtAssento, stmtViagem, stmtPassagem);
        } catch (SQLException throwables) {
            ConnectionFactory.executeRollBack();
            throwables.printStackTrace();
        }
    }

    private void setBatchForAssento(PreparedStatement stmt, Passagem model)
            throws SQLException {
        Iterator<TrechoLinha> itTl = model.getViagem().getTrechosLinha();
        while (itTl.hasNext())
            this.setKeysStatementAssento(stmt, model, itTl.next());
        stmt.executeBatch();
    }

    private void setKeysAndExecuteStatementPassagem(PreparedStatement stmt, Passagem model) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        stmt.setString(1, model.getNome());
        stmt.setString(2, model.getCpf());
        stmt.setString(3, model.getRg());
        stmt.setString(4, model.getTelefone());
        stmt.setDouble(5, model.getDiscount());
        stmt.setInt(6, model.isSeguro() ? 1 : 0);
        stmt.setString(7, dateFormat.format(model.getDataCompra()));
        stmt.execute();
    }

    private void setKeysAndExecuteStatementViagem(PreparedStatement stmt, Passagem model)
            throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        stmt.setLong(1, model.getNumPassagem());
        stmt.setString(2, dateFormat.format(model.getViagem().getData()));
        stmt.setString(3, model.getViagem().getCidadeOrigem());
        stmt.setString(4, model.getViagem().getCidadeDestino());
        stmt.setLong(5, model.getViagem().getLinha().getId());
        stmt.setString(6, model.getViagem().getLinha().getNome());
        stmt.execute();
    }

    private void setKeysStatementAssento(PreparedStatement stmt, Passagem model, TrechoLinha tl)
            throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        stmt.setString(1, dateFormat.format(model.getViagem().getData()));
        stmt.setInt(2, tl.getdPlus()-model.getViagem().getDatePlusCorrection());
        stmt.setLong(3, tl.getId());
        stmt.setLong(4, model.getNumPassagem());
        stmt.setString(5, model.getAssentoId());
        Double precoPago = tl.getTrecho().getValorTotal()*model.getDiscount()+
                (model.isSeguro() ? tl.getTrecho().getValorSeguro() : 0.0);
        stmt.setDouble(6, precoPago);
        stmt.setDouble(7, tl.getTrecho().getValorTotal());
        stmt.setDouble(8, tl.getTrecho().getValorSeguro());
        stmt.addBatch();
    }

    @Override
    public void delete(Passagem model) {
        String sql = "DELETE FROM Passagem WHERE numPassagem = ?;";
        try(Statement stmtConfig = ConnectionFactory.createStatement();
                PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmtConfig.execute("PRAGMA foreign_keys=on;");
            stmt.setLong(1, model.getNumPassagem());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Passagem selectById(String id) {
        String sql = "SELECT * FROM vPassagensVendidas WHERE numPassagem = ?";
        Passagem passagem = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                passagem = this.setResultPassagem(rs);
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return passagem;
    }

    private Passagem setResultPassagem(ResultSet rs) throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Passagem passagem = new Passagem(rs.getLong("numPassagem"),
                rs.getDouble("precoPago"),
                rs.getDouble("desconto"),
                rs.getString("nomeCliente"),
                rs.getString("cpfCliente"),
                rs.getString("rgCliente"),
                rs.getString("telefoneCliente"),
                rs.getBoolean("seguro"),
                dateFormat.parse(rs.getString("dataCompra")),
                rs.getString("idAssento"));
        passagem.setViagem(new Viagem(dateFormat.parse(rs.getString("dataViagem")),
                rs.getString("cidadeOrigem"),
                rs.getString("cidadeDestino"),
                rs.getDouble("valorViagem"),
                rs.getDouble("valorSeguro")));
        passagem.getViagem().setLinha(new Linha(rs.getLong("idLinha"),
                rs.getString("nomeLinha")));
        return passagem;
    }

    @Override
    public List<Passagem> selectAllByKeyword(String key) {
        String sql = "SELECT * FROM vPassagensVendidas WHERE cpfCliente = ?";
        List<Passagem> passagens = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                passagens.add(this.setResultPassagem(rs));
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return passagens;
    }

    @Override
    public void update(Passagem model) {
        throw new NotImplementedException();
    }

    @Override
    public List<Passagem> selectAll() {
        throw new NotImplementedException();
    }

}
