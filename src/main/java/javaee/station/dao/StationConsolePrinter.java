package javaee.station.dao;

import javaee.station.Station;
import javaee.station.dao.StationDao;
import java.util.List;

public class StationConsolePrinter {

    public static void main(String[] args) {
        StationDao stationDao = new StationDao();
        List<Station> stations = stationDao.getAllStations();

        for (Station station : stations) {
            System.out.println("ID: " + station.getId());
            System.out.println("Latitude: " + station.getLatitude());
            System.out.println("Longitude: " + station.getLongitude());
            System.out.println("Code postal: " + station.getCp());
            System.out.println("Ville: " + station.getVille());
            System.out.println("Adresse: " + station.getAdresse());
            // Ajoutez d'autres attributs ici si nécessaire
            System.out.println("-----------------------------------");
        }

        if (stations.isEmpty()) {
            System.out.println("Aucune station n'a été trouvée ou il y a eu une erreur lors de la lecture du fichier XML.");
        }
    }
}
