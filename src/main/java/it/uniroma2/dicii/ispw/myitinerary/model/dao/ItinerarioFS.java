package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;

import java.util.List;

public class ItinerarioFS implements ItinerarioDAO {

    @Override
    public void saveItinerario(ItinerarioBean itinerarioBean) {

    }

    @Override
    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        return List.of();
    }

    @Override
    public ItinerarioBean getDettagliItinerario(int idItinerario) {
        return null;
    }

}
