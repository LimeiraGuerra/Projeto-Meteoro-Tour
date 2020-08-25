package model.usecases;

import database.dao.LinhaDAO;
import model.entities.Linha;
import java.util.List;

public class GerenciarLinhaUC {

    LinhaDAO daoLinha = new LinhaDAO();
    long idFicticio = 3;

    public void createLinha(String nomeLinha){
        Linha linha = new Linha(idFicticio++ , nomeLinha);
        daoLinha.save(linha);

    }
    public void addLinha(Linha l) {
        /*Linha linha = daoLinha.searchLinha(l);
        if (linha == null){
            daoLinha.save(l);
        }
        else{
            linha.setNome(l.getNome());
            daoLinha.update(linha);
        }*/

    }
    public void deleteLinha(Linha linha) {
        daoLinha.delete(linha);
    }
    public Linha searchLinha(Linha l){
        return null;
    }
    public Linha searchLinhaNome(String nome){
        return null;
    }

    public List<Linha> getListLinha() {
        return null;
    }


    public void updateLinha(Linha linha) {
        daoLinha.update(linha);
    }
}
