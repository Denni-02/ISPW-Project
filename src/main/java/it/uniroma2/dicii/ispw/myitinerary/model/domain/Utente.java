package it.uniroma2.dicii.ispw.myitinerary.model.domain;

import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.UtenteBean;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.Ruolo;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.UtenteDAO;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.UtenteDBMS;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.UtenteFS;
import java.util.Date;

public class Utente {

    private String nome;
    private String cognome;
    private String cf;
    private Date dataDiNascita;
    private String email;
    private String password;
    private Ruolo ruolo;

    private UtenteDAO utenteDAO;

    public Utente() {
        if(App.getPersistenceLayer().equals(PersistenceType.JDBC)){
            utenteDAO = new UtenteDBMS();
        } else {
            utenteDAO = new UtenteFS();
        }
    }

    public Utente(String nome, String cognome, String cf, Date dataDiNascita, String email, String password ,Ruolo ruolo) {
        this();
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public Utente(UtenteBean utenteBean){
        this();
        this.nome = utenteBean.getNome();
        this.cognome = utenteBean.getCognome();
        this.cf = utenteBean.getCf();
        this.dataDiNascita = utenteBean.getDataDiNascita();
        this.email = utenteBean.getEmail();
        this.password = utenteBean.getPassword();
        this.ruolo = utenteBean.getRuolo();
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getCf() { return cf; }
    public void setCf(String cf) { this.cf = cf; }

    public Date getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(Date dataDiNascita) { this.dataDiNascita = dataDiNascita; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Ruolo getRuolo() { return ruolo; }
    public void setRuolo(Ruolo ruolo) { this.ruolo = ruolo; }
}
