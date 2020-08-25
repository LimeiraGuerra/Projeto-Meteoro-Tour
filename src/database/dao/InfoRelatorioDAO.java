package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.InfoLinhaTrechoRelatorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfoRelatorioDAO implements DAO<InfoLinhaTrechoRelatorio, String> {

    @Override
    public void save(InfoLinhaTrechoRelatorio model) {

    }

    @Override
    public void update(InfoLinhaTrechoRelatorio model) {

    }

    @Override
    public void delete(InfoLinhaTrechoRelatorio model) {

    }

    @Override
    public InfoLinhaTrechoRelatorio selectById(String id) {
        return null;
    }

    @Override
    public List<InfoLinhaTrechoRelatorio> selectAll() {
        return null;
    }

    @Override
    public List<InfoLinhaTrechoRelatorio> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<InfoLinhaTrechoRelatorio> selectByArgs(String... args) {
        return this.findInfoByInterval(args[0], args[1]);
    }

    public List<InfoLinhaTrechoRelatorio> findInfoByInterval(String ini, String end){
        String sql = "SELECT * FROM vInfoRelatorio where data BETWEEN ? AND ?;";
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

    private void setKeysToStatement(PreparedStatement stmt, String ini, String end) throws SQLException {
        stmt.setString(1, ini);
        stmt.setString(2, end);
    }

    private InfoLinhaTrechoRelatorio setValuesToModel(ResultSet rs) throws SQLException {
        return new InfoLinhaTrechoRelatorio(rs.getString("data"),
                rs.getString("horarioSaida"),
                rs.getString("nomeLinha"),
                rs.getString("nomeTrecho"),
                rs.getInt("uso"),
                rs.getDouble("lucro"));
    }
}
