package model.usecases;

import database.dao.LinhaDAO;
import model.entities.Linha;
import java.util.List;

public class GerenciarLinhaUC {

    LinhaDAO daoLinha = new LinhaDAO();

    public void createLinha(String str) {
        Linha linha = new Linha(str);
        daoLinha.save(linha);
    }

    public void deleteLinha(Linha linha) {
        daoLinha.delete(linha);
    }

    public List<Linha> getListLinha() {
        return daoLinha.selectAll();
    }

    public void updateLinha(Linha linha) {
        daoLinha.update(linha);
    }
}
