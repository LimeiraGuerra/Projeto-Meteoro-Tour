package database.dao;
import database.utils.DAO;
import model.entities.Linha;
import database.utils.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinhaDAO implements DAO<Linha, String> {

    @Override
    public void save(Linha model){
        String sqlLinha = "INSERT INTO LINHA(nome) VALUES(?);";
        try(PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlLinha)){
            stmtLinha.setString(1, model.getNome());
            stmtLinha.execute();
            ConnectionFactory.closeStatements(stmtLinha);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Linha model) {
        String sqlEdite = "UPDATE linha set nome = ? where id = ?;";
        try(PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlEdite)){
            stmtLinha.setString(1, model.getNome());
            stmtLinha.setLong(2, model.getId());
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
        try(PreparedStatement stmtLinha = ConnectionFactory.createPreparedStatement(sqlEdite)){
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
        long num = Long.parseLong(id);
        String sql = "Select * from linha where id = ?;";
        Linha linha = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setLong(1, num);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                linha = new Linha(rs.getLong(1), rs.getString(2));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linha;
    }

    @Override
    public List<Linha> selectAll() {
        String sql = "Select * from linha;";
        List<Linha> linhas = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Linha linha = new Linha(rs. getLong(1), rs.getString(2));
                linhas.add(linha);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linhas;
    }

    @Override
    public List<Linha> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<Linha> selectByArgs(String... args) {
        /*args[0] = cidadeOrigem
         * args[1] = cidadeDestino
         */
        String sql = "SELECT * FROM vLinhaByCidades WHERE idLinha IN (\n"
                + "SELECT tl.idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeOrigem = ? AND idLinha IN (\n"
                + "SELECT idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeDestino = ?));";
        List<Linha> linhas = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            setKeysCidades(stmt, args[0], args[1]);
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
            linhas.add(new Linha(rs.getLong("idLinha"), rs.getString("nomeLinha")));
        return linhas;
    }

    private void setKeysCidades(PreparedStatement stmt, String cidadeOrigem, String cidadeDestino)
            throws SQLException {
        stmt.setString(1, cidadeOrigem);
        stmt.setString(2, cidadeDestino);
    }
}
