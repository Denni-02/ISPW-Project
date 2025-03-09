package it.uniroma2.dicii.ispw.myitinerary.model.domain;

public class Annuncio {

    private int id;
    private String tipo; // "RISTORANTE" o "ALLOGGIO"
    private String nome;
    private String città;
    private String indirizzo;
    private byte[] foto; // Array di byte per le immagini
    private String sitoWeb;
    private String descrizione;
    private int utenteId;

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setFoto(byte[] fotos) {
        this.foto = fotos;
    }

    public void setSitoWeb(String sitoWeb) {
        this.sitoWeb = sitoWeb;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setUtenteId(int utenteId) {
        this.utenteId = utenteId;
    }
}
