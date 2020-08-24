package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
import model.entities.Viagem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssentosTrechoLinhaDAO implements DAO<AssentoTrechoLinha, String> {
    @Override
    public void save(AssentoTrechoLinha model) {

    }

    @Override
    public void update(AssentoTrechoLinha model) {

    }

    @Override
    public void delete(AssentoTrechoLinha model) {

    }

    @Override
    public AssentoTrechoLinha selectById(String id) {
        return null;
    }

    @Override
    public List<AssentoTrechoLinha> selectAll() {
        return null;
    }

    @Override
    public List<AssentoTrechoLinha> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<AssentoTrechoLinha> selectByArgs(String... args) {
        /**args[0] = idTrechoLinha todo
         * args[1] = data
         * args[2] = correção dPlus
         */
        String sql = "SELECT ast.* FROM AssentoTrechoLinha ast\n" +
                "JOIN TrechoLinha tl ON tl.id = ast.idTrechoLinha\n" +
                "AND tl.id = "+args[0]+"\n" +
                "AND ast.data = date('"+args[1]+"', '+'||(tl.dPlus-"+args[2]+")||' day');";
        List<AssentoTrechoLinha> ast = null;
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                ast = new ArrayList<>();
                ast.add(this.setSoldAssentos(rs));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return ast;
    }

    public List<String> selectAllAssentosVendidos(Viagem viagem, String groupTrechoLinhaId){
        String sql = "SELECT ast.idAssento FROM AssentoTrechoLinha ast\n" +
                "JOIN TrechoLinha tl ON tl.id = ast.idTrechoLinha\n" +
                "AND tl.id IN ("+groupTrechoLinhaId+")\n" +
                "AND ast.data = date('"+this.getStringData(viagem.getData())+
                "', '+'||(tl.dPlus-"+viagem.getDatePlusCorrection()+")||' day')\n" +
                "GROUP BY ast.idAssento;";
        List<String> ast = new ArrayList<>();
        try (Statement stmt = ConnectionFactory.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ast.add(rs.getString("idAssento"));
            }
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ast;
    }

    private String getStringData(Date data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(data);
    }

    private AssentoTrechoLinha setSoldAssentos(ResultSet rs) throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        AssentoTrechoLinha ast = new AssentoTrechoLinha(dateFormat.parse(rs.getString("data")));
        for (int i = 1; i < 45; i++) {
            if (rs.getInt(i+3) == 1) {
                ast.addAssentoVendido(String.format("%02d", i));
            }
        }
        return ast;
    }
}
