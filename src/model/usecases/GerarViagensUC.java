package model.usecases;

import database.dao.AssentosTrechoLinhaDAO;
import database.utils.DAO;
import model.entities.AssentoTrechoLinha;
import model.entities.Linha;
import model.entities.TrechoLinha;
import model.entities.Viagem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GerarViagensUC {
    private DAO<Viagem, String> daoViagem;
    private DAO<Linha, String> daoLinha;
    private DAO<TrechoLinha, String> daoTrechoLinha;
    private AssentosTrechoLinhaDAO daoAssentos;

    public GerarViagensUC(DAO<Viagem, String> daoViagem,
                          DAO<Linha, String> daoLinha,
                          DAO<TrechoLinha, String> daoTrechoLinha,
                          DAO<AssentoTrechoLinha, String> daoAssentos){
        this.daoViagem = daoViagem;
        this.daoLinha = daoLinha;
        this.daoTrechoLinha = daoTrechoLinha;
        this.daoAssentos = (AssentosTrechoLinhaDAO) daoAssentos;
    }

    public List<Viagem> searchForViagens(Date data, String cidadeOrigem, String cidadeDestino){
        List<Linha> linhas = this.getLinhasByCidades(cidadeOrigem, cidadeDestino);
        List<Viagem> viagens = new ArrayList<>();
        for (Linha l : linhas){
            Viagem v = new Viagem(data, cidadeOrigem, cidadeDestino, l);
            this.verifyDisponibility(v);
            viagens.add(v);
        }
        return viagens;
    }

    private List<Linha> getLinhasByCidades(String cidadeOrigem, String cidadeDestino){
        List<Linha> linhas = this.daoLinha.selectByArgs(cidadeOrigem, cidadeDestino);
        for (Linha l : linhas)
            l.addAllTrechosLinha(this.daoTrechoLinha.selectAllByArg(String.valueOf(l.getId())));
        return linhas;
    }

    private void verifyDisponibility(Viagem viagem){
        viagem.setAssentosVendidosViagem(this.daoAssentos.selectAllAssentosVendidos(viagem,
                                            this.getTrechoLinhaIdAndSetValues(viagem)));
    }

    private String getTrechoLinhaIdAndSetValues(Viagem viagem){
        StringBuilder st = new StringBuilder();
        Iterator<TrechoLinha> itTl = viagem.getTrechosLinha();
        double valueViagem = 0.0;
        double valueSeguroViagem = 0.0;
        while (true) {
            TrechoLinha tl = itTl.next();
            st.append(tl.getId());
            valueViagem += tl.getTrecho().getValorTotal();
            valueSeguroViagem += tl.getTrecho().getValorSeguro();
            if (itTl.hasNext()) st.append(", ");
            else break;
        }
        viagem.setValuesOfViagem(valueViagem, valueSeguroViagem);
        return st.toString();
    }
}