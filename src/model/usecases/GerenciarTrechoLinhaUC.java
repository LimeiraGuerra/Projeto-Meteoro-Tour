package model.usecases;


import database.dao.TrechoLinhaDAO;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import java.util.Date;
import java.util.List;

public class GerenciarTrechoLinhaUC {
    private TrechoLinhaDAO daoTrechoLinha = TrechoLinhaDAO.getInstancia();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC();
    private GerenciarLinhaUC ucLinha = new GerenciarLinhaUC();

    public void createTrechoLinha(Linha linha, Trecho trecho, Date horario, int ordem){
        TrechoLinha trechoL = new TrechoLinha(ordem, horario, trecho, linha);
        daoTrechoLinha.save(trechoL);
        ucLinha.updateLinha(linha);
        ucTrecho.updateTrecho(trecho);
    }

    public TrechoLinha searchTrechoLinha() {
        return searchTrechoLinha();
    }

    public TrechoLinha searchTrechoLinha(TrechoLinha tL){
        return daoTrechoLinha.searchTrechoLinha(tL);
    }

    public void deleteTrechoLinha(TrechoLinha trechoLinha) {
        Trecho trecho = trechoLinha.getTrecho();
        Linha linha = trechoLinha.getLinha();
        trecho.deleteTrechoLinha(trechoLinha);
        linha.deleteTrechoLinha(trechoLinha);
        daoTrechoLinha.delete(trechoLinha);
        ucLinha.updateLinha(linha);
        ucTrecho.updateTrecho(trecho);

    }

    public List<TrechoLinha> getListTrechoLinha() {
        return daoTrechoLinha.getListTrechoLinha();
    }

}
