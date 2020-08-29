package model.usecases;

import database.dao.FuncionarioDAO;
import model.entities.Funcionario;

import java.util.List;

public class GerenciarFuncionarioUC {
    private FuncionarioDAO daoFunc;

    public GerenciarFuncionarioUC(FuncionarioDAO daoFunc){
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
