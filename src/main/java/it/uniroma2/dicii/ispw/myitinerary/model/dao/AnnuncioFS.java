package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Annuncio;

import java.sql.SQLException;
import java.util.List;

public class AnnuncioFS implements AnnuncioDAO {
    @Override
    public void inserisciAnnuncio(AnnuncioBean annuncioBean) throws SQLException {

    }

    @Override
    public List<Annuncio> getAnnunciByUtenteId(int utenteId) throws SQLException {
        return List.of();
    }

    @Override
    public Annuncio getAnnuncioById(int id) throws SQLException {
        return null;
    }

    @Override
    public void cancellaAnnuncio(int id) throws SQLException {

    }
}
