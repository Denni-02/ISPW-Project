package it.uniroma2.dicii.ispw.myitinerary.controller;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.*;

import java.sql.SQLException;

public class InserisciAnnuncioController {

    private AnnuncioDAO annuncioDAO;
    private UtenteBean utenteBean;

    public InserisciAnnuncioController(UtenteBean utenteBean) {

        this.utenteBean = utenteBean;

        if(App.getPersistenceLayer().equals(PersistenceType.JDBC) ){
            this.annuncioDAO = new AnnuncioDBMS();
        } else {
            this.annuncioDAO = new AnnuncioFS();
        }

    }

    public void inserisciAnnuncio(AnnuncioBean annuncioBean) throws SQLException {
        annuncioDAO.inserisciAnnuncio(annuncioBean);
    }
}
