package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.CittàBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;

import java.util.Scanner;

public class CliInserisciCittàViaggiatore {

    private final UtenteBean utenteBean;
    private final CreaItinerarioController creaItinerarioController;
    private final Scanner scanner = new Scanner(System.in);

    public CliInserisciCittàViaggiatore(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
        this.creaItinerarioController = new CreaItinerarioController(utenteBean);
    }

    public void display() {
        System.out.print("Inserisci il nome della città: ");
        String nomeCittà = scanner.nextLine();

        if (nomeCittà == null || nomeCittà.trim().isEmpty()) {
            System.out.println("Errore: il campo città non può essere vuoto.");
            display();
        } else {
            CittàBean cittàBean = new CittàBean(nomeCittà);
            creaItinerarioController.setCittàBean(cittàBean);
            new CliInserisciFiltriViaggiatore(creaItinerarioController).display();
        }
    }
}
