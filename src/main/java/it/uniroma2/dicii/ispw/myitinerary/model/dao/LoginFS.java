package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LoginFS implements LoginDAO {

    private static final String FILE_NAME = "users.dat";
    private Map<String, Utente> utenti;

    public LoginFS() {
        utenti = loadUsers();
    }

    @Override
    public Utente autenticazione(LoginBean loginBean) {
        return utenti.get(loginBean.getEmail());
    }

    public void saveUser(Utente utente) {
        utenti.put(utente.getEmail(), utente);
        saveUsers();
    }

    private Map<String, Utente> loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, Utente>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>(); // Se il file non esiste, ritorna una nuova mappa vuota
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(utenti);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
