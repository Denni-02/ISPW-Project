package it.uniroma2.dicii.ispw.myitinerary.utils;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.controller.LoginController;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.Ruolo;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.LoginFS;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;

import java.util.Date;

public class TestAddUser {

    public static void main(String[] args) {
        // Inizializza il layer di persistenza e UI
        App.setPersistenceAndUI();

        // Crea un nuovo utente con le informazioni di base
        Utente utente = new Utente("Luca", "Bianchi", "BNCLCA80A01H501Z", new Date(), "2", "2", Ruolo.PROPRIETARIO_ATTIVITA);

        // Inizializza LoginFS o LoginDBMS a seconda del layer di persistenza selezionato
        LoginController loginController = new LoginController();

        // Qui potresti salvare l'utente nel database o file system, a seconda della persistenza scelta
        loginController.saveUser(utente); // Metodo che devi implementare in LoginController per salvare l'utente

        System.out.println("Utente creato con successo!");
    }
}
