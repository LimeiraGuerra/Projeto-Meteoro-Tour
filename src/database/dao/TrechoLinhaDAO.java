package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
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

public class TrechoLinhaDAO implements DAO<TrechoLinha, String> {

    private List<TrechoLinha> trechosLinhas = new ArrayList<>();
    private List<AssentoTrechoLinha> assentosVendScuff = new ArrayList<>();

    public TrechoLinhaDAO() {
    }

    public List<TrechoLinha> selectTrechosByLinha(Linha linha, String data){
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
    }

    @Override
    public void save(TrechoLinha model) {
        trechosLinhas.add(model);
    }

    @Override
    public void update(TrechoLinha model) {
        TrechoLinha tLinha = searchTrechoLinha(model);
        tLinha.setHorarioSaida(model.getHorarioSaida());
    }

    @Override
    public void delete(TrechoLinha model) {
        trechosLinhas.remove(model);
    }

    @Override
    public TrechoLinha selectById(String id) {
        int num = Integer.parseInt(id);
        return trechosLinhas.get(num);
    }

    @Override
    public List<TrechoLinha> selectAll() {
        return null;
    }

    @Override
    public List<TrechoLinha> selectAllByArg(String arg) {
        /**arg = idLinha*/
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
