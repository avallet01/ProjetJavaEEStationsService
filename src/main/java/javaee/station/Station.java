package javaee.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Station {
    private String id;
    private String latitude;
    private String longitude;
    private String cp; // Code postal
    private String ville; // Ville
    private String adresse; // Adresse
    private List<String> services = new ArrayList<>();
    private Map<String, String> openingHours;
    private Map<String, Double> fuelPrices;
    // Constructeur par défaut
    public Station() {
    }

    // Getters
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

    // Setters
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
    public double getLatitudeInDegrees() {
        if (latitude != null && !latitude.isEmpty()) {
            return Double.parseDouble(latitude) / 100000;
        } else {
            // Retourner une valeur par défaut ou lancer une exception selon votre logique d'application
            return 0; // Valeur par défaut si la latitude est vide
        }
    }

    public double getLongitudeInDegrees() {
        if (longitude != null && !longitude.isEmpty()) {
            return Double.parseDouble(longitude) / 100000;
        } else {
            // Retourner une valeur par défaut ou lancer une exception selon votre logique d'application
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
