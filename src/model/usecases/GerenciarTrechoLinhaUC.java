package model.usecases;


import database.dao.TrechoLinhaDAO;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import java.util.Date;
import java.util.List;

public class GerenciarTrechoLinhaUC {
    private TrechoLinhaDAO daoTrechoLinha = new TrechoLinhaDAO();
    private GerenciarTrechoUC ucTrecho = new GerenciarTrechoUC();
    private GerenciarLinhaUC ucLinha = new GerenciarLinhaUC();

    public void createTrechoLinha(Linha linha, Trecho trecho, Date horario, int ordem){
        TrechoLinha trechoL = new TrechoLinha(ordem, horario,0, trecho, linha);
        daoTrechoLinha.save(trechoL);
        ucLinha.updateLinha(linha);
        ucTrecho.updateTrecho(trecho);
    }

    public void deleteTrechoLinha(TrechoLinha trechoLinha) {
        Trecho trecho = trechoLinha.getTrecho();
        Linha linha = trechoLinha.getLinha();
        linha.deleteTrechoLinha(trechoLinha);
        daoTrechoLinha.delete(trechoLinha);
        ucLinha.updateLinha(linha);
        ucTrecho.updateTrecho(trecho);

    }
    public List<TrechoLinha> getListTrechosLinhas(){
        return daoTrechoLinha.selectAll();
    }

}
