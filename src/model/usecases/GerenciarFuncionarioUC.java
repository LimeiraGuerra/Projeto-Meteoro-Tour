package model.usecases;

import database.utils.DAOCrud;
import model.entities.Funcionario;

import java.util.List;

public class GerenciarFuncionarioUC {
    private DAOCrud<Funcionario, String> daoFunc;

    public GerenciarFuncionarioUC(DAOCrud<Funcionario, String> daoFunc){
        this.daoFunc = daoFunc;
    }

    public List<Funcionario> getListFunc(){
        return daoFunc.selectAll();
    }

    public void saveFunc(Funcionario funcionario){
        daoFunc.save(funcionario);
    }

    public void deleteFunc(Funcionario funcionario){
        daoFunc.delete(funcionario);
    }

    public void updateFunc(Funcionario funcionario){
        daoFunc.update(funcionario);
    }

}
