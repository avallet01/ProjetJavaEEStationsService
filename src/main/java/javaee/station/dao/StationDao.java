package javaee.station.dao;
import org.jdom2.Document; 
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javaee.station.Station;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StationDao {
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File("/Users/Do Ro The/eclipse-workspace/JavaEEStationService/PrixCarburants_annuel_2024.xml"); 

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
                // Ajoutez d'autres attributs 
                // Extraction des services
                List<String> services = new ArrayList<>();
                Element servicesElement = stationElement.getChild("services");
                if (servicesElement != null) {
                    for (Element serviceElement : servicesElement.getChildren("service")) {
                        services.add(serviceElement.getTextNormalize());
                    }
                }
                station.setServices(services);

                stations.add(station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }
}
