package it.uniroma2.dicii.ispw.myitinerary.controller;
import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.FiltroBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.CittàBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDAO;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDBMS;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioFS;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreaItinerarioController {

    private CittàBean cittàBean;
    private FiltroBean filtroBean;
    private UtenteBean utenteBean;
    private ItinerarioBean itinerarioBean;

    private ItinerarioDAO itinerarioDAO;
    private Map<Integer, List<Attività>> attivitàPerGiorno;

    public CreaItinerarioController(UtenteBean utenteBean) {

        this.utenteBean = utenteBean;

        if(App.getPersistenceLayer().equals(PersistenceType.JDBC) ){
            this.itinerarioDAO = new ItinerarioDBMS();
        } else {
            this.itinerarioDAO = new ItinerarioFS();
        }

        this.attivitàPerGiorno = new HashMap<Integer, List<Attività>>();
    }

    public void setCittàBean(CittàBean cittàBean) {
        this.cittàBean = cittàBean;
    }

    public void setFiltroBean(FiltroBean filtroBean) {
        this.filtroBean = filtroBean;
    }

    public CittàBean getCittàBean(){
        return this.cittàBean;
    }

    public FiltroBean getFiltroBean(){
        return this.filtroBean;
    }

    public void setUtenteBean(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
    }

    public UtenteBean getUtenteBean(){
        return this.utenteBean;
    }

    public void aggiungiAttivitàAGiorno(Attività attività, int giorno){
        attivitàPerGiorno.computeIfAbsent(giorno, k -> new ArrayList<>()).add(attività);
    }

    public void salvaItinerario() {
        Itinerario itinerario = new Itinerario(cittàBean.getNomeCittà(), utenteBean.getCf(), filtroBean.getGiorniViaggio(), attivitàPerGiorno);
        ItinerarioBean itinerarioBean = new ItinerarioBean(itinerario);
        //itinerarioDAO.saveItinerario(itinerario);
        itinerarioDAO.saveItinerario(itinerarioBean);
    }

    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        return itinerarioDAO.getItinerariByUtente(utenteId);
    }

    public ItinerarioBean getDettagliItinerario(int id) {
        return itinerarioDAO.getDettagliItinerario(id);
    }

    public void setItinerarioBean(ItinerarioBean itinerarioBean) {
        this.itinerarioBean = itinerarioBean;
    }

    public ItinerarioBean getItinerarioBean() {
        return itinerarioBean;
    }
}