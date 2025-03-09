package it.uniroma2.dicii.ispw.myitinerary.utils;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller.ControllerGrafico;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class ChangePage {

    public static void changeScene(Event event, String fxmFile, String title, Object controller, Utente utente) throws IOException {

        URL url = App.class.getResource(fxmFile);
        if (url == null) {
            LoggerManager.logSevere("File fxml non trovato.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        ControllerGrafico graphicController = fxmlLoader.getController();
        graphicController.inizializza(controller, utente);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
