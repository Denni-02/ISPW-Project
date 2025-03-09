package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.application.Application;
import javafx.event.ActionEvent;

import java.io.IOException;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class HomePageProprietario extends ControllerGrafico {

    private Utente utente;
    
    public void mieiAnnunciClick(ActionEvent actionEvent) {
    }

    @Override
    public void inizializza(Object controller, Utente utente) {
        this.utente = utente;
    }

    public void onInserisciRistorante(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/proprietarioattività/inserisciDati.fxml", "Inserimento Dati", null, utente);
    }

    public void onInserisciAlloggio(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/proprietarioattività/inserisciDati.fxml", "Inserimento Dati", null, utente);
    }
}
