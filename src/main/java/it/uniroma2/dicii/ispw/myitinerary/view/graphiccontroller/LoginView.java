package it.uniroma2.dicii.ispw.myitinerary.view.graphiccontroller;

import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.controller.LoginController;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static it.uniroma2.dicii.ispw.myitinerary.utils.ChangePage.changeScene;

public class LoginView {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMsg;

    Utente utente;

    public void onAccediClick(ActionEvent actionEvent) {

        try {

            if (!email.getText().isBlank() && !password.getText().isBlank()){

                LoginBean loginBean = new LoginBean(email.getText(), password.getText());
                LoginController loginController = new LoginController();
                UtenteBean utenteBean = loginController.login(loginBean);

                if (utenteBean != null){
                    switch (utenteBean.getRuolo()){
                        case VIAGGIATORE:
                            utente = new Utente(utenteBean);
                            changeScene(actionEvent, "/it/uniroma2/dicii/ispw/myitinerary/views/viaggiatore/homePageViaggiatore.fxml", "Home Page", null, utente);
                            handleError("Viaggiatore");
                        case PROPRIETARIO_ATTIVITA:
                            handleError("Proprietario Attività");
                    }
                } else {
                    handleError("Credenziali errate");
                }

            } else {
                handleError("Email e password necessarie");
            }

        } catch (Exception e){
            handleError(e.getMessage());
            LoggerManager.logInfoException("Errore di autenticazione: " + e.getMessage(), e);
        }
    }

    private void handleError(String msg){
        this.errorMsg.setText(msg);
    }

}
