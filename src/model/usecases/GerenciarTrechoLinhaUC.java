package model.usecases;
import database.utils.DAOCrud;
import model.entities.Linha;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import java.sql.SQLException;
import java.util.Date;

public class GerenciarTrechoLinhaUC {

    private DAOCrud<TrechoLinha, String> daoTrechoLinha;

    public GerenciarTrechoLinhaUC(DAOCrud<TrechoLinha, String> daoTrechoLinha) {
        this.daoTrechoLinha = daoTrechoLinha;
    }

    public void createTrechoLinha(Linha linha, Trecho trecho, Date horario, int ordem) throws SQLException {
        TrechoLinha trechoL = new TrechoLinha(ordem, horario,0, trecho, linha);
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
