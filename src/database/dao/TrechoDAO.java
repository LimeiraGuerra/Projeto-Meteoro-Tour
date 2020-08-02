package database.dao;

import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;

import java.sql.Time;
import java.util.*;

public class TrechoDAO implements DAO<Trecho, String> {
    private List<Trecho> trechos = new ArrayList<>();
    private List<int[]> trechosLinhaScuff = new ArrayList<>();
    private List<AssentoTrechoLinha> assentosVendScuff = new ArrayList<>();

    public TrechoDAO(){
        trechos.add(new Trecho("Descalvado", "São Carlos", 40.0, 30.0, 10.0, 1.0, 0.2));
        trechos.add(new Trecho("São Carlos", "Ibaté", 15.0, 5.0, 5.0, 0.5, 0.2));
        trechos.add(new Trecho("São Carlos", "Araraquara", 30.0, 20.0, 7.0, 0.8, 0.2));
        trechos.add(new Trecho("Araraquara", "Ibaté", 20.0, 15.0, 8.0, 1.0, 0.2));

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

    @Override
    public void save(Trecho model) {

    }

    @Override
    public void update(Trecho model) {

    }

    @Override
    public void delete(Trecho model) {

    }

    @Override
    public Trecho selectById(String id) {
        return null;
    }

    @Override
    public List<Trecho> selectByArgs(String... args) {
        return null;
    }

    public List<TrechoLinha> selectTrechosByLinha(Linha linha, String data){
        List<TrechoLinha> trechosLinha = new ArrayList<>();
        int cont = 0;
        for(int[] tl : trechosLinhaScuff){
            if(tl[0] == linha.getId()){
                TrechoLinha tlaux = new TrechoLinha(cont, Time.valueOf("12:00:00"), trechos.get(tl[1]), linha);
                if(assentosVendScuff.get(cont).getData().compareTo(java.sql.Date.valueOf(data)) == 0) {
                    tlaux.setAssentoTrechoLinha(assentosVendScuff.get(cont));
                }
                cont++;
                trechosLinha.add(tlaux);
            }
        }
        return trechosLinha;
    }
}
