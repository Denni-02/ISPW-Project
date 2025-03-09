package it.uniroma2.dicii.ispw.myitinerary;

import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.UIType;
import it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli.CliLoginView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App extends Application {

    private static PersistenceType persistence;
    private static UIType ui;

    public static void main(String[] args) {
        setPersistenceAndUI();
        if(App.ui.equals(UIType.JAVAFX)){
            launch();
        } else {
            new CliLoginView().display();
        }
    }

    public static void setPersistenceAndUI(){
        try(InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")){

            Properties prop = new Properties();
            prop.load(input);

            if (prop.getProperty("persistence").equals("FileSystem")) {
                App.persistence = PersistenceType.FYLE_SYSTEM;
            } else {
                App.persistence = PersistenceType.JDBC;
            }

            if (prop.getProperty("ui").equals("JavaFX")) {
                App.ui = UIType.JAVAFX;
            } else {
                App.ui = UIType.CLI;
            }

            System.out.println(App.persistence); //DEBUG
            System.out.println(App.ui); //DEBUG

        } catch (IOException e) {
            LoggerManager.logSevereException("Impossibile leggere file configurazione. Si procede con la scelta di default: JDBC e JAVAFX\n", e);
            App.persistence = PersistenceType.JDBC;
            App.ui = UIType.JAVAFX;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/it/uniroma2/dicii/ispw/myitinerary/views/login.fxml"));
        InputStream url = getClass().getResourceAsStream("/it/uniroma2/dicii/ispw/myitinerary/images/icon.png");

        if(url != null){
            Image icon = new Image(url);
            stage.getIcons().add(icon);
        }

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("MyItinerary");
        stage.show();
    }

    public static PersistenceType getPersistenceLayer(){
        return persistence;
    }

}
