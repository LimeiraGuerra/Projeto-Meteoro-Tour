package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrechoLinhaDAO implements DAO<TrechoLinha, String> {

    private List<TrechoLinha> trechosLinhas = new ArrayList<>();
    private List<AssentoTrechoLinha> assentosVendScuff = new ArrayList<>();
    private LinhaDAO linhadao = new LinhaDAO();
    private TrechoDAO trechoDAO = new TrechoDAO();

    /*public List<TrechoLinha> selectTrechosByLinha(Linha linha, String data){
        List<TrechoLinha> trechosLinhaTemp = new ArrayList<>();
        int cont = 0;
        for(TrechoLinha tl : trechosLinhas){
            if(tl.getLinha().equals(linha)){
                if(data != null && assentosVendScuff.get(cont).getData().compareTo(java.sql.Date.valueOf(data)) == 0) {
                    tl.setAssentoTrechoLinha(assentosVendScuff.get(cont));
                }
                else tl.setAssentoTrechoLinha(null);
                cont++;
                trechosLinhaTemp.add(tl);
            }
        }
        return trechosLinhaTemp;
    }*/

    @Override
    public void save(TrechoLinha model) {
        String sql = "INSERT INTO TrechoLinha(horarioSaida, ordem, dPlus, idLinha, idTrecho) VALUES(time(?), ?, ?, ?, ?);";
        try(PreparedStatement stmtTrechoLinha = ConnectionFactory.createPreparedStatement(sql)){
            setStatementTrecho(stmtTrechoLinha, model);
            ConnectionFactory.closeStatements(stmtTrechoLinha);
        }
        catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setStatementTrecho(PreparedStatement stmt, TrechoLinha model) throws ParseException, SQLException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        stmt.setString(1, formatador.format(model.getHorarioSaida()));
        stmt.setInt(2, model.getOrdem());
        stmt.setInt(3, model.getdPlus());
        stmt.setLong(4, model.getLinha().getId());
        stmt.setInt(5, model.getTrecho().getId());
        stmt.execute();
    }

    @Override
    public void update(TrechoLinha model) {
        TrechoLinha tLinha = searchTrechoLinha(model);
        tLinha.setHorarioSaida(model.getHorarioSaida());
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
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        TrechoLinha trechoL = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            stmt.setInt(1, num);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 trechoL = new TrechoLinha(rs.getInt(3), formatador.parse(rs.getString(2)), rs.getInt(4), trechoDAO.selectById(rs.getString(6)), linhadao.selectById(rs.getString(5)));
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
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        List<TrechoLinha> trechosLinha = new ArrayList<>();
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TrechoLinha trecholinha = new TrechoLinha(rs.getInt(3), formatador.parse(rs.getString(2)), rs.getInt(4), trechoDAO.selectById(rs.getString(6)), linhadao.selectById(rs.getString(5)));
                trechosLinha.add(trecholinha);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return trechosLinha;


    }

    @Override
    public List<TrechoLinha> selectAllByArg(String arg) {
        /*arg = idLinha*/
        String sql = "SELECT * FROM vTrechoLinhaByLinha WHERE idLinha = "+arg+";";
        List<TrechoLinha> tls = null;
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            tls = new ArrayList<>();
            while (rs.next()){
                TrechoLinha tl = this.setResultTrechoLinha(rs);
                tl.setTrecho(this.setResultTrecho(rs));
                tls.add(tl);
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return tls;
    }

    private Trecho setResultTrecho(ResultSet rs) throws SQLException {
        return new Trecho(rs.getString("cidadeOrigem"), rs.getString("cidadeDestino"),
                rs.getDouble("quilometragem"),
                rs.getInt("tempoDuracao"),
                rs.getDouble("valorPassagem"),
                rs.getDouble("taxaEmbarque"),
                rs.getDouble("valorSeguro"),
                rs.getInt("id"));
    }

    private TrechoLinha setResultTrechoLinha(ResultSet rs) throws SQLException, ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        return new TrechoLinha(rs.getLong("idTrechoLinha"),
                formatador.parse(rs.getString("horarioSaida")),
                rs.getInt("ordem"),
                rs.getInt("dPlus"));
    }


    @Override
    public List<TrechoLinha> selectByArgs(String... args) {
        return null;
    }

    public TrechoLinha searchTrechoLinha(TrechoLinha trechoLinha){
        return trechosLinhas.contains(trechoLinha) ? trechoLinha : null;
    }
    public List<TrechoLinha> getListTrechoLinha(){
        return trechosLinhas;
    }
}
