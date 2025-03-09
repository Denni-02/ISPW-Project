package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;

import java.util.Scanner;

public class CliHomePageProprietario {

    private UtenteBean utenteBean;
    private Scanner scanner = new Scanner(System.in);

    public CliHomePageProprietario(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
    }

    public void display() {
        System.out.println("==== Home Page Proprietario ====");
        System.out.println("Benvenuto " + utenteBean.getNome());
        System.out.println("1. Inserisci Annuncio");
        System.out.println("2. I Miei Annunci");
        System.out.println("3. Esci");
        System.out.print("Scegli un'opzione: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                new CliInserisciDati(utenteBean).display();
                break;
            case "2":
                //new CliMieiItinerariViaggiatore(utenteBean).display();
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
