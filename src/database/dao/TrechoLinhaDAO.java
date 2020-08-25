package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import database.utils.DAOSelects;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TrechoLinhaDAO implements DAOCrud<TrechoLinha, String>, DAOSelects<TrechoLinha, Linha> {

    @Override
    public void save(TrechoLinha model) {

    }

    @Override
    public void update(TrechoLinha model) {
    }

    @Override
    public void delete(TrechoLinha model) {

    }

    @Override
    public TrechoLinha selectById(String id) {
        return null;
    }

    @Override
    public List<TrechoLinha> selectAll() {
        return null;
    }

    @Override
    public List<TrechoLinha> selectAllByKeyword(String key) {return null;}

    public TrechoLinha searchTrechoLinha(TrechoLinha trechoLinha){
        return null;
    }
    public List<TrechoLinha> getListTrechoLinha(){
        return null;
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

    private Trecho setResultTrecho(ResultSet rs) throws SQLException {
        return new Trecho(rs.getString("cidadeOrigem"),
                rs.getString("cidadeDestino"),
                rs.getDouble("quilometragem"),
                rs.getInt("tempoDuracao"),
                rs.getDouble("valorPassagem"),
                rs.getDouble("taxaEmbarque"),
                rs.getDouble("valorSeguro"));
    }

    private TrechoLinha setResultTrechoLinha(ResultSet rs) throws SQLException, ParseException {
        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        return new TrechoLinha(rs.getLong("idTrechoLinha"),
                formatador.parse(rs.getString("horarioSaida")),
                rs.getInt("ordem"),
                rs.getInt("dPlus"));
    }

    @Override
    public List<TrechoLinha> selectByInterval(Linha ini, Linha end) {
        return null;
    }
}
