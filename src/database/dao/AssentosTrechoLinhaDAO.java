package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOSelects;
import model.entities.Viagem;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssentosTrechoLinhaDAO implements DAOSelects<String, Viagem> {

    @Override
    public List<String> selectByParent(Viagem parent) {
        String sql = "SELECT ast.idAssento FROM AssentoTrechoLinha ast\n" +
                "JOIN TrechoLinha tl ON tl.id = ast.idTrechoLinha\n" +
                "AND tl.id IN ("+parent.getTrechoLinhaIdsAndSetValues()+")\n" +
                "AND ast.data = date('"+this.getStringData(parent.getData())+
                "', '+'||(tl.dPlus-"+parent.getDatePlusCorrection()+")||' day')\n" +
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

    @Override
    public List<String> selectByInterval(Viagem ini, Viagem end) {
        throw new NotImplementedException();
    }
}
