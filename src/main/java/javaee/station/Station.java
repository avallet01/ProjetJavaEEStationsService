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

    public Station() {
    }


    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCp() {
        return cp;
    }

    public String getVille() {
        return ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public List<String> getServices() {
        return services;
    }

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
