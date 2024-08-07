package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlaceType;
import it.uniroma2.dicii.ispw.myitinerary.bean.AttivitàBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.FiltroBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.errors.ApiException;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Optional;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class SelezionaAttivitàViaggiatore extends ControllerGrafico {

    private Utente viaggiatore;
    private UtenteBean utenteBean;

    private static final String API_KEY = "AIzaSyD6DGet6wWAVnJeeZEIFBA6fOQMNRfmHPM";

    private ObservableList<Attività> attivitaList;

    @FXML
    private ListView<Attività> attivitaListView;
    @FXML
    private ChoiceBox<Integer> giornoChoiceBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Label numeroRecensioniLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label descrizioneLabel;
    @FXML
    private Label indirizzoLabel;

    private CreaItinerarioController creaItinerarioController;

    @Override
    public void inizializza(Object controller, Utente utente) {

        System.out.println("Metodo initialize chiamato");

        if (controller instanceof CreaItinerarioController) {
            this.creaItinerarioController = (CreaItinerarioController) controller;
        } else {
            this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        }

        viaggiatore = utente;
        utenteBean = new UtenteBean(viaggiatore);

        attivitaList = FXCollections.observableArrayList();
        attivitaListView.setItems(attivitaList);

        // Inizializzazione giorni
        ObservableList<Integer> giorniList = FXCollections.observableArrayList();
        for (int i = 1; i <= creaItinerarioController.getFiltroBean().getGiorniViaggio(); i++) {
            giorniList.add(i);
        }
        giornoChoiceBox.setItems(giorniList);
        giornoChoiceBox.getSelectionModel().selectFirst();

        caricaAttivitaDaGoogleMaps();
        mostraAttivitàTrovate();
    }

    private void caricaAttivitaDaGoogleMaps() {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        String cityName = creaItinerarioController.getCittàBean().getNomeCittà();
        FiltroBean filtro = creaItinerarioController.getFiltroBean();

        if (filtro.isMusei()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.MUSEUM);
        }
        if (filtro.isAttrazioni()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.TOURIST_ATTRACTION);
        }
        if (filtro.isParchi()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.PARK);
        }
        if (filtro.isMusica()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.MOVIE_THEATER); // come esempio
        }
        if (filtro.isArte()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.ART_GALLERY);
        }
        if (filtro.isVitaNotturna()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.NIGHT_CLUB);
        }
        if (filtro.isBiblioteche()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.LIBRARY);
        }
    }

    private void aggiungiAttivitaByPlaceType(GeoApiContext context, String cityName, PlaceType placeType) {
        try {

            TextSearchRequest request = PlacesApi.textSearchQuery(context, cityName).type(placeType);
            PlacesSearchResponse response = request.await();
            System.out.println("Risultati trovati per " + placeType + ": " + response.results.length);

            for (PlacesSearchResult result : response.results) {
                System.out.println("Nome: " + result.name);
                //Attività attività = new Attività();
                AttivitàBean attivitàBean = new AttivitàBean();

                //attività.setNome(result.name != null ? result.name : "Nome non disponibile");
                attivitàBean.setNome(result.name != null ? result.name : "Nome non disponibile");
                System.out.println(result.name);

                String description;
                switch (placeType) {
                    case MUSEUM: description = "Museo"; break;
                    case TOURIST_ATTRACTION: description = "Monumento"; break;
                    case PARK: description = "Parco"; break;
                    case NIGHT_CLUB: description = "Vita Notturna"; break;
                    case LIBRARY: description = "Libro"; break;
                    case ART_GALLERY: description = "Arte"; break;
                    case MOVIE_THEATER: description = "Teatro"; break;
                    default: description = "Non trovata"; break;
                }

                //attività.setDescrizione(description);
                attivitàBean.setDescrizione(description);
                System.out.println(String.valueOf(placeType));

                //attività.setIndirizzo(result.formattedAddress != null ? result.formattedAddress : "Indirizzo non disponibile");
                attivitàBean.setIndirizzo(result.formattedAddress != null ? result.formattedAddress : "Indirizzo non disponibile");
                System.out.println(result.formattedAddress);

                //attività.setRating(result.rating);
                attivitàBean.setRating(result.rating);
                System.out.println(result.rating);

                //attività.setNumeroRecensioni(result.userRatingsTotal);
                attivitàBean.setNumeroRecensioni(result.userRatingsTotal);
                System.out.println(result.userRatingsTotal);

                if (result.photos != null && result.photos.length > 0) {
                    String photoReference = result.photos[0].photoReference;
                    //attività.setImageUrl("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                            //+ photoReference + "&key=" + API_KEY);
                    attivitàBean.setImageUrl("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                            + photoReference + "&key=" + API_KEY);
                }

                //attività.setId(result.placeId != null ? result.placeId.hashCode() : 0);
                attivitàBean.setId(result.placeId != null ? result.placeId.hashCode() : 0);

                Attività attività = new Attività(attivitàBean);

                attivitaList.add(attività);
                System.out.println("Numero di attività aggiunte: " + attivitaList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostraAttivitàTrovate(){

        // Imposta la visualizzazione degli elementi nella ListView
        attivitaListView.setItems(attivitaList);
        attivitaListView.setCellFactory(param -> new ListCell<Attività>() {
            @Override
            protected void updateItem(Attività item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNome() == null) {
                    setText(null);
                } else {
                    setText(item.getNome());
                }
            }
        });

        attivitaListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                indirizzoLabel.setText(newValue.getIndirizzo());
                double roundedRating = Math.round(newValue.getRating() * 100.0) / 100.0;
                ratingLabel.setText(String.valueOf(roundedRating));
                descrizioneLabel.setText(newValue.getDescrizione());
                numeroRecensioniLabel.setText(String.valueOf(newValue.getNumeroRecensioni()));

                // Carica e mostra l'immagine
                if (newValue.getImageUrl() != null) {
                    Image image = new Image(newValue.getImageUrl());
                    imageView.setImage(image);
                } else {
                    imageView.setImage(null); // Pulisci l'ImageView se non c'è immagine disponibile
                }
            }
        });
    }


    public void onIndietroClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciFiltriViaggiatore.fxml", "Inserimento Filtri", creaItinerarioController , viaggiatore);
    }

    public void onHomeClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", creaItinerarioController, viaggiatore);
    }

    public void onSalvaItinerarioClick(ActionEvent actionEvent) {

        // Crea un'alert di conferma
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Salvataggio");
        alert.setHeaderText("Salvataggio dell'Itinerario");
        alert.setContentText("Vuoi salvare l'itinerario corrente?");

        // Opzioni di azione
        ButtonType buttonTypeSalva = new ButtonType("Salva");
        ButtonType buttonTypeAnnulla = new ButtonType("Annulla");

        // Aggiunta dei bottoni
        alert.getButtonTypes().setAll(buttonTypeSalva, buttonTypeAnnulla);

        // Attendi la risposta dell'utente
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeSalva) {
            try {
                // Salva l'itinerario attraverso il controller
                creaItinerarioController.salvaItinerario();
                System.out.println("Itinerario salvato con successo.");
                // Naviga a una schermata di conferma o home
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Errore durante il salvataggio dell'itinerario.");
            }
        } else {
            // Azione annullata, chiudi semplicemente l'alert
            System.out.println("Salvataggio annullato.");
        }
    }

    public void onAggiungiAttivitàClick(ActionEvent actionEvent) {


        // Ottieni l'attività selezionata dalla ListView
        Attività attivitaSelezionata = attivitaListView.getSelectionModel().getSelectedItem();
        // Ottieni il giorno selezionato dal ChoiceBox
        Integer giornoSelezionato = giornoChoiceBox.getValue();

        if (attivitaSelezionata != null && giornoSelezionato != null) {
            // Aggiungi l'attività all'itinerario per il giorno selezionato
            creaItinerarioController.aggiungiAttivitàAGiorno(attivitaSelezionata, giornoSelezionato);
            System.out.println("Attività aggiunta all'itinerario: " + attivitaSelezionata.getNome());
        } else {
            // Gestisci il caso in cui non sia stata selezionata nessuna attività o giorno
            System.out.println("Seleziona un'attività e un giorno prima di aggiungere.");
        }
    }

    public void mieiItinerariClick(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/itinerariCreatiViaggiatore.fxml", "I miei Itinerari", null, viaggiatore);
    }
}
