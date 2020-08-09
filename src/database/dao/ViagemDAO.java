package database.dao;

import database.utils.DAO;
import model.entities.Linha;
import model.entities.Viagem;

import java.util.ArrayList;
import java.util.List;

public class ViagemDAO implements DAO<Viagem, String> {
    private List<String[]> keysCidadesTest = new ArrayList<>();

    private static ViagemDAO instancia;

    private ViagemDAO(){
        this.keysCidadesTest.add(new String[]{"Descalvado", "São Carlos", "0"});
        this.keysCidadesTest.add(new String[]{"São Carlos", "Ibaté", "0"});
        this.keysCidadesTest.add(new String[]{"Descalvado", "São Carlos", "1"});
        this.keysCidadesTest.add(new String[]{"São Carlos", "Araraquara", "1"});
        this.keysCidadesTest.add(new String[]{"Araraquara", "Ibaté", "1"});
    }

    @Override
    public void save(Viagem model) {

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
    public List<Viagem> selectByArgs(String... args) {
        /* todo */
        /* Retorno pra teste */
        List<Viagem> viagens = new ArrayList<>();
        LinhaDAO daoLinha = LinhaDAO.getInstancia();

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
        }
        return viagens;
    }

    public static ViagemDAO getInstancia(){
        if (instancia == null){
            instancia = new ViagemDAO();
        }
        return instancia;
    }

}
