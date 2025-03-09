package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;

import java.util.List;
import java.util.Scanner;

public class CliSelezionaAttivitàViaggiatore {

    private final CreaItinerarioController creaItinerarioController;
    private final Scanner scanner = new Scanner(System.in);

    public CliSelezionaAttivitàViaggiatore(CreaItinerarioController creaItinerarioController) {
        this.creaItinerarioController = creaItinerarioController;
    }

    public void display() {
        System.out.println("==== Seleziona Attività ====");

        // Ottenere la lista delle attività disponibili dal controller
        List<Attività> attivitàList = creaItinerarioController.getAttivitàDisponibili();

        if (attivitàList.isEmpty()) {
            System.out.println("Nessuna attività disponibile per la selezione.");
            confermaEtermina();
            return;
        }

        for (int i = 0; i < attivitàList.size(); i++) {
            Attività attività = attivitàList.get(i);
            System.out.println((i + 1) + ". " + attività.getNome() + " - " + attività.getDescrizione());
        }

        while (true) {
            System.out.print("Seleziona il numero dell'attività da aggiungere (oppure 0 per terminare): ");
            int scelta = Integer.parseInt(scanner.nextLine());

            if (scelta > 0 && scelta <= attivitàList.size()) {
                Attività attivitàSelezionata = attivitàList.get(scelta - 1);
                System.out.print("Inserisci il numero del giorno a cui assegnare l'attività: ");
                int giorno = Integer.parseInt(scanner.nextLine());

                creaItinerarioController.aggiungiAttivitàAGiorno(attivitàSelezionata, giorno);
                System.out.println("Attività aggiunta con successo!");
            } else if (scelta == 0) {
                confermaEtermina();
                break;
            } else {
                System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void confermaEtermina() {
        System.out.print("Vuoi salvare l'itinerario? (s/n): ");
        String scelta = scanner.nextLine().trim().toLowerCase();

        if (scelta.equals("s")) {
            creaItinerarioController.salvaItinerario();
            System.out.println("Itinerario salvato con successo!");
            tornaAllaHomePage();
        } else {
            System.out.println("Itinerario non salvato.");
            tornaAllaHomePage();
        }
    }

    private void tornaAllaHomePage() {
        CliHomePageViaggiatore homePage = new CliHomePageViaggiatore(creaItinerarioController.getUtenteBean());
        homePage.display();
    }
}
