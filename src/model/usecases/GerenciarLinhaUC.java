package model.usecases;

import database.dao.TrechoLinhaDAO;
import database.utils.DAOCrud;
import model.entities.Linha;
import model.entities.TrechoLinha;
import java.util.List;

public class GerenciarLinhaUC {

    private DAOCrud<Linha, String> daoLinha;
    private TrechoLinhaDAO daoTrechoLinha;

    public GerenciarLinhaUC(DAOCrud<Linha, String> daoLinha, TrechoLinhaDAO daoTrechoLinha) {
        this.daoLinha = daoLinha;
        this.daoTrechoLinha = daoTrechoLinha;
    }

    public void createLinha(String str){
        Linha linha = new Linha(str);
        daoLinha.save(linha);
    }

    public void deleteLinha(Linha linha) {
        for (TrechoLinha tl : linha.getListTrechoLinha()){
            daoTrechoLinha.delete(tl);
        }
        daoLinha.delete(linha);

    }

    public List<Linha> getListLinha(){
            List<Linha> linhas = daoLinha.selectAll();
            for (Linha l : linhas) {
                List<TrechoLinha> trechosLinhas = daoTrechoLinha.selectByParent(l);
                l.addAllTrechosLinha(trechosLinhas);
                l.setQuilometragem();
                l.setValorTotalLinha();
            }
            return linhas;
        }

    public void updateLinha(Linha linha) {
        daoLinha.update(linha);
    }

    public boolean checkLinha(Linha linha) {
        return daoLinha.selectById(linha.getId() + "") == null;
    }
}
