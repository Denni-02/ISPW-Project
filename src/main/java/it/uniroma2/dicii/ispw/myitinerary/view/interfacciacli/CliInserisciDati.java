package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.InserisciAnnuncioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliInserisciDati {

    private InserisciAnnuncioController controller;
    private UtenteBean utenteBean;
    private Scanner scanner = new Scanner(System.in);

    public CliInserisciDati(UtenteBean utente) {
        this.controller = new InserisciAnnuncioController(utente);
        this.utenteBean = utente;
    }

    public void display() {
        AnnuncioBean annuncioBean = new AnnuncioBean();

        System.out.println("Inserisci i dettagli dell'annuncio:");

        System.out.print("Tipo (RISTORANTE/ALLOGGIO): ");
        String tipo = scanner.nextLine().toUpperCase();
        annuncioBean.setTipo(tipo);

        System.out.print("Nome: ");
        annuncioBean.setNome(scanner.nextLine());

        System.out.print("Città: ");
        annuncioBean.setCittà(scanner.nextLine());

        System.out.print("Indirizzo: ");
        annuncioBean.setIndirizzo(scanner.nextLine());

        // Foto
        System.out.print("Vuoi caricare foto? (s/n): ");
        String caricaFoto = scanner.nextLine();
        List<byte[]> fotoList = new ArrayList<>();
        if (caricaFoto.equalsIgnoreCase("s")) {
            System.out.print("Numero di foto da caricare: ");
            int numFoto = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < numFoto; i++) {
                // Per semplicità, richiediamo solo il percorso dei file qui
                System.out.print("Percorso foto " + (i + 1) + ": ");
                String percorso = scanner.nextLine();
                // Carica le foto in byte array come fatto in JavaFX
                // ...
            }
        }
        annuncioBean.setFoto(fotoList);

        System.out.print("Sito Web (opzionale): ");
        annuncioBean.setSitoWeb(scanner.nextLine());

        System.out.print("Descrizione: ");
        annuncioBean.setDescrizione(scanner.nextLine());

        annuncioBean.setCfUtente(utenteBean.getCf());

        try {
            controller.inserisciAnnuncio(annuncioBean);
            System.out.println("Annuncio inserito con successo!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante l'inserimento dell'annuncio.");
        }
    }
}
