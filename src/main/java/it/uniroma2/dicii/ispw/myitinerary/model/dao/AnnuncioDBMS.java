package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.AnnuncioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Annuncio;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;
import it.uniroma2.dicii.ispw.myitinerary.utils.SingletonDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnuncioDBMS implements AnnuncioDAO {

    @Override
    public void inserisciAnnuncio(AnnuncioBean annuncioBean) throws SQLException {
        String query = "INSERT INTO annuncio (nome, città, indirizzo, sitoWeb, descrizione, tipo, utenteId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = SingletonDBConnection.getInstance().getConnection().prepareStatement(query)) {
            ps.setString(1, annuncioBean.getNome());
            ps.setString(2, annuncioBean.getCittà());
            ps.setString(3, annuncioBean.getIndirizzo());
            ps.setString(4, annuncioBean.getSitoWeb());
            ps.setString(5, annuncioBean.getDescrizione());
            ps.setString(6, annuncioBean.getTipo());
            ps.setString(7, annuncioBean.getCfUtente());  // Passa l'utenteId (codice fiscale)
            ps.executeUpdate();
        } catch (SQLException e) {
            LoggerManager.logSevereException("Errore durante l'inserimento dell'annuncio", e);
            throw e;
        }
    }

    @Override
    public List<Annuncio> getAnnunciByUtenteId(int utenteId) throws SQLException {
        String query = "SELECT * FROM Annuncio WHERE utenteId = ?";
        List<Annuncio> annunci = new ArrayList<>();
        try (Connection conn = SingletonDBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, utenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Annuncio annuncio = new Annuncio();
                    annuncio.setId(rs.getInt("id"));
                    annuncio.setTipo(rs.getString("tipo"));
                    annuncio.setNome(rs.getString("nome"));
                    annuncio.setCittà(rs.getString("città"));
                    annuncio.setIndirizzo(rs.getString("indirizzo"));
                    annuncio.setFoto(rs.getBytes("foto"));
                    annuncio.setSitoWeb(rs.getString("sitoWeb"));
                    annuncio.setDescrizione(rs.getString("descrizione"));
                    annuncio.setUtenteId(rs.getInt("utente_id"));
                    annunci.add(annuncio);
                }
            }
        }
        return annunci;
    }

    @Override
    public Annuncio getAnnuncioById(int id) throws SQLException {
        String query = "SELECT * FROM Annuncio WHERE id = ?";
        try (Connection conn = SingletonDBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Annuncio annuncio = new Annuncio();
                    annuncio.setId(rs.getInt("id"));
                    annuncio.setTipo(rs.getString("tipo"));
                    annuncio.setNome(rs.getString("nome"));
                    annuncio.setCittà(rs.getString("città"));
                    annuncio.setIndirizzo(rs.getString("indirizzo"));
                    annuncio.setFoto(rs.getBytes("foto"));
                    annuncio.setSitoWeb(rs.getString("sitoWeb"));
                    annuncio.setDescrizione(rs.getString("descrizione"));
                    annuncio.setUtenteId(rs.getInt("utente_id"));
                    return annuncio;
                }
            }
        }
        return null;
    }

    @Override
    public void cancellaAnnuncio(int id) throws SQLException {
        String query = "DELETE FROM Annuncio WHERE id = ?";
        try (Connection conn = SingletonDBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
