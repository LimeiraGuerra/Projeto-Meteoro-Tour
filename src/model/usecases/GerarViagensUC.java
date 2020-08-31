package model.usecases;

import database.utils.DAOSelects;
import model.entities.Linha;
import model.entities.TrechoLinha;
import model.entities.Viagem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GerarViagensUC {

    private DAOSelects<Linha, String> daoLinha;
    private DAOSelects<TrechoLinha, Linha> daoTrechoLinha;
    private DAOSelects<String, Viagem> daoAssentos;

    public GerarViagensUC(DAOSelects<Linha, String> daoLinha,
                          DAOSelects<TrechoLinha, Linha> daoTrechoLinha,
                          DAOSelects<String, Viagem> daoAssentos){
        this.daoLinha = daoLinha;
        this.daoTrechoLinha = daoTrechoLinha;
        this.daoAssentos = daoAssentos;
    }

    public List<Viagem> searchForViagens(Date data, String cidadeOrigem, String cidadeDestino){
        List<Linha> linhas = this.getLinhasByCidades(cidadeOrigem, cidadeDestino);
        List<Viagem> viagens = new ArrayList<>();
        for (Linha l : linhas){
            Viagem v = new Viagem(data, cidadeOrigem, cidadeDestino, l);
            if (v.getData() != null) {
                this.verifyDisponibility(v);
                viagens.add(v);
            }
        }
        return viagens;
    }

    private List<Linha> getLinhasByCidades(String cidadeOrigem, String cidadeDestino){
        List<Linha> linhas = this.daoLinha.selectByInterval(cidadeOrigem, cidadeDestino);
        for (Linha l : linhas)
            l.addAllTrechosLinha(this.daoTrechoLinha.selectByParent(l));
        return linhas;
    }

    private void verifyDisponibility(Viagem viagem){
        viagem.setAssentosVendidosViagem(this.daoAssentos.selectByParent(viagem));
    }
}