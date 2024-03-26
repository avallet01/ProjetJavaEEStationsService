package javaee.station.dao;
import org.jdom2.Document; 
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javaee.station.Station;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StationDao est une classe d'objet d'accès aux données utilisée pour récupérer les informations des stations à partir d'un fichier XML.
 */
public class StationDao {
    
    /**
     * Récupère toutes les stations à partir du fichier XML spécifié par le chemin du fichier.
     * 
     * @param filePath Le chemin vers le fichier XML contenant les données des stations.
     * @return Une liste d'objets Station remplis avec les données du fichier XML.
     */
    public List<Station> getAllStations(String filePath) {
        List<Station> stations = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(filePath);

        try {
            Document document = saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();
            List<Element> stationList = rootElement.getChildren("pdv");

            // Itérer sur chaque élément station dans le XML et peupler l'objet Station
            for (Element stationElement : stationList) {
                Station station = new Station();
                // Définir les attributs de base de la station
                station.setId(stationElement.getAttributeValue("id"));
                station.setLatitude(stationElement.getAttributeValue("latitude"));
                station.setLongitude(stationElement.getAttributeValue("longitude"));
                station.setCp(stationElement.getAttributeValue("cp"));
                station.setVille(stationElement.getChildText("ville"));
                station.setAdresse(stationElement.getChildText("adresse"));

                // Traiter les services offerts par la station
                List<String> services = new ArrayList<>();
                Element servicesElement = stationElement.getChild("services");
                if (servicesElement != null) {
                    for (Element serviceElement : servicesElement.getChildren("service")) {
                        services.add(serviceElement.getTextNormalize());
                    }
                }
                station.setServices(services);

                // Traiter les horaires d'ouverture de la station
                Element horairesElement = stationElement.getChild("horaires");
                Map<String, String> openingHours = new HashMap<>();
                if (horairesElement != null) {
                    for (Element jour : horairesElement.getChildren("jour")) {
                        String dayName = jour.getAttributeValue("nom");
                        Element horaire = jour.getChild("horaire");
                        String hours = "Fermé"; // Valeur par défaut si l'attribut "ferme" est présent ou pas d'élément "horaire"
                        if (horaire != null) {
                            String openingTime = horaire.getAttributeValue("ouverture");
                            String closingTime = horaire.getAttributeValue("fermeture");
                            hours = openingTime + " - " + closingTime;
                        }
                        openingHours.put(dayName, hours);
                    }
                }
                station.setOpeningHours(openingHours);

                // Traiter les prix des carburants proposés par la station
                List<Element> priceElements = stationElement.getChildren("prix");
                Map<String, Double> fuelPrices = new HashMap<>();
                for (Element priceElement : priceElements) {
                    String fuelType = priceElement.getAttributeValue("nom");
                    String value = priceElement.getAttributeValue("valeur");

                    if (fuelType != null && value != null) {
                        try {
                            double price = Double.parseDouble(value.trim());
                            fuelPrices.put(fuelType, price);  
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur lors de l'analyse du prix du carburant pour " + fuelType + " : " + value);
                        }
                    } else {
                        System.err.println("Type de carburant ou valeur manquante pour un élément de prix.");
                    }
                }
                station.setFuelPrices(fuelPrices);
                stations.add(station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }
}
