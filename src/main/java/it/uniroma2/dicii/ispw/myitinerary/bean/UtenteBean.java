package it.uniroma2.dicii.ispw.myitinerary.bean;

import it.uniroma2.dicii.ispw.myitinerary.enumeration.Ruolo;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Utente;

import java.util.Date;

public class UtenteBean {

    private String nome;
    private String cognome;
    private String cf;
    private Date dataDiNascita;
    private String email;
    private String password;
    private Ruolo ruolo;

    public UtenteBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UtenteBean(String nome, String cognome, String cf, Date dataDiNascita, String email, String password ,Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataDiNascita = dataDiNascita;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    public UtenteBean(Utente utente) {
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.cf = utente.getCf();
        this.dataDiNascita = utente.getDataDiNascita();
        this.email = utente.getEmail();
        this.password = utente.getPassword();
        this.ruolo = utente.getRuolo();
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
