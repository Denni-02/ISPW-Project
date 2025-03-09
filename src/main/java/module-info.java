module it.uniroma2.dicii.ispw.myitinerary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires google.maps.services;
    requires java.prefs;
    requires com.google.gson;

    opens it.uniroma2.dicii.ispw.myitinerary to javafx.fxml;
    exports it.uniroma2.dicii.ispw.myitinerary;

    exports it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller to javafx.fxml;
    opens it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller to javafx.fxml;

    opens it.uniroma2.dicii.ispw.myitinerary.model.domain to javafx.base;
    opens it.uniroma2.dicii.ispw.myitinerary.bean to javafx.base;
}