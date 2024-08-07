package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.AttivitàConGiornoBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class DettagliItinerarioViaggiatore extends ControllerGrafico{

    private CreaItinerarioController creaItinerarioController;
    private Utente viaggiatore;
    private UtenteBean utenteBean;

    @FXML
    private TableView<AttivitàConGiornoBean> dettagliItinerarioTableView;

    private ItinerarioBean itinerarioBean;

    @Override
    public void inizializza(Object controller, Utente utente) {

        if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        } else {
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

        viaggiatore = utente;
        utenteBean = new UtenteBean(viaggiatore);
        itinerarioBean = creaItinerarioController.getItinerarioBean();

        // Recupera i dettagli dell'itinerario
        ItinerarioBean dettagli = creaItinerarioController.getDettagliItinerario(itinerarioBean.getId());

        // Imposta le colonne della tabella
        TableColumn<AttivitàConGiornoBean, Integer> giornoColonna = new TableColumn<>("Giorno");
        giornoColonna.setCellValueFactory(new PropertyValueFactory<>("giorno"));

        TableColumn<AttivitàConGiornoBean, String> nomeColonna = new TableColumn<>("Nome Attività");
        nomeColonna.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<AttivitàConGiornoBean, String> descrizioneColonna = new TableColumn<>("Descrizione");
        descrizioneColonna.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        TableColumn<AttivitàConGiornoBean, String> indirizzoColonna = new TableColumn<>("Indirizzo");
        indirizzoColonna.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));

        TableColumn<AttivitàConGiornoBean, Float> valutazioneColonna = new TableColumn<>("Valutazione");
        valutazioneColonna.setCellValueFactory(new PropertyValueFactory<>("rating"));

        dettagliItinerarioTableView.getColumns().addAll(giornoColonna, nomeColonna, descrizioneColonna, indirizzoColonna, valutazioneColonna);

        // Aggiungi i dati alla tabella
        ObservableList<AttivitàConGiornoBean> attivitàList = FXCollections.observableArrayList();

        for (Map.Entry<Integer, List<Attività>> entry : dettagli.getAttivitàPerGiorno().entrySet()) {
            int giorno = entry.getKey();
            for (Attività attività : entry.getValue()) {
                attivitàList.add(new AttivitàConGiornoBean(giorno, attività));
            }
        }

        dettagliItinerarioTableView.setItems(attivitàList);

        // Ordinamento per giorno
        dettagliItinerarioTableView.getSortOrder().add(giornoColonna);

    }

    public void indietroButton(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/itinerariCreatiViaggiatore.fxml", "I miei itinerari", creaItinerarioController, viaggiatore);
    }
}
