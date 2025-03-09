package it.uniroma2.dicii.ispw.myitinerary.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import it.uniroma2.dicii.ispw.myitinerary.App;
import it.uniroma2.dicii.ispw.myitinerary.bean.*;
import it.uniroma2.dicii.ispw.myitinerary.enumeration.PersistenceType;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDAO;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioDBMS;
import it.uniroma2.dicii.ispw.myitinerary.model.dao.ItinerarioFS;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Attività;
import it.uniroma2.dicii.ispw.myitinerary.model.domain.Itinerario;
import it.uniroma2.dicii.ispw.myitinerary.utils.SingletonDBConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CreaItinerarioController {

    private CittàBean cittàBean;
    private FiltroBean filtroBean;
    private UtenteBean utenteBean;
    private ItinerarioBean itinerarioBean;

    private ItinerarioDAO itinerarioDAO;
    private Map<Integer, List<Attività>> attivitàPerGiorno;

    public CreaItinerarioController(UtenteBean utenteBean) {

        this.utenteBean = utenteBean;

        if(App.getPersistenceLayer().equals(PersistenceType.JDBC) ){
            this.itinerarioDAO = new ItinerarioDBMS();
        } else {
            this.itinerarioDAO = new ItinerarioFS();
        }

        this.attivitàPerGiorno = new HashMap<Integer, List<Attività>>();
    }

    public void setCittàBean(CittàBean cittàBean) {
        this.cittàBean = cittàBean;
    }

    public void setFiltroBean(FiltroBean filtroBean) {
        this.filtroBean = filtroBean;
    }

    public CittàBean getCittàBean(){
        return this.cittàBean;
    }

    public FiltroBean getFiltroBean(){
        return this.filtroBean;
    }

    public void setUtenteBean(UtenteBean utenteBean) {
        this.utenteBean = utenteBean;
    }

    public UtenteBean getUtenteBean(){
        return this.utenteBean;
    }

    public void aggiungiAttivitàAGiorno(Attività attività, int giorno){
        attivitàPerGiorno.computeIfAbsent(giorno, k -> new ArrayList<>()).add(attività);
    }

    public void salvaItinerario() {
        Itinerario itinerario = new Itinerario(cittàBean.getNomeCittà(), utenteBean.getCf(), filtroBean.getGiorniViaggio(), attivitàPerGiorno, new Date());
        ItinerarioBean itinerarioBean = new ItinerarioBean(itinerario);
        itinerarioDAO.saveItinerario(itinerarioBean);
    }

    public List<ItinerarioBean> getItinerariByUtente(String utenteId) {
        return itinerarioDAO.getItinerariByUtente(utenteId);
    }

    public ItinerarioBean getDettagliItinerario(int id) {
        return itinerarioDAO.getDettagliItinerario(id);
    }

    public void setItinerarioBean(ItinerarioBean itinerarioBean) {
        this.itinerarioBean = itinerarioBean;
    }

    public ItinerarioBean getItinerarioBean() {
        return itinerarioBean;
    }

    public void rimuoviAttivitàDaGiorno(Attività attività, Integer giorno) {
        if (attivitàPerGiorno.containsKey(giorno)) {
            attivitàPerGiorno.get(giorno).remove(attività);
        }
    }

    public void cancellaItinerario(ItinerarioBean selectedItinerario) {
        itinerarioDAO.deleteItinerario(selectedItinerario.getId());
    }

    private List<Attività> attivitàDisponibili = new ArrayList<>();
    private String api_key;

    public List<Attività> getAttivitàDisponibili() {
        if (attivitàDisponibili.isEmpty()) {
            caricaAttivitaDaGoogleMaps();
        }
        return attivitàDisponibili;
    }

    private void caricaAttivitaDaGoogleMaps() {

        try (InputStream input = SingletonDBConnection.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            api_key = prop.getProperty("API");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Caricamento delle attività simulato come nella versione JavaFX
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(api_key)
                .build();

        String cityName = cittàBean.getNomeCittà();
        FiltroBean filtro = filtroBean;

        if (filtro.isMusei()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.MUSEUM);
        }
        if (filtro.isAttrazioni()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.TOURIST_ATTRACTION);
        }
        if (filtro.isParchi()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.PARK);
        }
        if (filtro.isMusica()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.MOVIE_THEATER); // come esempio
        }
        if (filtro.isArte()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.ART_GALLERY);
        }
        if (filtro.isVitaNotturna()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.NIGHT_CLUB);
        }
        if (filtro.isBiblioteche()) {
            aggiungiAttivitaByPlaceType(context, cityName, PlaceType.LIBRARY);
        }
    }

    private void aggiungiAttivitaByPlaceType(GeoApiContext context, String cityName, PlaceType placeType) {
        try {
            TextSearchRequest request = PlacesApi.textSearchQuery(context, cityName).type(placeType);
            PlacesSearchResponse response = request.await();

            for (var result : response.results) {
                AttivitàBean attivitàBean = new AttivitàBean();
                attivitàBean.setNome(result.name != null ? result.name : "Nome non disponibile");

                String description;
                switch (placeType) {
                    case MUSEUM:
                        description = "Museo";
                        break;
                    case TOURIST_ATTRACTION:
                        description = "Monumento";
                        break;
                    case PARK:
                        description = "Parco";
                        break;
                    case NIGHT_CLUB:
                        description = "Vita Notturna";
                        break;
                    case LIBRARY:
                        description = "Libro";
                        break;
                    case ART_GALLERY:
                        description = "Arte";
                        break;
                    case MOVIE_THEATER:
                        description = "Teatro";
                        break;
                    default:
                        description = "Non trovata";
                        break;
                }

                attivitàBean.setDescrizione(description);
                attivitàBean.setIndirizzo(result.formattedAddress != null ? result.formattedAddress : "Indirizzo non disponibile");
                attivitàBean.setRating(result.rating);
                attivitàBean.setNumeroRecensioni(result.userRatingsTotal);
                if (result.photos != null && result.photos.length > 0) {
                    String photoReference = result.photos[0].photoReference;
                    attivitàBean.setImageUrl("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=la-tua-API-key");
                }
                attivitàDisponibili.add(new Attività(attivitàBean));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}