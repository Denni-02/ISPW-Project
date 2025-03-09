package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;
import it.uniroma2.dicii.ispw.myitinerary.controller.VisualizzaItinerariController;

import java.util.List;
import java.util.Scanner;

public class CliMieiItinerariViaggiatore {

    private final UtenteBean utenteBean;
    //private final CreaItinerarioController creaItinerarioController;
    private final VisualizzaItinerariController visualizzaItinerariController;
    private final Scanner scanner = new Scanner(System.in);

    public CliMieiItinerariViaggiatore(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
        //this.creaItinerarioController = new CreaItinerarioController(utenteBean);
        this.visualizzaItinerariController = new VisualizzaItinerariController(utenteBean);
    }

    public void display() {
        System.out.println("==== I Miei Itinerari ====");

        //List<ItinerarioBean> itinerariList = creaItinerarioController.getItinerariByUtente(utenteBean.getCf());
        List<ItinerarioBean> itinerariList = visualizzaItinerariController.getItinerariByUtente(utenteBean.getCf());

        if (itinerariList.isEmpty()) {
            System.out.println("Non hai itinerari creati.");
            tornaAllaHomePage();
            return;
        }

        // Mostra la lista di itinerari
        for (int i = 0; i < itinerariList.size(); i++) {
            ItinerarioBean itinerario = itinerariList.get(i);
            System.out.println((i + 1) + ". " + itinerario.getNomeCittà() + " - " + itinerario.getNumeroGiorni() + " giorno/i");
        }

        // Chiedi all'utente di selezionare un itinerario per visualizzare i dettagli
        System.out.print("Seleziona il numero dell'itinerario per vedere i dettagli (oppure 0 per tornare indietro): ");
        int scelta = Integer.parseInt(scanner.nextLine());

        if (scelta > 0 && scelta <= itinerariList.size()) {
            ItinerarioBean itinerarioSelezionato = itinerariList.get(scelta - 1);
            //creaItinerarioController.setItinerarioBean(itinerarioSelezionato);
            visualizzaItinerariController.setItinerarioBean(itinerarioSelezionato);
            //new CliDettagliItinerarioViaggiatore(creaItinerarioController).display();
            new CliDettagliItinerarioViaggiatore(visualizzaItinerariController).display();
        } else if (scelta == 0) {
            tornaAllaHomePage();
        } else {
            System.out.println("Scelta non valida. Riprova.");
            display();
        }
    }

    private void tornaAllaHomePage() {
        CliHomePageViaggiatore homePage = new CliHomePageViaggiatore(utenteBean);
        homePage.display();
    }
}
