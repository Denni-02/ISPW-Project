package it.uniroma2.dicii.ispw.myitinerary.model.domain;

import it.uniroma2.dicii.ispw.myitinerary.bean.AttivitàBean;

import java.io.Serializable;

public class Attività implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descrizione;
    private String indirizzo;
    private float rating;
    private int numeroRecensioni;
    private String imageUrl;

    public Attività() {}

    public Attività(String nome, String descrizione, String indirizzo, float rating, int numeroRecensioni, String imageUrl) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.rating = rating;
        this.numeroRecensioni = numeroRecensioni;
        this.imageUrl = imageUrl;
    }

    public Attività(AttivitàBean attivitàBean){
        this.nome = attivitàBean.getNome();
        this.descrizione = attivitàBean.getDescrizione();
        this.indirizzo = attivitàBean.getIndirizzo();
        this.rating = attivitàBean.getRating();
        this.numeroRecensioni = attivitàBean.getNumeroRecensioni();
        this.imageUrl = attivitàBean.getImageUrl();
    }

    public Attività(String nome, String descrizione, String indirizzo, String orari) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumeroRecensioni() {
        return numeroRecensioni;
    }

    public void setNumeroRecensioni(int numeroRecensioni) {
        this.numeroRecensioni = numeroRecensioni;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }
}
