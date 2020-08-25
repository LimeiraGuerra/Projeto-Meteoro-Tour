package model.usecases;

import database.utils.DAOCrud;
import model.entities.Administrador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUC {
    private DAOCrud<Administrador, String> daoAdministrador;

    public LoginUC(DAOCrud<Administrador, String> daoAdministrador) {
        this.daoAdministrador = daoAdministrador;
    }

    private String hashSha1(String senha) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(senha.getBytes());
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public Administrador getAdministrador(String senha){
        return daoAdministrador.selectById(hashSha1(senha));
    }
}
