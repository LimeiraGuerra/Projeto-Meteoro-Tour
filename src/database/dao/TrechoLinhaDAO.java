package database.dao;
import database.utils.ConnectionFactory;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import java.sql.PreparedStatement;
import database.utils.DAOCrud;
import database.utils.DAOSelects;
import model.entities.Linha;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrechoLinhaDAO implements DAOCrud<TrechoLinha, String>, DAOSelects<TrechoLinha, Linha> {

    private LinhaDAO linhadao = new LinhaDAO();
    private TrechoDAO trechoDAO = new TrechoDAO();

    @Override
    public void save(TrechoLinha model) {
        String sql = "INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time(?), ?, ?, ?, ?);";
        try(PreparedStatement stmtTrechoLinha = ConnectionFactory.createPreparedStatement(sql)){
            setStatementTrecho(stmtTrechoLinha, model);
            ConnectionFactory.closeStatements(stmtTrechoLinha);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(TrechoLinha model) {
        String sqlEdite = "UPDATE trechoLinha set horarioSaida = ? where id = ?;";
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        try(PreparedStatement stmtTrechoL = ConnectionFactory.createPreparedStatement(sqlEdite)){
            stmtTrechoL.setString(1, formatador.format(model.getHorarioSaida()));
            stmtTrechoL.setLong(2, model.getId());
            stmtTrechoL.execute();
            ConnectionFactory.closeStatements(stmtTrechoL);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(TrechoLinha model) {
        String sqlDelete = "DELETE FROM trechoLinha where id = ?;";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sqlDelete)) {
            stmt.setLong(1, model.getId());
            stmt.execute();
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public TrechoLinha selectById(String id) {
        int num = Integer.parseInt(id);
        String sql = "Select * from TrechoLinha where id = ?;";
        TrechoLinha trechoL = null;
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, num);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                trechoL = new TrechoLinha(rs.getInt(3),
                        formatador.parse(rs.getString(2)),
                        rs.getInt(4),
                        trechoDAO.selectById(rs.getString(6)),
                        linhadao.selectById(rs.getString(5)),
                        rs.getLong(1));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return trechoL;
    }

    @Override
    public List<TrechoLinha> selectAll() {
        String sql = "Select * from TrechoLinha;";
        List<TrechoLinha> trechosLinha = new ArrayList<>();
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TrechoLinha trecholinha = new TrechoLinha(rs.getInt(3),
                        formatador.parse(rs.getString(2)),
                        rs.getInt(4),
                        trechoDAO.selectById(rs.getString(6)),
                        linhadao.selectById(rs.getString(5)),
                        rs.getLong(1));
                trechosLinha.add(trecholinha);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return trechosLinha;
    }

    @Override
    public List<TrechoLinha> selectByParent(Linha parent) {
        String sql = "SELECT * FROM vTrechoLinhaByLinha WHERE idLinha = "+ parent.getId() +";";
        List<TrechoLinha> tls = null;
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            tls = new ArrayList<>();
            while (rs.next()){
                TrechoLinha tl = this.setResultTrechoLinha(rs);
                tl.setTrecho(this.setResultTrecho(rs));
                tl.setLinha(parent);
                tls.add(tl);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return tls;
    }

    private void setStatementTrecho(PreparedStatement stmt, TrechoLinha model) throws SQLException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        stmt.setString(1, formatador.format(model.getHorarioSaida()));
        stmt.setInt(2, model.getOrdem());
        stmt.setInt(3, model.getdPlus());
        stmt.setLong(4, model.getLinha().getId());
        stmt.setInt(5, model.getTrecho().getId());
        stmt.execute();
    }

    private Trecho setResultTrecho(ResultSet rs) throws SQLException {
        return new Trecho(rs.getString("cidadeOrigem"), rs.getString("cidadeDestino"),
                rs.getDouble("quilometragem"),
                rs.getInt("tempoDuracao"),
                rs.getDouble("valorPassagem"),
                rs.getDouble("taxaEmbarque"),
                rs.getDouble("valorSeguro"),
                rs.getInt(1));
    }

    private TrechoLinha setResultTrechoLinha(ResultSet rs) throws SQLException, ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        return new TrechoLinha(rs.getLong(1),
                formatador.parse(rs.getString("horarioSaida")),
                rs.getInt("ordem"),
                rs.getInt("dPlus"));
    }

    public List<TrechoLinha> selectByInterval(Linha ini, Linha end) {
        throw new NotImplementedException();
    }


    @Override
    public List<TrechoLinha> selectAllByKeyword(String key) {
        String sql = "SELECT * FROM  trechoLinha WHERE idTrecho ="+key+";";
        List<TrechoLinha> tls = new ArrayList<>();
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                TrechoLinha tl = this.setResultTrechoLinha(rs);
                tls.add(tl);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return tls;
    }

}
