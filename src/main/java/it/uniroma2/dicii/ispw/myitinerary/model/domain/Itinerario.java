package it.uniroma2.dicii.ispw.myitinerary.model.domain;

import java.util.List;
import java.util.Map;

public class Itinerario {

    private String nomeCittà;
    private String utenteId;
    private int numeroGiorni;
    private Map<Integer, List<Attività>> attivitàPerGiorno;

    // Costruttore vuoto
    public Itinerario() {}

    // Costruttore con parametri
    public Itinerario(String nomeCittà, String utenteId, int numeroGiorni, Map<Integer, List<Attività>> attivitàPerGiorno) {
        this.nomeCittà = nomeCittà;
        this.utenteId = utenteId;
        this.numeroGiorni = numeroGiorni;
        this.attivitàPerGiorno = attivitàPerGiorno;
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

}

