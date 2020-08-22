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
    private List<Linha> linhas = new ArrayList<>();

    @Override
    public void save(Linha model) {
        linhas.add(model);
    }

    @Override
    public void update(Linha model) {
        int index = linhas.indexOf(model);
        linhas.remove(index);
        linhas.add(index, model);
    }

    @Override
    public void delete(Linha model) {
        linhas.remove(model);
    }

    @Override
    public Linha selectById(String id) {
        Long num = Long.parseLong(id);
        for (Linha l : linhas){
            if (l.equals(num)) return l;
        }
        return null;
    }

    @Override
    public List<Linha> selectAll() {
        return null;
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

    public Linha searchLinha(Linha linha){
        return linhas.contains(linha) ? linha : null;
    }

    //Melhor verificar se existe na tabela da view do que fazer um metodo de dao
    public Linha searchLinhaNome(String nome){
        for (Linha linha: linhas) {
            if(linha.getNome().equals(nome)){
                return linha;
            }
        }
        return null;
    }

    public List<Linha> getListLinha(){
        return linhas;
    }
}
