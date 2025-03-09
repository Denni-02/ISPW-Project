package it.uniroma2.dicii.ispw.myitinerary.model.dao;

import it.uniroma2.dicii.ispw.myitinerary.bean.ItinerarioBean;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;
import it.uniroma2.dicii.ispw.myitinerary.utils.IDGenerator;

import java.io.*;
import java.util.*;

public class ItinerarioFS implements ItinerarioDAO {

    private static final String FILE_NAME = "itinerari.dat";
    private Map<Integer, Itinerario> itinerari;

    public ItinerarioFS() {
        itinerari = loadItinerari();
        System.out.println("Itinerari caricati: " + itinerari.size()); // Debug: Stampa quanti itinerari sono stati caricati
    }

    @Override
    public void saveItinerario(ItinerarioBean itinerarioBean) {
        int newId = IDGenerator.getNextId();
        itinerarioBean.setId(newId);
        Itinerario itinerario = new Itinerario(itinerarioBean);
        itinerario.setId(newId);
        itinerari.put(itinerario.getId(), itinerario);
        saveItinerari();

        // Debug: conferma che l'itinerario è stato salvato
        System.out.println("Itinerario salvato con ID: " + newId);
        printItinerari();  // Stampa tutti gli itinerari per verificare
    }

    @Override
    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        List<ItinerarioBean> result = new ArrayList<>();
        for (Itinerario itinerario : itinerari.values()) {
            if (itinerario.getUtenteId().equals(utenteId)) {
                result.add(new ItinerarioBean(itinerario));
            }
        }
        return result;
    }

    @Override
    public ItinerarioBean getDettagliItinerario(int idItinerario) {
        System.out.println("Caricamento dettagli per l'itinerario con ID: " + idItinerario);
        Itinerario itinerario = itinerari.get(idItinerario);
        if (itinerario == null) {
            System.out.println("Itinerario non trovato!");
            printItinerari();  // Stampa tutti gli itinerari per debug
            return null;
        }
        return new ItinerarioBean(itinerario);
    }

    @Override
    public void deleteItinerario(int idItinerario) {
        itinerari.remove(idItinerario);
        saveItinerari();
    }

    private Map<Integer, Itinerario> loadItinerari() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                Map<Integer, Itinerario> loadedItinerari = (Map<Integer, Itinerario>) obj;
                System.out.println("Itinerari caricati: " + loadedItinerari.size());
                return loadedItinerari;
            } else {
                throw new IOException("Formato del file non valido.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato, creando un nuovo file.");
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void saveItinerari() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(itinerari);
            System.out.println("Itinerari salvati su file: " + FILE_NAME); // Debug: Conferma del salvataggio
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printItinerari() {
        System.out.println("Stato corrente degli itinerari salvati:");
        for (Map.Entry<Integer, Itinerario> entry : itinerari.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Città: " + entry.getValue().getNomeCittà() + ", Numero Giorni: " + entry.getValue().getNumeroGiorni());
        }
    }
}
