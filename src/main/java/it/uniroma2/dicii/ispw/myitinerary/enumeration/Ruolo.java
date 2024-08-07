package it.uniroma2.dicii.ispw.myitinerary.enumeration;

public enum Ruolo {
    VIAGGIATORE(0),
    PROPRIETARIO_ATTIVITA(1);

    private final int id;

    Ruolo(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public static Ruolo getRuoloById(int id) {
        for (Ruolo ruolo : Ruolo.values()) {
            if (ruolo.getId() == id) {
                return ruolo;
            }
        }
        return null;
    }
}
