package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.FiltroBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.CreaItinerarioController;

import java.util.Scanner;

public class CliInserisciFiltriViaggiatore {

    private final CreaItinerarioController creaItinerarioController;
    private final Scanner scanner = new Scanner(System.in);

    public CliInserisciFiltriViaggiatore(CreaItinerarioController creaItinerarioController) {
        this.creaItinerarioController = creaItinerarioController;
    }

    public void display() {
        FiltroBean filtroBean = new FiltroBean();

        System.out.print("Inserisci il numero di giorni del viaggio: ");
        String giorniText = scanner.nextLine();
        int giorniViaggio;

        try {
            giorniViaggio = Integer.parseInt(giorniText);
            if (giorniViaggio <= 0) {
                System.out.println("Errore: il numero di giorni deve essere maggiore di zero.");
                display();
                return;
            }
            filtroBean.setGiorniViaggio(giorniViaggio);
        } catch (NumberFormatException e) {
            System.out.println("Errore: inserisci un numero valido di giorni.");
            display();
            return;
        }

        filtroBean.setMusei(askForFilter("Vuoi visitare musei? (s/n): "));
        filtroBean.setAttrazioni(askForFilter("Vuoi visitare monumenti? (s/n): "));
        filtroBean.setParchi(askForFilter("Vuoi visitare parchi? (s/n): "));
        filtroBean.setMusica(askForFilter("Vuoi partecipare a eventi musicali? (s/n): "));
        filtroBean.setArte(askForFilter("Vuoi visitare gallerie d'arte? (s/n): "));
        filtroBean.setVitaNotturna(askForFilter("Vuoi vivere la vita notturna? (s/n): "));
        filtroBean.setBiblioteche(askForFilter("Vuoi visitare biblioteche? (s/n): "));

        creaItinerarioController.setFiltroBean(filtroBean);
        new CliSelezionaAttivitàViaggiatore(creaItinerarioController).display();
    }

    private boolean askForFilter(String question) {
        System.out.print(question);
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("s");
    }
}
