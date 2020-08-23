package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.Passagem;
import model.entities.TrechoLinha;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PassagemDAO implements DAO<Passagem, String> {
    private Map<String, Passagem> passagens = new HashMap<>();

    @Override
    public void save(Passagem model) {
        String sqlPassagem = "INSERT INTO Passagem (nomeCliente, cpfCliente, rgCliente, telefoneCliente," +
                "precoPago, seguro, cidadeOrigem, cidadeDestino, idLinha, dataCompra, dataViagem) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, datetime(?), datetime(?));";
        String sqlLink = "INSERT INTO TrechoLinhaPassagem (idPassagem, idTrechoLinha) VALUES (?, ?);";
        String sqlAssento = "INSERT INTO AssentoTrechoLinha (data, idTrechoLinha, idAssento)" +
                "VALUES (date(?, '+'||?||' day'), ?, ?);";

        try(PreparedStatement stmtPassagem = ConnectionFactory.createPreparedStatement(sqlPassagem);
                PreparedStatement stmtLink = ConnectionFactory.createPreparedStatement(sqlLink);
                PreparedStatement stmtAssento = ConnectionFactory.createPreparedStatement(sqlAssento)) {
            ConnectionFactory.getConnection().setAutoCommit(false);
            this.setKeysAndExecuteStatementPassagem(stmtPassagem, model);
            ResultSet rs = stmtPassagem.getGeneratedKeys();
            if (rs.next()){
                model.setNumPassagem(rs.getLong(1));
                this.setBatchForPassagem(stmtLink, stmtAssento, model);
                ConnectionFactory.getConnection().commit();
            }else ConnectionFactory.executeRollBack();
        } catch (SQLException throwables) {
            ConnectionFactory.executeRollBack();
            throwables.printStackTrace();
        }
    }

    private void setBatchForPassagem(PreparedStatement stmtLink, PreparedStatement stmtAssento, Passagem model)
            throws SQLException {
        Iterator<TrechoLinha> itTl = model.getViagem().getTrechosLinha();
        while (itTl.hasNext()) {
            TrechoLinha tl = itTl.next();
            this.setKeysStatementLink(stmtLink, model.getNumPassagem(), tl.getId());
            this.setKeysStatementAssento(stmtAssento, model, tl);
        }
        stmtLink.executeBatch();
        stmtAssento.executeBatch();
    }

    private void setKeysAndExecuteStatementPassagem(PreparedStatement stmt, Passagem model) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        stmt.setString(1, model.getNome());
        stmt.setString(2, model.getCpf());
        stmt.setString(3, model.getRg());
        stmt.setString(4, model.getTelefone());
        stmt.setDouble(5, model.getPrecoPago());
        stmt.setInt(6, model.isSeguro() ? 1 : 0);
        stmt.setString(7, model.getCidadeOrigem());
        stmt.setString(8, model.getCidadeDestino());
        stmt.setLong(9, model.getLinha().getId());
        stmt.setString(10, dateFormat.format(model.getDataCompra()));
        stmt.setString(11, dateFormat.format(model.getDataViagem()));
        stmt.execute();
    }

    private void setKeysStatementLink(PreparedStatement stmt, long numPassagem, long idTrechoLinha)
            throws SQLException {
        stmt.setLong(1, numPassagem);
        stmt.setLong(2, idTrechoLinha);
        stmt.addBatch();
    }

    private void setKeysStatementAssento(PreparedStatement stmt, Passagem model, TrechoLinha tl)
            throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        stmt.setString(1, dateFormat.format(model.getDataViagem()));
        stmt.setInt(2, tl.getdPlus()-model.getViagem().getDatePlusCorrection());
        stmt.setLong(3, tl.getId());
        stmt.setString(4, model.getAssentoId());
        stmt.addBatch();
    }

    @Override
    public void update(Passagem model) {

    }

    @Override
    public void delete(Passagem model) {
        passagens.remove(""+model.getNumPassagem());
    }

    @Override
    public Passagem selectById(String id) {
        String sql = "SELECT p.*";
        return passagens.containsKey(id) ? passagens.get(id) : null;
    }

    @Override
    public List<Passagem> selectAll() {
        return null;
    }

    @Override
    public List<Passagem> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<Passagem> selectByArgs(String... args) {
        List<Passagem> matchP = new ArrayList<>();
        for(Passagem p : passagens.values())
            if (args[0].equals(p.getCpf())) matchP.add(p);
        return matchP;
    }

    public List<Passagem> selectByDateInterval(String ini, String end){
        List<Passagem> pAux = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (Passagem p : passagens.values()){
            String date = df.format(p.getDataViagem());
            if (ini.compareTo(date) <= 0 && end.compareTo(date) >= 0){
                pAux.add(p);
            }
        }
        return pAux;
    }
}
