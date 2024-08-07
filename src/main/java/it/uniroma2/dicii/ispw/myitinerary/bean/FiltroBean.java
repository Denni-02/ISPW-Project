package it.uniroma2.dicii.ispw.myitinerary.bean;

public class FiltroBean {

    private boolean musei;
    private boolean attrazioni;
    private boolean parchi;
    private boolean musica;
    private boolean arte;
    private boolean vitaNotturna;
    private boolean biblioteche;

    private int giorniViaggio;

    public int getGiorniViaggio() {
        return giorniViaggio;
    }

    public void setGiorniViaggio(int giorniViaggio) {
        this.giorniViaggio = giorniViaggio;
    }

    public void setMusei(boolean selected) {
        this.musei = selected;
    }

    public void setAttrazioni(boolean selected) {
        this.attrazioni = selected;
    }

    public void setParchi(boolean selected) {
        this.parchi = selected;
    }

    public void setMusica(boolean selected) {
        this.musica = selected;
    }

    public void setArte(boolean selected) {
        this.arte = selected;
    }

    public void setVitaNotturna(boolean selected) {
        this.vitaNotturna = selected;
    }

    public void setBiblioteche(boolean selected) {
        this.biblioteche = selected;
    }

    public boolean isMusei() {
        return musei;
    }

    public boolean isAttrazioni() {
        return attrazioni;
    }

    public boolean isParchi() {
        return parchi;
    }

    public boolean isMusica() {
        return musica;
    }

    public boolean isArte() {
        return arte;
    }

    public boolean isVitaNotturna() {
        return vitaNotturna;
    }

    public boolean isBiblioteche() {
        return biblioteche;
    }
}
