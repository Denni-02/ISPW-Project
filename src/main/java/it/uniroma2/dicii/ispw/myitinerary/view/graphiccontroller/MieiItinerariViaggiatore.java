package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class MieiItinerariViaggiatore extends ControllerGrafico {

    private CreaItinerarioController creaItinerarioController;
    private Utente viaggiatore;
    private UtenteBean utenteBean;

    @FXML
    private ListView<ItinerarioBean> itinerariListView;

    @Override
    public void inizializza(Object controller, Utente utente) {

        if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        } else {
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

        viaggiatore = utente;
        utenteBean = new UtenteBean(viaggiatore);

        ObservableList<ItinerarioBean> itinerariList = FXCollections.observableArrayList(creaItinerarioController.getItinerariByUtente(utenteBean.getCf()));
        itinerariListView.setItems(itinerariList);
        itinerariListView.setCellFactory(param -> new ListCell<ItinerarioBean>() {
            @Override
            protected void updateItem(ItinerarioBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNomeCittà() == null) {
                    setText(null);
                } else {
                    setText(item.getNomeCittà() + " - " + item.getNumeroGiorni() + " giorni");
                }
            }
        });

        itinerariListView.setOnMouseClicked((MouseEvent event) -> {
            ItinerarioBean selectedItinerario = itinerariListView.getSelectionModel().getSelectedItem();
            creaItinerarioController.setItinerarioBean(selectedItinerario);
            if (selectedItinerario != null) {
                try {
                    ChangePage.changeScene(event, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/dettagliItinerarioViaggiatore.fxml", "Dettagli Itinerario", creaItinerarioController, viaggiatore);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController, viaggiatore);
    }

    public void indietroButton(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController, viaggiatore);
    }
}
