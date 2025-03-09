package it.uniroma2.dicii.ispw.myitinerary.model.domain;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Itinerario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeCittà;
    private String utenteId;
    private int numeroGiorni;
    private Map<Integer, List<Attività>> attivitàPerGiorno;
    private Date dataCreazione;
    private int id;

    // Costruttore vuoto
    public Itinerario() {}

    // Costruttore con parametri
    public Itinerario(String nomeCittà, String utenteId, int numeroGiorni, Map<Integer, List<Attività>> attivitàPerGiorno) {
        this.nomeCittà = nomeCittà;
        this.utenteId = utenteId;
        this.numeroGiorni = numeroGiorni;
        this.attivitàPerGiorno = attivitàPerGiorno;
    }

    public Itinerario(String nomeCittà, String utenteId, int numeroGiorni, Map<Integer, List<Attività>> attivitàPerGiorno, Date dataCreazione) {
        this.nomeCittà = nomeCittà;
        this.utenteId = utenteId;
        this.numeroGiorni = numeroGiorni;
        this.attivitàPerGiorno = attivitàPerGiorno;
        this.dataCreazione = dataCreazione;
    }

    public Itinerario(String nomeCittà, String utenteId, int numeroGiorni, Map<Integer, List<Attività>> attivitàPerGiorno, Date dataCreazione, int id) {
        this.nomeCittà = nomeCittà;
        this.utenteId = utenteId;
        this.numeroGiorni = numeroGiorni;
        this.attivitàPerGiorno = attivitàPerGiorno;
        this.dataCreazione = dataCreazione;
        this.id = id;
    }

    public Itinerario(ItinerarioBean itinerarioBean) {
        this.nomeCittà = itinerarioBean.getNomeCittà();
        this.utenteId = itinerarioBean.getUtenteId();
        this.numeroGiorni = itinerarioBean.getNumeroGiorni();
        this.attivitàPerGiorno = itinerarioBean.getAttivitàPerGiorno();
        this.dataCreazione = itinerarioBean.getDataCreazione();
    }


    // Getter e Setter
    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getNomeCittà() {
        return nomeCittà;
    }

    public void setNomeCittà(String nomeCittà) {
        this.nomeCittà = nomeCittà;
    }

    public String getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(String utenteId) {
        this.utenteId = utenteId;
    }

    public int getNumeroGiorni() {
        return numeroGiorni;
    }

    public void setNumeroGiorni(int numeroGiorni) {
        this.numeroGiorni = numeroGiorni;
    }

    public Map<Integer, List<Attività>> getAttivitàPerGiorno() {
        return attivitàPerGiorno;
    }

    public void setAttivitàPerGiorno(Map<Integer, List<Attività>> attivitàPerGiorno) {
        this.attivitàPerGiorno = attivitàPerGiorno;
    }


    public void setId(int newId) {
        this.id = newId;
    }

    public Integer getId() {
        return id;
    }
}

