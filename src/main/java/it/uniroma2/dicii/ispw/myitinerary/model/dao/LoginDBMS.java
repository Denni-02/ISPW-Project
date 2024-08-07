package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.Ruolo;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import it.uniroma2.dicii.ispw.myitinerary.utils.LoggerManager;
import it.uniroma2.dicii.ispw.myitinerary.utils.SingletonDBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDBMS implements LoginDAO {

    @Override
    public Utente autenticazione(LoginBean loginBean) throws SQLException {

        Utente utente = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            String query = "select * from utente where email = ? and password = ?";
            ps = SingletonDBConnection.getInstance().getConnection().prepareStatement(query);
            ps.setString(1, loginBean.getEmail());
            ps.setString(2, loginBean.getPassword());
            rs = ps.executeQuery();

            if (rs.next()) {
                utente = setUtente(rs);
            }

        } catch (SQLException e) {
            LoggerManager.logSevereException("Errore esecuzione query SQL", e);
        } finally {
            if (rs != null) { rs.close(); }
            if (ps != null) { ps.close(); }
            SingletonDBConnection.getInstance().closeConnection();
        }
        
        return utente;
    }

    private Utente setUtente(ResultSet rs) throws SQLException {
        Utente utente = new Utente();
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));
        utente.setCf(rs.getString("cf"));
        utente.setDataDiNascita(rs.getDate("dataDiNascita"));
        utente.setEmail(rs.getString("email"));
        utente.setPassword(rs.getString("password"));
        utente.setRuolo(Ruolo.getRuoloById(rs.getInt("ruolo")));
        return utente;
    }
}
