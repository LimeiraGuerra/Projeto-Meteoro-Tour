package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Passagem;
import model.usecases.ConsultarPassagensVendidasUC;
import view.loader.ModalAvisoLoader;

import java.util.ArrayList;
import java.util.List;

public class PassagemController {
    @FXML TextField txtSearchBar;
    @FXML TableView<Passagem> tablePassagens;
    @FXML TableColumn<Passagem, String> colNumero, colData, colHorarioSaida,
            colLinha, colOrigem, colDestino, colValor;
    @FXML Button btnReemicao, btnDevolucao, btnReagendamento;

    private Passagem passagemReagendamento;
    private List<Passagem> passagensEncontradas;

    private ConsultarPassagensVendidasUC ucPassagensVendidas;

    public PassagemController() {
        this.ucPassagensVendidas = new ConsultarPassagensVendidasUC();
    }

    @FXML
    private void initialize() {
        bindTableColumnsToSource();
    }

    private void bindTableColumnsToSource() {
        /* todo resolver o bind pq tem q acessar 3 classes para os dados, o q estaria em banco
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numPassagem"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataViagem"));
        colHorarioSaida.setCellValueFactory(new PropertyValueFactory<>("horarioSaida"));
        colLinha.setCellValueFactory(new PropertyValueFactory<>(""));*/
    }

    public void searchForViagens(ActionEvent actionEvent) {
        boolean choice = verifyRadioChoices();
        String searchKey = verifyTextField(choice);
        if (searchKey != null){
            if (choice) searchByNumber(searchKey);
            else {}//todo pesquisa cpf
            unlockOptions();
        }
        else {

        }
        //exibResultsToTableView();
        //System.out.println(this.passagensEncontradas.get(0).getNumPassagem());
    }

    private void unlockOptions() {
        btnDevolucao.setDisable(false);
        btnReagendamento.setDisable(false);
        btnReemicao.setDisable(false);
    }

    private void searchByNumber(String number) {
        Passagem result = ucPassagensVendidas.searchViagensByNumber(number);
        if (result != null) {
            this.passagensEncontradas = new ArrayList<>();
            this.passagensEncontradas.add(result);
        }
        else {
            new ModalAvisoLoader("mensagemTeste");
        }
    }

    private boolean verifyRadioChoices() {
        return true;// todo
    }

    private String verifyTextField(boolean choice){
        String searchKey = txtSearchBar.getText();
        if (choice){
            // todo if filtro numero
        }
        else {
            // todo if filtro cpf
        }
        return searchKey;
    }
}
