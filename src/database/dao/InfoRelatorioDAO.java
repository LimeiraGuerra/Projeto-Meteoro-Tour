package database.dao;

import database.utils.DAO;
import model.entities.InfoLinhaTrechoRelatorio;
import model.entities.Linha;
import model.entities.Passagem;
import model.entities.TrechoLinha;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoRelatorioDAO implements DAO<InfoLinhaTrechoRelatorio, String> {
    private static InfoRelatorioDAO instancia;

    private InfoRelatorioDAO(){

    }

    public static InfoRelatorioDAO getInstancia(){
        if (instancia == null){
            instancia = new InfoRelatorioDAO();
        }
        return instancia;
    }

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
        Map<String, Linha> formating = new HashMap<>();
        List<Passagem> resultPassagem = null;//PassagemDAO.getInstancia().selectByDateInterval(ini, end);
        List<InfoLinhaTrechoRelatorio> iltr = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        for(Passagem rp : resultPassagem){
            if (!formating.containsKey(rp.getDataViagemStr())){
                formating.put(rp.getDataViagemStr(), rp.getViagem().getLinha());
            }
        }
        for (String k : formating.keySet()){
            for (TrechoLinha tl : formating.get(k).getListTrechosLinha()){
                iltr.add(new InfoLinhaTrechoRelatorio(
                        k, df.format(tl.getHorarioSaida()),
                        formating.get(k).getNome(),
                        tl.getTrecho().getCidadeOrigem() + " - " + tl.getTrecho().getCidadeDestino(),
                        0, 0.0));
            }
        }
        return iltr;
    }
}
