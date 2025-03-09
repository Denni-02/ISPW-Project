package it.uniroma2.dicii.ispw.myitinerary.controller;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDAO;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDBMS;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioFS;

import java.util.List;

public class VisualizzaItinerariController {

    private ItinerarioDAO itinerarioDAO;
    private UtenteBean utenteBean;
    private ItinerarioBean itinerarioBean;

    public VisualizzaItinerariController(UtenteBean utenteBean) {

        this.utenteBean = utenteBean;

        if(App.getPersistenceLayer().equals(PersistenceType.JDBC) ){
            this.itinerarioDAO = new ItinerarioDBMS();
        } else {
            this.itinerarioDAO = new ItinerarioFS();
        }

    }

    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        return itinerarioDAO.getItinerariByUtente(utenteId);
    }

    public void setItinerarioBean(ItinerarioBean itinerarioSelezionato) {
        this.itinerarioBean = itinerarioSelezionato;
    }

    public ItinerarioBean getItinerarioBean() {
        return itinerarioBean;
    }

    public ItinerarioBean getDettagliItinerario(int id) {
        return itinerarioDAO.getDettagliItinerario(id);
    }

    public UtenteBean getUtenteBean() {
        return utenteBean;
    }

    public void cancellaItinerario(ItinerarioBean selectedItinerario) {
    }
}
