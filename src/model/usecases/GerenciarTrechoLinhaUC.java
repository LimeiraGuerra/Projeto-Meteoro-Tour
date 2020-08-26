package model.usecases;
import database.utils.DAOCrud;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import java.util.Date;

public class GerenciarTrechoLinhaUC {

    private DAOCrud<TrechoLinha, String> daoTrechoLinha;

    public GerenciarTrechoLinhaUC(DAOCrud<TrechoLinha, String> daoTrechoLinha) {
        this.daoTrechoLinha = daoTrechoLinha;
    }

    public void saveTrechoLinha(TrechoLinha trechoLinha){
        daoTrechoLinha.save(trechoLinha);
    }

    public void createTrechoLinha(Linha linha, Trecho trecho, Date horario, int ordem) {
        TrechoLinha trechoL = new TrechoLinha(ordem, horario, trecho, linha);
        daoTrechoLinha.save(trechoL);
    }

    public void deleteTrechoLinha(TrechoLinha trechoLinha) {
        Linha linha = trechoLinha.getLinha();
        linha.deleteTrechoLinha(trechoLinha);
        daoTrechoLinha.delete(trechoLinha);
    }

    public void updateTrechoLinha(TrechoLinha trechoLinha){
        TrechoLinha tLinha = daoTrechoLinha.selectById(trechoLinha.getId()+"");
        tLinha.setHorarioSaida(trechoLinha.getHorarioSaida());
        daoTrechoLinha.update(tLinha);
    }

}
