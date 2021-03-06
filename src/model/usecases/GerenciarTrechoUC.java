package model.usecases;
import database.utils.DAOCrud;
import model.entities.Trecho;
import model.entities.TrechoLinha;
import view.util.DataValidator;
import java.util.List;

public class GerenciarTrechoUC {

    private DAOCrud<Trecho, String> daoTrecho;
    private DAOCrud<TrechoLinha, String> trechoLinhaDAO;

    public GerenciarTrechoUC(DAOCrud<Trecho, String> daoTrecho, DAOCrud<TrechoLinha, String> trechoLinhaDAO) {
        this.daoTrecho = daoTrecho;
        this.trechoLinhaDAO = trechoLinhaDAO;
    }

    public Trecho searchForOrigemDestino(String origem, String destino){
        for (Trecho trecho : getListTrechos()){
            if (iscityOrigin(origem, trecho) && iscityDestiny(destino, trecho)){
                return trecho;
            }
        }
        return  null;
    }

    public Trecho createTrecho(String origem, String destino, double km, int tempo, double valorP, double valorE, double valorS) {
        origem = DataValidator.txtInputVerifier(origem);
        destino = DataValidator.txtInputVerifier(destino);
        Trecho t = new Trecho(origem, destino, km, tempo, valorP, valorE, valorS);
        this.addTrecho(t);
        return t;
    }
    public void atualizaTrecho(double km, int tempo, double valorP, double valorE, double valorS, Trecho trecho){
        trecho.setTaxaEmbarque(valorE);
        trecho.setQuilometragem(km);
        trecho.setTempoDuracao(tempo);
        trecho.setValorSeguro(valorS);
        trecho.setValorPassagem(valorP);
        updateTrecho(trecho);

    }
    public void  deleteTrecho(Trecho t){
        daoTrecho.delete(t);
    }

    public List<Trecho> getListTrechos() {
        return daoTrecho.selectAll();
    }

    public void addTrecho(Trecho trecho){
        daoTrecho.save(trecho);
    }

    private boolean iscityOrigin(String cidade, Trecho trecho){
        return trecho.getCidadeOrigem().equals(cidade);
    }

    private boolean iscityDestiny(String cidade, Trecho trecho){
        return trecho.getCidadeDestino().equals(cidade);
    }

    public void updateTrecho(Trecho trecho) {
        daoTrecho.update(trecho);
    }

    public boolean ContainsTrechoLinha(Trecho trecho){
        return trechoLinhaDAO.selectAllByKeyword(String.valueOf(trecho.getId())).size() == 0;
    }
}
