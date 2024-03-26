package javaee.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La classe Station représente une station-service avec ses informations et ses services.
 */
public class Station {
    private String id;
    private String latitude;
    private String longitude;
    private String cp; // Code postal
    private String ville; // Ville
    private String adresse; // Adresse
    private List<String> services = new ArrayList<>(); // Liste des services disponibles
    private Map<String, String> openingHours; // Horaires d'ouverture
    private Map<String, Double> fuelPrices; // Prix des carburants

    /**
     * Constructeur par défaut de la classe Station.
     */
    public Station() {
    }

    // Getters et Setters avec des commentaires Javadoc pour chaque méthode

    /**
     * Obtient l'ID de la station.
     * @return ID de la station.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtient la latitude de la station.
     * @return Latitude de la station.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Obtient la longitude de la station.
     * @return Longitude de la station.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Obtient le code postal de la station.
     * @return Code postal de la station.
     */
    public String getCp() {
        return cp;
    }

    /**
     * Obtient la ville où se trouve la station.
     * @return Ville de la station.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Obtient l'adresse de la station.
     * @return Adresse de la station.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Obtient la liste des services disponibles à la station.
     * @return Liste des services.
     */
    public List<String> getServices() {
        return services;
    }

    /**
     * Définit les services disponibles à la station.
     * @param services Les services à définir.
     */
    public void setServices(List<String> services) {
        this.services = services;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Convertit la latitude en degrés.
     * @return Latitude convertie en degrés.
     */
    public double getLatitudeInDegrees() {
        if (latitude != null && !latitude.isEmpty()) {
            return Double.parseDouble(latitude) / 100000;
        } else {
            return 0; // Valeur par défaut si la latitude est vide
        }
    }

    /**
     * Convertit la longitude en degrés.
     * @return Longitude convertie en degrés.
     */
    public double getLongitudeInDegrees() {
        if (longitude != null && !longitude.isEmpty()) {
            return Double.parseDouble(longitude) / 100000;
        } else {
            return 0; // Valeur par défaut si la longitude est vide
        }
    }

    
    public Map<String, String> getOpeningHours() {
        return openingHours;
    }


    public void setOpeningHours(Map<String, String> openingHours) {
        this.openingHours = openingHours;
    }

   
    public Map<String, Double> getFuelPrices() {
        return fuelPrices;
    }

    public void setFuelPrices(Map<String, Double> fuelPrices) {
        this.fuelPrices = fuelPrices;
    }
}
