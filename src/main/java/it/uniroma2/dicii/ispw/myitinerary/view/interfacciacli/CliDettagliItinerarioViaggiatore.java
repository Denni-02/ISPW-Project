package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.VisualizzaItinerariController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CliDettagliItinerarioViaggiatore {

    //private final CreaItinerarioController creaItinerarioController;
    private final VisualizzaItinerariController visualizzaItinerariController;
    private final Scanner scanner = new Scanner(System.in);

    /*public CliDettagliItinerarioViaggiatore(CreaItinerarioController creaItinerarioController) {
        this.creaItinerarioController = creaItinerarioController;
    }*/

    public CliDettagliItinerarioViaggiatore(VisualizzaItinerariController visualizzaItinerariController) {
        this.visualizzaItinerariController = visualizzaItinerariController;
    }

    public void display() {
        //ItinerarioBean itinerario = creaItinerarioController.getItinerarioBean();
        ItinerarioBean itinerario = visualizzaItinerariController.getItinerarioBean();

        if (itinerario == null) {
            System.out.println("Errore: Itinerario non trovato.");
            tornaAllaMieiItinerari();
            return;
        }

        System.out.println("==== Dettagli Itinerario ====");

        //ItinerarioBean dettagli = creaItinerarioController.getDettagliItinerario(itinerario.getId());
        ItinerarioBean dettagli = visualizzaItinerariController.getDettagliItinerario(itinerario.getId());

        System.out.println("Città: " + dettagli.getNomeCittà());
        System.out.println("Numero di Giorni: " + dettagli.getNumeroGiorni());

        // Verifica che la mappa delle attività per giorno non sia null
        Map<Integer, List<Attività>> attivitàPerGiorno = dettagli.getAttivitàPerGiorno();
        if (attivitàPerGiorno == null || attivitàPerGiorno.isEmpty()) {
            System.out.println("Nessuna attività pianificata per questo itinerario.");
        } else {
            // Mostra le attività per giorno
            for (Map.Entry<Integer, List<Attività>> entry : attivitàPerGiorno.entrySet()) {
                int giorno = entry.getKey();
                System.out.println("Giorno " + giorno + ":");

                for (Attività attività : entry.getValue()) {
                    System.out.println("  - " + attività.getNome() + ": " + attività.getDescrizione());
                }
            }
        }

        System.out.print("Premi invio per tornare alla lista degli itinerari...");
        scanner.nextLine();

        tornaAllaMieiItinerari();
    }


    private void tornaAllaMieiItinerari() {
        //new CliMieiItinerariViaggiatore(creaItinerarioController.getUtenteBean()).display();
        new CliMieiItinerariViaggiatore(visualizzaItinerariController.getUtenteBean()).display();
    }
}
