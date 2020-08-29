package model.usecases;

import database.utils.DAOSelects;
import model.entities.Linha;
import model.entities.TrechoLinha;

import java.util.List;

public class AutoCompleteUC {

    private DAOSelects<TrechoLinha, Linha> daoTrechoLinha;
    private DAOSelects<Linha, String> daoLinha;

    public AutoCompleteUC(DAOSelects<TrechoLinha, Linha> daoTrechoLinha, DAOSelects<Linha, String> daoLinha) {
        this.daoTrechoLinha = daoTrechoLinha;
        this.daoLinha = daoLinha;
    }

    public List<String> getLineNames(){
        return daoLinha.selectStringForAutoComplete();
    }

    public List<String> getCityNames(){
        return daoTrechoLinha.selectStringForAutoComplete();
    }
}
