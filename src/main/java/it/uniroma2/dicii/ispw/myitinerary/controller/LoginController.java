package it.uniroma2.dicii.ispw.myitinerary.controller;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.exceptions.InvalidDataException;
import it.uniroma2.dicii.ispw.myitinerary.exceptions.ItemNotFoundException;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.LoginDAO;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.LoginDBMS;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.LoginFS;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import java.sql.SQLException;

public class LoginController {

    private LoginDAO loginDAO;

    public LoginController() {
        if(App.getPersistenceLayer().equals(PersistenceType.JDBC) ){
            loginDAO = new LoginDBMS();
        } else {
            loginDAO = new LoginFS();
        }
    }

    public UtenteBean login(LoginBean loginBean) throws InvalidDataException, SQLException, ItemNotFoundException {

        if(loginBean.getEmail().isBlank() || loginBean.getPassword().isBlank()){
            throw new InvalidDataException("Email e password necessarie");
        }

        Utente utente = loginDAO.autenticazione(loginBean);

        if(utente == null){
            throw new ItemNotFoundException("Accesso negato");
        }

        return new UtenteBean(utente.getNome(), utente.getCognome(), utente.getCf(), utente.getDataDiNascita(), utente.getEmail(), utente.getPassword(), utente.getRuolo());
    }

}
