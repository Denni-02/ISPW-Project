package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.CittàBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class InserisciCittàViaggiatore extends ControllerGrafico {

    private Utente viaggiatore;
    private UtenteBean utenteBean;
    private CreaItinerarioController creaItinerarioController;

    @FXML
    private TextField cittàTextField;

    @Override
    public void inizializza(Object controller, Utente utente) {

        utenteBean = new UtenteBean(utente);

        if (controller == null){
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

        if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        }

        viaggiatore = utente;
    }

    public void onAvantiClick(ActionEvent actionEvent) throws IOException {
        String nomeCittà = cittàTextField.getText();
        CittàBean cittàBean = new CittàBean(nomeCittà);
        creaItinerarioController.setCittàBean(cittàBean);
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciFiltriViaggiatore.fxml", "Inserimento Filtri", creaItinerarioController, viaggiatore);
    }

    public void onIndietroClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController, viaggiatore);
    }

    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController , viaggiatore);
    }

    public void mieiItinerariClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/itinerariCreatiViaggiatore.fxml", "I miei Itinerari", null, viaggiatore);
    }
}
