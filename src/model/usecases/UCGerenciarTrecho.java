package model.usecases;
import model.entities.Trecho;
import java.util.ArrayList;
import java.util.List;

public class UCGerenciarTrecho {
    private static UCGerenciarTrecho instancia;
    private List<Trecho> trechos = new ArrayList<>();


    public Trecho buscarTrechoOrigemDestino(String origem, String destino){
        for (Trecho trecho: trechos) {
            if (trecho.getCidadeOrigem().equals(origem) && trecho.getCidadeDestino().equals(destino)){
                return trecho;
            }
        }
        return null;
    }

    public Trecho buscarTrecho(Trecho trecho){
        for (Trecho t: trechos) {
            if (trecho.equals(t)){
                return trecho;
            }
        }
        return null;
    }

    public  Trecho criarTrecho(String origem, String destino, double km, double tempo, double valorP, double valorE, double valorS){
        Trecho t = new Trecho(origem, destino, km, tempo, valorP, valorE, valorS);
        trechos.add(t);
        return t;
    }
    public void editarTrecho(double km, double tempo, double valorP, double valorE, double valorS, Trecho t){
        Trecho trecho = buscarTrecho(t);
        trecho.setTaxaEmbarque(valorE);
        trecho.setQuilometragem(km);
        trecho.setTempoDuracao(tempo);
        trecho.setValorSeguro(valorS);
        trecho.setValorPassagem(valorP);
    }
    public void  removeTrecho(Trecho t){
        trechos.remove(t);
    }

    public List<Trecho> getListTrechos() {
        return trechos;
    }

    public void addTrecho(Trecho trecho) {
        trechos.add(trecho);
    }

    public static UCGerenciarTrecho getInstancia(){
        if (instancia == null){
            instancia = new UCGerenciarTrecho();
        }
        return instancia;
    }
    private UCGerenciarTrecho() {
    }
}
