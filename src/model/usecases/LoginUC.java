package model.usecases;

import database.dao.AdministradorDAO;
import model.entities.Administrador;

public class LoginUC {

    private AdministradorDAO daoAdministrador = new AdministradorDAO();

    public boolean isCheckPassword(String senha){
        Administrador adm = getAdministrador();
        return adm.getSenha().equals(senha);
    }

    public Administrador getAdministrador(){
        return daoAdministrador.getAdministrador();
    }

}
