package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import database.utils.DAOSelects;
import model.entities.InfoLinhaTrechoRelatorio;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoRelatorioDAO implements DAOSelects<InfoLinhaTrechoRelatorio, String> {

    @Override
    public List<String> selectStringForAutoComplete() {
        throw new NotImplementedException();
    }

    @Override
    public List<InfoLinhaTrechoRelatorio> selectByParent(String parent) {
        throw new NotImplementedException();
    }

    @Override
    public List<InfoLinhaTrechoRelatorio> selectByInterval(String ini, String end) {
        String sql = "SELECT * FROM vInfoRelatorio where " +
                this.chooseDataColumnForSearch(ini, end) + " BETWEEN date(?) AND date(?);";
        List<InfoLinhaTrechoRelatorio> infosRelatorio = new ArrayList<>();
        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            this.setKeysToStatement(stmt, ini, end);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                infosRelatorio.add(this.setValuesToModel(rs));
            ConnectionFactory.closeStatements(stmt);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return infosRelatorio;
    }

    private String chooseDataColumnForSearch(String ini, String end){
        return (ini.length()==10 && end.length()==10) ? "data" : "dataCompra";
    }

    private void setKeysToStatement(PreparedStatement stmt, String ini, String end) throws SQLException {
        stmt.setString(1, ini);
        stmt.setString(2, end);
    }

    private InfoLinhaTrechoRelatorio setValuesToModel(ResultSet rs) throws SQLException {
        return new InfoLinhaTrechoRelatorio(rs.getString("data"),
                rs.getString("horarioSaida"),
                rs.getString("nomeLinha"),
                rs.getString("cidadeOrigem"),
                rs.getString("cidadeDestino"),
                rs.getInt("uso"),
                rs.getDouble("lucro"));
    }
}
