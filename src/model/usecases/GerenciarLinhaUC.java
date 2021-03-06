package model.usecases;

import database.utils.DAOCrud;
import database.utils.DAOSelects;
import model.entities.Linha;
import model.entities.TrechoLinha;
import view.util.DataValidator;
import java.util.List;

public class GerenciarLinhaUC {

    private DAOCrud<Linha, String> daoLinha;
    private DAOSelects<TrechoLinha, Linha> daoTrechoLinha;

    public GerenciarLinhaUC(DAOCrud<Linha, String> daoLinha, DAOSelects<TrechoLinha, Linha> daoTrechoLinha) {
        this.daoLinha = daoLinha;
        this.daoTrechoLinha = daoTrechoLinha;
    }

    public void createLinha(String str){
        Linha linha = new Linha(validatorTrim(str));
        daoLinha.save(linha);
    }

    public void deleteLinha(Linha linha) {
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
        linha.setNome(validatorTrim(linha.getNome()));
        daoLinha.update(linha);
    }

    public boolean checkLinha(Linha linha) {
        return daoLinha.selectById(String.valueOf(linha.getId())) == null;
    }

    private String validatorTrim(String str){
        return DataValidator.txtInputVerifier(str);
    }
}
