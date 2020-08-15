package database.dao;

import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
import model.entities.Linha;
import model.entities.TrechoLinha;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrechoLinhaDAO implements DAO<TrechoLinha, String> {

    private static TrechoLinhaDAO instancia;
    private List<TrechoLinha> trechosLinhas = new ArrayList<>();
    private List<int[]> trechosLinhaScuff = new ArrayList<>();
    private List<AssentoTrechoLinha> assentosVendScuff = new ArrayList<>();

    private TrechoLinhaDAO() {
        trechosLinhas.add(new TrechoLinha(0, Time.valueOf("12:00:00"), TrechoDAO.getInstancia().selectById("0"), LinhaDAO.getInstancia().selectById("1") ));
        trechosLinhas.add(new TrechoLinha(1, Time.valueOf("12:30:00"), TrechoDAO.getInstancia().selectById("2"), LinhaDAO.getInstancia().selectById("1") ));

        trechosLinhas.add(new TrechoLinha(0, Time.valueOf("12:00:00"), TrechoDAO.getInstancia().selectById("0"), LinhaDAO.getInstancia().selectById("2") ));
        trechosLinhas.add(new TrechoLinha(1, Time.valueOf("12:30:00"), TrechoDAO.getInstancia().selectById("1"), LinhaDAO.getInstancia().selectById("2") ));
        trechosLinhas.add(new TrechoLinha(2, Time.valueOf("12:50:00"), TrechoDAO.getInstancia().selectById("3"), LinhaDAO.getInstancia().selectById("2") ));

        trechosLinhaScuff.add(new int[]{1, 0});
        trechosLinhaScuff.add(new int[]{1, 1});
        trechosLinhaScuff.add(new int[]{2, 0});
        trechosLinhaScuff.add(new int[]{2, 2});
        trechosLinhaScuff.add(new int[]{2, 3});

        assentosVendScuff.add(new AssentoTrechoLinha(java.sql.Date.valueOf("2020-10-11"), Arrays.asList(new String[]{"04", "05", "23", "03"})));
        assentosVendScuff.add(new AssentoTrechoLinha(java.sql.Date.valueOf("2020-10-10"), Arrays.asList(new String[]{"04", "32", "21", "42", "39"})));
        assentosVendScuff.add(new AssentoTrechoLinha(java.sql.Date.valueOf("2020-10-10"), Arrays.asList(new String[]{"32", "29", "08"})));
        assentosVendScuff.add(new AssentoTrechoLinha(java.sql.Date.valueOf("2020-10-10"), Arrays.asList(new String[]{"23", "03"})));
        assentosVendScuff.add(new AssentoTrechoLinha(java.sql.Date.valueOf("2020-10-11"), Arrays.asList(new String[]{"44", "10", "18"})));
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
    public List<TrechoLinha> selectByArgs(String... args) {
        return null;
    }

    public TrechoLinha searchTrechoLinha(TrechoLinha trechoLinha){
        return trechosLinhas.contains(trechoLinha) ? trechoLinha : null;
    }
    public List<TrechoLinha> getListTrechoLinha(){
        return trechosLinhas;
    }

    public static TrechoLinhaDAO getInstancia(){
        if (instancia == null){
            instancia = new TrechoLinhaDAO();
        }
        return  instancia;
    }
}
