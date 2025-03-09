package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;

import java.util.Scanner;

public class CliHomePageViaggiatore {

    private UtenteBean utenteBean;
    private Scanner scanner = new Scanner(System.in);

    public CliHomePageViaggiatore(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
    }

    public void display() {
        System.out.println("==== Home Page Viaggiatore ====");
        System.out.println("Benvenuto " + utenteBean.getNome());
        System.out.println("1. Crea Nuovo Itinerario");
        System.out.println("2. I Miei Itinerari");
        System.out.println("3. Esci");
        System.out.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                new CliInserisciCittàViaggiatore(utenteBean).display();
                break;
            case "2":
                new CliMieiItinerariViaggiatore(utenteBean).display();
                break;
            case "3":
                System.out.println("Uscita...");
                break;
            default:
                System.out.println("Opzione non valida. Riprova.");
                display();
                break;
        }
    }
}
