package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAOCrud;
import database.utils.DAOSelects;
import model.entities.Linha;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LinhaDAO implements DAOCrud<Linha, String>, DAOSelects<Linha, String> {

    @Override
    public void save(Linha model) {
    }

    @Override
    public void update(Linha model) {
    }

    @Override
    public void delete(Linha model) {
    }

    @Override
    public Linha selectById(String id) {
        return null;
    }

    @Override
    public List<Linha> selectAll() {
        return null;
    }

    @Override
    public List<Linha> selectAllByKeyword(String key) {
        return null;
    }

    @Override
    public List<Linha> selectByParent(String parent) {
        throw new NotImplementedException();
    }

    @Override
    public List<Linha> selectByInterval(String ini, String end) {
        String sql = "SELECT * FROM vLinhaByCidades WHERE idLinha IN (\n"
                + "SELECT tl.idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeOrigem = ? AND idLinha IN (\n"
                + "SELECT idLinha FROM trechoLinha tl JOIN trecho t ON t.id = tl.idTrecho\n"
                + "WHERE t.cidadeDestino = ?));";
        List<Linha> linhas = null;
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)) {
            setKeysCidades(stmt, ini, end);
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
