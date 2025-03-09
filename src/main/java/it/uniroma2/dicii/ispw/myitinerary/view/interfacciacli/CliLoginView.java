package it.uniroma2.dicii.ispw.myitinerary.view.interfacciacli;

import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.LoginController;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.Ruolo;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;

import java.util.Scanner;

public class CliLoginView {

    private LoginController loginController;
    private Scanner scanner;

    public CliLoginView() {
        this.loginController = new LoginController();
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        System.out.println("==== Login ====");

        System.out.print("Inserisci email: ");
        String email = scanner.nextLine();

        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        try {
            if (!email.isBlank() && !password.isBlank()) {
                LoginBean loginBean = new LoginBean(email, password);
                UtenteBean utenteBean = loginController.login(loginBean);

                if (utenteBean != null) {
                    handleSuccess(utenteBean);
                } else {
                    handleError("Credenziali errate.");
                }
            } else {
                handleError("Email e password sono obbligatorie.");
            }
        } catch (Exception e) {
            handleError("Errore durante il login: " + e.getMessage());
            LoggerManager.logInfoException("Errore di autenticazione: " + e.getMessage(), e);
        }
    }

    private void handleSuccess(UtenteBean utenteBean) {
        System.out.println("Login avvenuto con successo! Benvenuto, " + utenteBean.getNome());

        if (utenteBean.getRuolo() == Ruolo.VIAGGIATORE) {
            System.out.println("Ruolo: Viaggiatore");
            // Continua con la logica specifica per il viaggiatore
            new CliHomePageViaggiatore(utenteBean).display();
        } else if (utenteBean.getRuolo() == Ruolo.PROPRIETARIO_ATTIVITA) {
            System.out.println("Ruolo: Proprietario Attività");
            // Continua con la logica specifica per il proprietario attività
            new CliHomePageProprietario(utenteBean).display();
        }
    }

    private void handleError(String msg) {
        System.out.println("Errore: " + msg);
    }


}
