package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.event.ActionEvent;
import java.io.IOException;
import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class HomePageViaggiatore extends ControllerGrafico {

    private Utente viaggiatore;
    private UtenteBean utenteBean;

    @Override
    public void inizializza(Object controller, Utente utente) {
        viaggiatore = utente;
    }

    public void onCreaClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciCittàViaggiatore.fxml", "Inserimento Città", null, viaggiatore);
    }

    public void mieiItinerariClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/itinerariCreatiViaggiatore.fxml", "I miei Itinerari", null, viaggiatore);
    }
}
