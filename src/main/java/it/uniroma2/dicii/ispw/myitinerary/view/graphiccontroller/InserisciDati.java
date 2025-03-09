package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.controller.InserisciAnnuncioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class InserisciDati extends ControllerGrafico {


    private InserisciAnnuncioController controller;
    private Utente utente;
    private UtenteBean utenteBean;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cittàField;

    @FXML
    private TextField indirizzoField;

    @FXML
    private ChoiceBox<String> tipoChoiceBox;

    @FXML
    private TextField sitoWebField;

    @FXML
    private TextArea descrizioneArea;

    private List<byte[]> fotoList = new ArrayList<>();

    @Override
    public void inizializza(Object controller, Utente utente) {

        utenteBean = new UtenteBean(utente);

        if (controller == null){
            this.controller = new InserisciAnnuncioController(utenteBean);
        }

        if (controller instanceof InserisciAnnuncioController) {
            this.controller = (InserisciAnnuncioController) controller;
        }

        this.utente = utente;

        tipoChoiceBox.getItems().addAll("RISTORANTE", "ALLOGGIO");
        tipoChoiceBox.getSelectionModel().selectFirst();

    }

    public void onCaricaFotoClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Immagini", "*.jpg", "*.png", "*.jpeg")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fotoList.add(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Mostra un messaggio di errore
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText("Errore Caricamento");
                    alert.setContentText("Si è verificato un errore durante il caricamento delle immagini.");
                    alert.showAndWait();
                }
            }
        }
    }

    public void onInserisciClick(ActionEvent actionEvent) {

        try {
            // Altri campi del form
            String nome = nomeField.getText();
            String città = cittàField.getText();
            String indirizzo = indirizzoField.getText();
            String sitoWeb = sitoWebField.getText();
            String descrizione = descrizioneArea.getText();
            String tipo = tipoChoiceBox.getValue();

            // Recupera il codice fiscale dell'utente corrente
            String cfUtente = utenteBean.getCf();

            // Chiamata al metodo per inserire l'annuncio nel DB
            AnnuncioBean annuncioBean = new AnnuncioBean(nome, città, indirizzo, sitoWeb, descrizione, tipo, cfUtente);
            InserisciAnnuncioController annuncioController = new InserisciAnnuncioController(utenteBean);
            annuncioController.inserisciAnnuncio(annuncioBean);

        } catch (Exception e) {
            LoggerManager.logSevereException("Errore durante l'inserimento dell'annuncio", e);
        }



    }


    public void mieiAnnunciClick(ActionEvent actionEvent) {
    }

    public void homeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/proprietarioattività/inserisciDati.fxml", "Home Page", controller, utente);
    }
}
