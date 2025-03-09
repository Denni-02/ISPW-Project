package it.uniroma2.dicii.ispw.myitinerary.bean;

import java.util.List;
import java.util.Map;

public class AnnuncioBean {

    private String cfUtente;
    private String tipo;
    private String nome;
    private String città;
    private String indirizzo;
    private List<byte[]> foto; // Lista di immagini come byte array
    private String sitoWeb;
    private String descrizione;

    public AnnuncioBean(String nome, String città, String indirizzo, String sitoWeb, String descrizione, String tipo, String cfUtente) {
        this.nome = nome;
        this.città = città;
        this.indirizzo = indirizzo;
        this.sitoWeb = sitoWeb;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.cfUtente = cfUtente;
    }

    public AnnuncioBean() {

    }

    public String getCfUtente() {
        return cfUtente;
    }

    public void setCfUtente(String cfUtente) {
        this.cfUtente = cfUtente;
    }

    public void setTipo(String value) {
        this.tipo = value;
    }

    public void setNome(String text) {
        this.nome = text;
    }

    public void setCittà(String text) {
        this.città = text;
    }

    public void setIndirizzo(String text) {
        this.indirizzo = text;
    }

    public void setFoto(List<byte[]> fotoList) {
        this.foto = fotoList;
    }

    public void setSitoWeb(String text) {
        this.sitoWeb = text;
    }

    public void setDescrizione(String text) {
        this.descrizione = text;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getCittà() {
        return città;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public List<byte[]> getFoto() {
        return foto;
    }

    public String getSitoWeb() {
        return sitoWeb;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
