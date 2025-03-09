package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import com.google.maps.GeoApiContext;
import com.google.maps.PlaceAutocompleteRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.PlaceAutocompleteType;
import it.uniroma2.dicii.ispw.myitinerary.bean.CittàBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class InserisciCittàViaggiatore extends ControllerGrafico {

    private Utente viaggiatore;
    private UtenteBean utenteBean;
    private CreaItinerarioController creaItinerarioController;

    private GeoApiContext context;
    private String apiKey;
    private PlaceAutocompleteRequest.SessionToken sessionToken;

    @FXML
    private TextField cittàTextField;

    @FXML
    private ListView<String> suggestionsListView;

    @FXML
    private ListView<String> recentCitiesListView;

    private Preferences preferences;

    private static final String RECENT_CITIES_KEY = "recentCities";
    private static final int MAX_RECENT_CITIES = 10;

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

        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            var prop = new Properties();
            prop.load(input);
            apiKey = prop.getProperty("API");
            context = new GeoApiContext.Builder().apiKey(apiKey).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sessionToken = new PlaceAutocompleteRequest.SessionToken();

        cittàTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateSuggestions(newValue);
            } else {
                suggestionsListView.getItems().clear();
            }
        });

        suggestionsListView.setOnMouseClicked(event -> {
            String selectedCity = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                cittàTextField.setText(selectedCity);
                suggestionsListView.getItems().clear();
            }
        });

        preferences = Preferences.userNodeForPackage(this.getClass());
        loadRecentCities();

        cittàTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                updateSuggestions(newValue);
            } else {
                suggestionsListView.getItems().clear();
            }
        });

        suggestionsListView.setOnMouseClicked(event -> {
            String selectedCity = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                cittàTextField.setText(selectedCity);
                suggestionsListView.getItems().clear();
                addRecentCity(selectedCity);
            }
        });

        recentCitiesListView.setOnMouseClicked(event -> {
            String selectedCity = recentCitiesListView.getSelectionModel().getSelectedItem();
            if (selectedCity != null) {
                cittàTextField.setText(selectedCity);
            }
        });
    }

    private void loadRecentCities() {
        try {
            String[] recentCities = preferences.keys();
            List<String> recentCitiesList = new ArrayList<>();
            for (String city : recentCities) {
                recentCitiesList.add(preferences.get(city, ""));
            }
            ObservableList<String> observableList = FXCollections.observableArrayList(recentCitiesList);
            recentCitiesListView.setItems(observableList);
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    private void addRecentCity(String city) {
        try {
            String[] recentCities = preferences.keys();
            if (recentCities.length >= MAX_RECENT_CITIES) {
                // Rimuovi la città più vecchia
                preferences.remove(recentCities[0]);
            }
            // Aggiungi la nuova città
            preferences.put(city, city);
            loadRecentCities();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }

    private void updateSuggestions(String query) {
        try {
            AutocompletePrediction[] predictions = PlacesApi.placeAutocomplete(context, query, sessionToken)
                    .types(PlaceAutocompleteType.CITIES)
                    .await();
                    //.predictions;
            List<String> suggestions = List.of(predictions).stream()
                    .map(prediction -> prediction.description)
                    .collect(Collectors.toList());
            ObservableList<String> observableSuggestions = FXCollections.observableArrayList(suggestions);
            suggestionsListView.setItems(observableSuggestions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAvantiClick(ActionEvent actionEvent) throws IOException {
        String nomeCittà = cittàTextField.getText();
        if (nomeCittà == null || nomeCittà.trim().isEmpty()) {
            // Mostra popup di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di inserimento");
            alert.setHeaderText("Campo città vuoto");
            alert.setContentText("Per favore, inserisci una città prima di procedere.");
            alert.showAndWait();
        } else {
            // Procedi come al solito
            CittàBean cittàBean = new CittàBean(nomeCittà);
            creaItinerarioController.setCittàBean(cittàBean);
            changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciFiltriViaggiatore.fxml", "Inserimento Filtri", creaItinerarioController, viaggiatore);
        }
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

    public void onCercaClick(ActionEvent actionEvent) throws IOException {
        String nomeCittà = cittàTextField.getText();
        if (nomeCittà == null || nomeCittà.trim().isEmpty()) {
            // Mostra popup di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di inserimento");
            alert.setHeaderText("Campo città vuoto");
            alert.setContentText("Per favore, inserisci una città prima di procedere.");
            alert.showAndWait();
        } else {
            // Procedi come al solito
            CittàBean cittàBean = new CittàBean(nomeCittà);
            creaItinerarioController.setCittàBean(cittàBean);
            changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciFiltriViaggiatore.fxml", "Inserimento Filtri", creaItinerarioController, viaggiatore);
        }
    }

    public void tastoCentraleClick(ActionEvent actionEvent) throws IOException {
        String nomeCittà = cittàTextField.getText();
        if (nomeCittà == null || nomeCittà.trim().isEmpty()) {
            // Mostra popup di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di inserimento");
            alert.setHeaderText("Campo città vuoto");
            alert.setContentText("Per favore, inserisci una città prima di procedere.");
            alert.showAndWait();
        } else {
            // Procedi come al solito
            CittàBean cittàBean = new CittàBean(nomeCittà);
            creaItinerarioController.setCittàBean(cittàBean);
            changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/inserisciFiltriViaggiatore.fxml", "Inserimento Filtri", creaItinerarioController, viaggiatore);
        }
    }
}
