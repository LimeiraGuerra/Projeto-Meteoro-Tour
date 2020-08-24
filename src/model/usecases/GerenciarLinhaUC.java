package model.usecases;

import database.dao.LinhaDAO;
import model.entities.Linha;
import java.util.List;

public class GerenciarLinhaUC {

    LinhaDAO daoLinha = new LinhaDAO();
    long idFicticio = 3;

    public Linha createLinha(String nomeLinha){
        Linha linha = new Linha(idFicticio++ , nomeLinha);
        return linha;
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
    public Linha searchLinhaNome(String nome){
        return daoLinha.searchLinhaNome(nome);
    }

    public List<Linha> getListLinha() {
        return daoLinha.getListLinha();
    }


    public void updateLinha(Linha linha) {
        daoLinha.update(linha);
    }
}
