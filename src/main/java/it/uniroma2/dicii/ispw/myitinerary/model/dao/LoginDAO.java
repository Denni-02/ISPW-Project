package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.LoginBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;
import java.sql.SQLException;

public interface LoginDAO {
    public Utente autenticazione(LoginBean loginBean) throws SQLException;
}
