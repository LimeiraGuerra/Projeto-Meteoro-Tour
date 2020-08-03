package model.usecases;

import database.dao.LinhaDAO;
import model.entities.Linha;
import java.util.List;

public class GerenciarLinhaUC {

    LinhaDAO daoLinha = LinhaDAO.getInstancia();
    int idFicticio = 3;

    public void createLinha(String nomeLinha){
        Linha linha = new Linha(idFicticio++ , nomeLinha);
        daoLinha.save(linha);

    }
    public void addLinha(Linha l) {
        Linha linha = daoLinha.searchLinha(l);
        if (linha == null){
            daoLinha.save(l);
        }
        else{
            linha.setNome(l.getNome());
            daoLinha.update(linha);
        }

    }
    public void deleteLinha(Linha linha) {
        daoLinha.delete(linha);
    }

    public Linha searchLinha(Linha l){
        return  daoLinha.searchLinha(l);
    }

    public List<Linha> getListLinha() {
        return daoLinha.getListLinha();
    }

    public void updateLinha(String nomeNovo, Linha l) {
        Linha linha = searchLinha(l);
        linha.setNome(nomeNovo);
        daoLinha.update(linha);
    }


}
