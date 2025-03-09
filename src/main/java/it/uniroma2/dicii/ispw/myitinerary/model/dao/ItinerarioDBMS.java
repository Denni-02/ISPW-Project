package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.utils.SingletonDBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItinerarioDBMS implements ItinerarioDAO {

    @Override
    public void saveItinerario(ItinerarioBean itinerarioBean) {

        String insertItinerario = "INSERT INTO ITINERARIO (nomeCittà, numeroGiorni, idUtente, dataCreazione) VALUES (?, ?, ?, ?)";
        String insertAttivita = "INSERT INTO ATTIVITA (nome, descrizione, indirizzo, valutazione, numeroRecensioni, imageUrl) VALUES (?, ?, ?, ?, ?, ?)";
        String insertItinerarioAttivita = "INSERT INTO ITINERARIO_ATTIVITA (idItinerario, idAttività, giorno) VALUES (?, ?, ?)";

        try (Connection conn = SingletonDBConnection.getInstance().getConnection()) {

            // Inserisci l'itinerario
            PreparedStatement psItinerario = conn.prepareStatement(insertItinerario, Statement.RETURN_GENERATED_KEYS);
            psItinerario.setString(1, itinerarioBean.getNomeCittà());
            psItinerario.setInt(2, itinerarioBean.getNumeroGiorni());
            psItinerario.setString(3, itinerarioBean.getUtenteId());
            psItinerario.setDate(4, new java.sql.Date(itinerarioBean.getDataCreazione().getTime())); // Imposta la data di creazione
            psItinerario.executeUpdate();

            // Recupera l'ID generato per l'itinerario
            ResultSet rsItinerario = psItinerario.getGeneratedKeys();
            int idItinerario = 0;
            if (rsItinerario.next()) {
                idItinerario = rsItinerario.getInt(1);
            }

            // Inserisci le attività
            for (Map.Entry<Integer, List<Attività>> entry : itinerarioBean.getAttivitàPerGiorno().entrySet()) {
                for (Attività attività : entry.getValue()) {
                    PreparedStatement psAttivita = conn.prepareStatement(insertAttivita, Statement.RETURN_GENERATED_KEYS);
                    psAttivita.setString(1, attività.getNome());
                    psAttivita.setString(2, attività.getDescrizione());
                    psAttivita.setString(3, attività.getIndirizzo());
                    psAttivita.setFloat(4, attività.getRating());
                    psAttivita.setInt(5, attività.getNumeroRecensioni());
                    psAttivita.setString(6, attività.getImageUrl());
                    psAttivita.executeUpdate();

                    // Recupera l'ID generato per l'attività
                    ResultSet rsAttivita = psAttivita.getGeneratedKeys();
                    int idAttivita = 0;
                    if (rsAttivita.next()) {
                        idAttivita = rsAttivita.getInt(1);
                    }

                    // Inserisci la relazione tra itinerario e attività
                    PreparedStatement psItinerarioAttivita = conn.prepareStatement(insertItinerarioAttivita);
                    psItinerarioAttivita.setInt(1, idItinerario);
                    psItinerarioAttivita.setInt(2, idAttivita);
                    psItinerarioAttivita.setInt(3, entry.getKey());
                    psItinerarioAttivita.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        List<ItinerarioBean> itinerari = new ArrayList<>();
        String query = "SELECT iditinerario, nomeCittà, numeroGiorni, dataCreazione FROM ITINERARIO WHERE idUtente = ?";

        try (Connection conn = SingletonDBConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, utenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItinerarioBean itinerario = new ItinerarioBean();
                itinerario.setId(rs.getInt("iditinerario"));
                itinerario.setNomeCittà(rs.getString("nomeCittà"));
                itinerario.setNumeroGiorni(rs.getInt("numeroGiorni"));
                itinerario.setDataCreazione(rs.getDate("dataCreazione"));
                itinerari.add(itinerario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itinerari;
    }

    @Override
    public ItinerarioBean getDettagliItinerario(int idItinerario) {

        ItinerarioBean itinerario = new ItinerarioBean();
        Map<Integer, List<Attività>> attivitàPerGiorno = new HashMap<>();

        String queryItinerario = "SELECT nomeCittà, numeroGiorni FROM ITINERARIO WHERE iditinerario = ?";
        String queryAttività = "SELECT A.nome, A.descrizione, A.indirizzo, A.valutazione, A.numeroRecensioni, A.imageUrl, IA.giorno " +
                "FROM ATTIVITA A " +
                "JOIN ITINERARIO_ATTIVITA IA ON A.idattività = IA.idAttività " +
                "WHERE IA.idItinerario = ?";

        try (Connection conn = SingletonDBConnection.getInstance().getConnection()) {

            // Recupero dati dell'itinerario
            PreparedStatement psItinerario = conn.prepareStatement(queryItinerario);
            psItinerario.setInt(1, idItinerario);
            ResultSet rsItinerario = psItinerario.executeQuery();

            if (rsItinerario.next()) {
                itinerario.setNomeCittà(rsItinerario.getString("nomeCittà"));
                itinerario.setNumeroGiorni(rsItinerario.getInt("numeroGiorni"));
                itinerario.setId(idItinerario);
            }

            // Recupero attività per giorno
            PreparedStatement psAttività = conn.prepareStatement(queryAttività);
            psAttività.setInt(1, idItinerario);
            ResultSet rsAttività = psAttività.executeQuery();

            while (rsAttività.next()) {
                int giorno = rsAttività.getInt("giorno");
                Attività attività = new Attività();
                attività.setNome(rsAttività.getString("nome"));
                attività.setDescrizione(rsAttività.getString("descrizione"));
                attività.setIndirizzo(rsAttività.getString("indirizzo"));
                attività.setRating(rsAttività.getFloat("valutazione"));
                attività.setNumeroRecensioni(rsAttività.getInt("numeroRecensioni"));
                attività.setImageUrl(rsAttività.getString("imageUrl"));

                attivitàPerGiorno.computeIfAbsent(giorno, k -> new ArrayList<>()).add(attività);
            }

            itinerario.setAttivitàPerGiorno(attivitàPerGiorno);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itinerario;
    }

    @Override
    public void deleteItinerario(int idItinerario) {
        String deleteItinerario = "DELETE FROM ITINERARIO WHERE iditinerario = ?";
        String deleteAttivita = "DELETE FROM ATTIVITA WHERE idattività IN (SELECT idAttività FROM ITINERARIO_ATTIVITA WHERE idItinerario = ?)";
        String deleteItinerarioAttivita = "DELETE FROM ITINERARIO_ATTIVITA WHERE idItinerario = ?";

        try (Connection conn = SingletonDBConnection.getInstance().getConnection()) {

            // Cancella le relazioni tra itinerario e attività
            PreparedStatement psItinerarioAttivita = conn.prepareStatement(deleteItinerarioAttivita);
            psItinerarioAttivita.setInt(1, idItinerario);
            psItinerarioAttivita.executeUpdate();

            // Cancella le attività
            PreparedStatement psAttivita = conn.prepareStatement(deleteAttivita);
            psAttivita.setInt(1, idItinerario);
            psAttivita.executeUpdate();

            // Cancella l'itinerario
            PreparedStatement psItinerario = conn.prepareStatement(deleteItinerario);
            psItinerario.setInt(1, idItinerario);
            psItinerario.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
