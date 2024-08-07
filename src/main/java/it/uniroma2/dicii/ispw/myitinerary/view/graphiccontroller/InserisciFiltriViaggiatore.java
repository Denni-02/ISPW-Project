package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.FiltroBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class InserisciFiltriViaggiatore extends ControllerGrafico{

    private Utente viaggiatore;
    private UtenteBean utenteBean;

    @FXML
    private Button museiButton;
    @FXML
    private Button attrazioniButton;
    @FXML
    private Button parchiButton;
    @FXML
    private Button musicaButton;
    @FXML
    private Button arteButton;
    @FXML
    private Button vitaNotturnaButton;
    @FXML
    private Button bibliotecheButton;

    private CreaItinerarioController creaItinerarioController;

    @FXML
    private TextField giorniTextField;

    @Override
    public void inizializza(Object controller, Utente utente) {

        viaggiatore = utente;
        utenteBean = new UtenteBean(utente);

        if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        } else {
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

        configureButton(museiButton);
        configureButton(attrazioniButton);
        configureButton(parchiButton);
        configureButton(musicaButton);
        configureButton(arteButton);
        configureButton(vitaNotturnaButton);
        configureButton(bibliotecheButton);

    }

    private void configureButton(Button button) {
        button.setOnAction(event -> {
            if (button.getStyleClass().contains("selected")) {
                button.getStyleClass().remove("selected");
                button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: #000000; -fx-border-width: 1;");
            } else {
                button.getStyleClass().add("selected");
                button.setStyle("-fx-background-color: black; -fx-text-fill: white;");
            }
        });
    }

    public void onAvantiClick(ActionEvent actionEvent) throws IOException {
        int giorniViaggio = Integer.parseInt(giorniTextField.getText());
        FiltroBean filtroBean = new FiltroBean();
        filtroBean.setMusei(museiButton.getStyleClass().contains("selected"));
        filtroBean.setAttrazioni(attrazioniButton.getStyleClass().contains("selected"));
        filtroBean.setParchi(parchiButton.getStyleClass().contains("selected"));
        filtroBean.setMusica(musicaButton.getStyleClass().contains("selected"));
        filtroBean.setArte(arteButton.getStyleClass().contains("selected"));
        filtroBean.setVitaNotturna(vitaNotturnaButton.getStyleClass().contains("selected"));
        filtroBean.setBiblioteche(bibliotecheButton.getStyleClass().contains("selected"));
        filtroBean.setGiorniViaggio(giorniViaggio);
        creaItinerarioController.setFiltroBean(filtroBean);
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/selezionaAttivitàViaggiatore.fxml", "Selezione Attività", creaItinerarioController , viaggiatore);
    }

    public void onIndietroClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciCittàViaggiatore.fxml", "Inserimento Città", creaItinerarioController , viaggiatore);
    }

    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController , viaggiatore);
    }

    public void mieiItinerariClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/itinerariCreatiViaggiatore.fxml", "I miei Itinerari", null, viaggiatore);
    }
}
