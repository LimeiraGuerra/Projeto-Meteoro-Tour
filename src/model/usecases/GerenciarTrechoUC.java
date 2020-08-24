package model.usecases;
import database.dao.TrechoDAO;
import model.entities.Trecho;
import java.util.List;

public class GerenciarTrechoUC {

    private TrechoDAO daoTrecho = new TrechoDAO();

    public Trecho searchForOrigemDestino(String origem, String destino){
        for (Trecho trecho : getListTrechos()){
            if (iscityOrigin(origem, trecho) && iscityDestiny(destino, trecho)){
                return  trecho;
            }
        }
        return  null;
    }

    public  Trecho createTrecho(String origem, String destino, double km, int tempo, double valorP, double valorE, double valorS){
        Trecho t = new Trecho(origem, destino, km, tempo, valorP, valorE, valorS);
        this.addTrecho(t);
        return t;
    }
    public void atualizaTrecho(double km, int tempo, double valorP, double valorE, double valorS, Trecho trecho){
        System.out.println(trecho.toString() + "\n" + trecho.getQuilometragem());
        trecho.setTaxaEmbarque(valorE);
        trecho.setQuilometragem(km);
        trecho.setTempoDuracao(tempo);
        trecho.setValorSeguro(valorS);
        trecho.setValorPassagem(valorP);
        System.out.println("Depois");
        System.out.println(trecho.toString() + "\n" + trecho.getQuilometragem());
        updateTrecho(trecho);

    }
    public void  deleteTrecho(Trecho t){
        daoTrecho.delete(t);
    }

    public List<Trecho> getListTrechos() {
       return  daoTrecho.selectAll();
    }

    public void addTrecho(Trecho trecho) {
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
}
