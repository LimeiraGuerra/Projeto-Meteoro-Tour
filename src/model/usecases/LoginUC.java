package model.usecases;

import database.dao.AdministradorDAO;
import model.entities.Administrador;

public class LoginUC {

    private AdministradorDAO daoAdministrador = AdministradorDAO.getInstancia();
    private  Administrador adm;

    public boolean isCheckPassword(String senha){
        adm = getAdministrador();
        return adm.getSenha().equals(senha);
    }

    public Administrador getAdministrador(){
        return daoAdministrador.getAdministrador();
    }
}
