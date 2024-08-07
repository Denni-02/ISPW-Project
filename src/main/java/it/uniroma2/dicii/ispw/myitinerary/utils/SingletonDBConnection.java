package it.uniroma2.dicii.ispw.myitinerary.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonDBConnection {

    private static SingletonDBConnection instance = null; //unica istanza

    protected Connection conn = null;

    protected SingletonDBConnection() {}

    public Connection getConnection() {

        try(InputStream input = SingletonDBConnection.class.getClassLoader().getResourceAsStream("application.properties")){

            Properties prop = new Properties();
            prop.load(input);

            String connection_url = prop.getProperty("CONNECTION_URL");
            String user = prop.getProperty("LOGIN_USER");
            String pass = prop.getProperty("LOGIN_PASS");

            conn = DriverManager.getConnection(connection_url, user, pass);

        } catch (IOException e) {
            LoggerManager.logSevereException("Errore caricamento file di properties.", e);
        } catch (SQLException e) {
            LoggerManager.logSevereException("Connessione al DB fallita.", e);
        }

        return conn;
    }

    public static  synchronized SingletonDBConnection getInstance() {
        if (instance == null) {
            instance = new SingletonDBConnection();
        }
        return instance;
    }

    public void closeConnection() {
        try {
            this.conn.close();
        }catch(SQLException e) {
            LoggerManager.logSevereException("Chiusura DB fallita.", e);
        }
    }
}
