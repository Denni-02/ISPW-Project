package it.uniroma2.dicii.ispw.myitinerary.bean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;

public class AttivitàConGiornoBean {

    private final int giorno;
    private final String nome;
    private final String descrizione;
    private final String indirizzo;
    private final float rating;

    public AttivitàConGiornoBean(int giorno, Attività attività) {
        this.giorno = giorno;
        this.nome = attività.getNome();
        this.descrizione = attività.getDescrizione();
        this.indirizzo = attività.getIndirizzo();
        this.rating = attività.getRating();
    }

    public int getGiorno() {
        return giorno;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public float getRating() {
        return rating;
    }
}
