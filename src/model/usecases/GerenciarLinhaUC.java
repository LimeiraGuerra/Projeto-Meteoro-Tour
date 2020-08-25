package model.usecases;

import database.dao.LinhaDAO;
import database.dao.TrechoLinhaDAO;
import model.entities.Linha;
import model.entities.TrechoLinha;
import java.util.List;

public class GerenciarLinhaUC {

    LinhaDAO daoLinha = new LinhaDAO();
    TrechoLinhaDAO daoTrechoLinha = new TrechoLinhaDAO();

    public void createLinha(String str) {
        Linha linha = new Linha(str);
        daoLinha.save(linha);
    }

    public void deleteLinha(Linha linha) {
        daoLinha.delete(linha);
    }

    public List<Linha> getListLinha() {
        List<Linha> linhas = daoLinha.selectAll();
        for (Linha l : linhas) {
            List<TrechoLinha> trechosLinhas = daoTrechoLinha.selectAllByArg(l.getId()+"");
            l.addAllTrechosLinha(trechosLinhas);
            l.setQuilometragem();
            l.setValorTotalLinha();
    }
        return linhas;
    }


    public void updateLinha(Linha linha) {
        daoLinha.update(linha);
    }


}
