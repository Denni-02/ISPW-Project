package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import java.util.List;

public interface ItinerarioDAO {

    public void saveItinerario(ItinerarioBean itinerarioBean);

    List<ItinerarioBean> getItinerariByUtente(String utenteId);
    ItinerarioBean getDettagliItinerario(int idItinerario);

    void deleteItinerario(int id);
}
