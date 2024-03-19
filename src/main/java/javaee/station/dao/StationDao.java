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

public class StationDao {
    public List<Station> getAllStations(String filePath) {
        List<Station> stations = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(filePath); 

        try {
            Document document = saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();
            List<Element> stationList = rootElement.getChildren("pdv");

            for (Element stationElement : stationList) {
                Station station = new Station();
                station.setId(stationElement.getAttributeValue("id"));
                station.setLatitude(stationElement.getAttributeValue("latitude"));
                station.setLongitude(stationElement.getAttributeValue("longitude"));
                station.setCp(stationElement.getAttributeValue("cp"));
                station.setVille(stationElement.getChildText("ville"));
                station.setAdresse(stationElement.getChildText("adresse"));
                List<String> services = new ArrayList<>();
                Element servicesElement = stationElement.getChild("services");
                if (servicesElement != null) {
                    for (Element serviceElement : servicesElement.getChildren("service")) {
                        services.add(serviceElement.getTextNormalize());
                    }
                }
                station.setServices(services);
                Element horairesElement = stationElement.getChild("horaires");
                Map<String, String> openingHours = new HashMap<>();
                if (horairesElement != null) {
                    for (Element jour : horairesElement.getChildren("jour")) {
                        String dayName = jour.getAttributeValue("nom");
                        Element horaire = jour.getChild("horaire");
                        String hours = "Closed"; // Default value if "ferme" attribute is present or no "horaire" element
                        if (horaire != null) {
                            String openingTime = horaire.getAttributeValue("ouverture");
                            String closingTime = horaire.getAttributeValue("fermeture");
                            hours = openingTime + " - " + closingTime;
                        }
                        openingHours.put(dayName, hours);
                    }
                }
                station.setOpeningHours(openingHours);
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
                            System.err.println("Error parsing fuel price for " + fuelType + ": " + value);
                        }
                    } else {
                        System.err.println("Missing fuel type or value for a price element.");
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
