package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Annuncio;
import java.sql.SQLException;
import java.util.List;

public interface AnnuncioDAO {
    void inserisciAnnuncio(AnnuncioBean annuncioBean) throws SQLException;
    List<Annuncio> getAnnunciByUtenteId(int utenteId) throws SQLException;
    Annuncio getAnnuncioById(int id) throws SQLException;
    void cancellaAnnuncio(int id) throws SQLException;
}
