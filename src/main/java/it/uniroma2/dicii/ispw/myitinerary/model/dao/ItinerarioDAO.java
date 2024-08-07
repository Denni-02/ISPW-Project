package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;

import java.util.List;

public interface ItinerarioDAO {

    //public void saveItinerario(Itinerario itinerario);
    public void saveItinerario(ItinerarioBean itinerarioBean);

    List<ItinerarioBean> getItinerariByUtente(String utenteId);
    ItinerarioBean getDettagliItinerario(int idItinerario);

}
