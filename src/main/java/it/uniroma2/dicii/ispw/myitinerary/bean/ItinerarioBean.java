package it.uniroma2.dicii.ispw.myitinerary.bean;

import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;

import java.util.List;
import java.util.Map;

public class ItinerarioBean {

    private String nomeCittà;
    private String utenteId;
    private int numeroGiorni;
    private int idItinerario;
    private Map<Integer, List<Attività>> attivitàPerGiorno;

    // Costruttore vuoto
    public ItinerarioBean() {}

    // Costruttore con parametri
    public ItinerarioBean(String nomeCittà, String utenteId, int numeroGiorni, Map<Integer, List<Attività>> attivitàPerGiorno) {
        this.nomeCittà = nomeCittà;
        this.utenteId = utenteId;
        this.numeroGiorni = numeroGiorni;
        this.attivitàPerGiorno = attivitàPerGiorno;
    }

    public ItinerarioBean(Itinerario itinerario){
        this.nomeCittà = itinerario.getNomeCittà();
        this.utenteId = itinerario.getUtenteId();
        this.numeroGiorni = itinerario.getNumeroGiorni();
        this.attivitàPerGiorno = itinerario.getAttivitàPerGiorno();
    }

    // Getter e Setter
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

    public void setId(int idItinerario) {
        this.idItinerario = idItinerario;
    }

    public int getId() {
        return idItinerario;
    }
}
