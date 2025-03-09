package it.uniroma2.dicii.ispw.myitinerary.utils;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private static final String ID_FILE_NAME = "id_counter.dat";
    private static AtomicInteger counter;

    static {
        counter = new AtomicInteger(loadLastId());
    }

    private static int loadLastId() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ID_FILE_NAME))) {
            return ois.readInt();
        } catch (FileNotFoundException e) {
            // Il file non esiste ancora, inizia da 0
            return 0;
        } catch (IOException e) {
            // In caso di errore, stampa lo stack trace e inizia da 0
            e.printStackTrace();
            return 0;
        }
    }

    private static void saveCurrentId(int id) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ID_FILE_NAME))) {
            oos.writeInt(id);
        } catch (IOException e) {
            // In caso di errore, stampa lo stack trace
            e.printStackTrace();
        }
    }

    public static synchronized int getNextId() {
        int id = counter.incrementAndGet();
        saveCurrentId(id);
        return id;
    }
}
