package database.dao;

import database.utils.ConnectionFactory;
import database.utils.DAO;
import model.entities.TrechoLinha;
import model.entities.Viagem;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViagemDAO implements DAO<Viagem, String> {
    private List<String[]> keysCidadesTest;

    private void getKeysCidadesTest(){
        keysCidadesTest = new ArrayList<>();
        TrechoLinhaDAO trechoLinhaDAO = new TrechoLinhaDAO();
        List<TrechoLinha> tl = trechoLinhaDAO.selectAll();
        for (TrechoLinha t : tl){
            keysCidadesTest.add(new String[]{
                    t.getTrecho().getCidadeOrigem(), t.getTrecho().getCidadeDestino(), ""+t.getLinha().getId()});
        }
    }

    @Override
    public void save(Viagem model) {
        String sql = "INSERT INTO Viagem (data, cidadeOrigem, cidadeDestino, idLinha)" +
                "VALUES ("+this.setSqlKeysViagem(model)+");";
        try (Statement stmt = ConnectionFactory.createStatement()) {
            stmt.execute(sql);
            stmt.getGeneratedKeys();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String setSqlKeysViagem(Viagem model){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(model.getData()) + ", " +
                model.getCidadeOrigem() + ", " +
                model.getCidadeDestino() + ", " +
                model.getLinha().getId();
    }

    @Override
    public void update(Viagem model) {

    }

    @Override
    public void delete(Viagem model) {

    }

    @Override
    public Viagem selectById(String id) {
        return null;
    }

    @Override
    public List<Viagem> selectAll() {
        return null;
    }

    @Override
    public List<Viagem> selectAllByArg(String arg) {
        return null;
    }

    @Override
    public List<Viagem> selectByArgs(String... args) {
/*
        List<Viagem> viagens = new ArrayList<>();
        LinhaDAO daoLinha = LinhaDAO.getInstancia();

        getKeysCidadesTest();

        for (String[] tl : this.keysCidadesTest) {
            if (tl[0].equals(args[0])) {
                for (String[] tl2 : this.keysCidadesTest) {
                    if (tl2[1].equals(args[1]) && tl[2].equals(tl2[2])) {
                        Linha linha = daoLinha.selectById(tl[2]);
                        linha.addAllTrechosLinha(TrechoLinhaDAO.getInstancia().selectTrechosByLinha(linha, args[2]));
                        viagens.add(new Viagem(java.sql.Date.valueOf(args[2]), tl[0], tl2[1], linha));
                        break;
                    }
                }
            }
        }*/
        return null;
    }
}
