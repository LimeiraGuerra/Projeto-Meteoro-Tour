package model.usecases;
import database.utils.DAOCrud;
import model.entities.Linha;
import model.entities.TrechoLinha;

public class GerenciarTrechoLinhaUC {

    private DAOCrud<TrechoLinha, String> daoTrechoLinha;

    public GerenciarTrechoLinhaUC(DAOCrud<TrechoLinha, String> daoTrechoLinha) {
        this.daoTrechoLinha = daoTrechoLinha;
    }

    public void saveTrechoLinha(TrechoLinha trechoLinha){
        daoTrechoLinha.save(trechoLinha);
    }

    public void deleteTrechoLinha(TrechoLinha trechoLinha) {
        Linha linha = trechoLinha.getLinha();
        linha.deleteTrechoLinha(trechoLinha);
        daoTrechoLinha.delete(trechoLinha);
    }

    public void updateTrechoLinha(TrechoLinha trechoLinha){
        daoTrechoLinha.update(trechoLinha);
    }

}
