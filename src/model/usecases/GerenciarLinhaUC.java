package model.usecases;

import database.dao.LinhaDAO;
import database.dao.TrechoLinhaDAO;
import model.entities.Linha;
import model.entities.TrechoLinha;
import java.util.List;

public class GerenciarLinhaUC {

    private LinhaDAO daoLinha;
    private TrechoLinhaDAO daoTrechoLinha;

    public GerenciarLinhaUC(LinhaDAO daoLinha, TrechoLinhaDAO daoTrechoLinha) {
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

    public void updateInativo(Linha l){
        daoLinha.update(l, 1);
    }

    public boolean checkDeleteLinha(Linha l){
        return daoLinha.checkLinhaPassagem(l.getId());
    }

}
