package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.controller.VisualizzaItinerariController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class MieiItinerariViaggiatore extends ControllerGrafico {

    //private CreaItinerarioController creaItinerarioController;
    private VisualizzaItinerariController visualizzaItinerariController;
    private Utente viaggiatore;
    private UtenteBean utenteBean;

    @FXML
    private ListView<ItinerarioBean> itinerariListView;

    @Override
    public void inizializza(Object controller, Utente utente) {

        /*if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        } else {
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

         */

        if (controller instanceof VisualizzaItinerariController) {
            this.visualizzaItinerariController = (VisualizzaItinerariController) controller;
        } else {
            this.visualizzaItinerariController = new VisualizzaItinerariController(utenteBean);
        }

        viaggiatore = utente;
        utenteBean = new UtenteBean(viaggiatore);

        //ObservableList<ItinerarioBean> itinerariList = FXCollections.observableArrayList(creaItinerarioController.getItinerariByUtente(utenteBean.getCf()));
        ObservableList<ItinerarioBean> itinerariList = FXCollections.observableArrayList(visualizzaItinerariController.getItinerariByUtente(utenteBean.getCf()));
        itinerariListView.setItems(itinerariList);
        itinerariListView.setCellFactory(param -> new ListCell<ItinerarioBean>() {
            @Override
            protected void updateItem(ItinerarioBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNomeCittà() == null) {
                    setText(null);
                } else {
                    String dateFormatted = "Data non disponibile";
                    if (item.getDataCreazione() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        dateFormatted = sdf.format(item.getDataCreazione());
                    }
                    setText(item.getNomeCittà() + " - " + item.getNumeroGiorni() + " giorno/i - Creato il: " + dateFormatted);
                    setFont(new Font("Arial", 18)); // Imposta il font e la dimensione del testo
                }
            }
        });

    }


    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", visualizzaItinerariController, viaggiatore);
    }

    public void indietroButton(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", visualizzaItinerariController, viaggiatore);
    }

    public void onCancellaItinerarioClick(ActionEvent actionEvent) {
        ItinerarioBean selectedItinerario = itinerariListView.getSelectionModel().getSelectedItem();
        if (selectedItinerario != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Cancellazione");
            alert.setHeaderText("Cancellazione dell'Itinerario");
            alert.setContentText("Sei sicuro di voler cancellare l'itinerario selezionato?");

            ButtonType buttonTypeCancella = new ButtonType("Cancella");
            ButtonType buttonTypeAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeCancella, buttonTypeAnnulla);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeCancella) {
                //creaItinerarioController.cancellaItinerario(selectedItinerario);
                visualizzaItinerariController.cancellaItinerario(selectedItinerario);
                itinerariListView.getItems().remove(selectedItinerario);
                System.out.println("Itinerario cancellato con successo.");
                showAlert("Cancellazione", "Itinerario cancellato con successo.");
            } else {
                System.out.println("Cancellazione annullata.");
            }
        } else {
            System.out.println("Seleziona un itinerario prima di cancellare.");
            showAlert("Errore", "Seleziona un itinerario prima di cancellare.");

        }
    }

    public void onVediDettagliClick(ActionEvent actionEvent) {
        ItinerarioBean selectedItinerario = itinerariListView.getSelectionModel().getSelectedItem();
        //creaItinerarioController.setItinerarioBean(selectedItinerario);
        visualizzaItinerariController.setItinerarioBean(selectedItinerario);
        if (selectedItinerario != null) {
            int itinerarioId = selectedItinerario.getId();
            System.out.println("ID Itinerario selezionato: " + itinerarioId);  // Aggiunto log per ID selezionato
            //creaItinerarioController.setItinerarioBean(selectedItinerario);
            visualizzaItinerariController.setItinerarioBean(selectedItinerario);
            try {
                //ChangePage.changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/dettagliItinerarioViaggiatore.fxml", "Dettagli Itinerario", creaItinerarioController, viaggiatore);
                ChangePage.changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/dettagliItinerarioViaggiatore.fxml", "Dettagli Itinerario", visualizzaItinerariController, viaggiatore);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Seleziona un itinerario prima di vedere i dettagli.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
