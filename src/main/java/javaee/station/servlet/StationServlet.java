package javaee.station.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javaee.station.Station;
import javaee.station.dao.StationDao;

public class StationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        // Récupération du chemin du fichier XML contenant les données des stations
        String realPath = getServletContext().getRealPath("/WEB-INF/PrixCarburants_annuel_2024.xml");
        
        // Initialisation du DAO de la station
        StationDao stationDao = new StationDao();
        
        // Récupération de toutes les stations à partir du fichier XML
        List<Station> stations = stationDao.getAllStations(realPath);
        
        // Conversion des données des stations en JSON
        String stationsJson = getStationsAsJson(stations);
        
     // Collecte de tous les services disponibles
        Set<String> allServices = getAllServices(stations);
        request.setAttribute("allServices", allServices);

        // Passage des données JSON à la JSP pour affichage
        request.setAttribute("stationsJson", stationsJson);
        request.getRequestDispatcher("/stationMap.jsp").forward(request, response);
    }

    private String getStationsAsJson(List<Station> stations) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            json.append("{")
                .append("\"latitude\":").append(station.getLatitudeInDegrees()).append(",")
                .append("\"longitude\":").append(station.getLongitudeInDegrees()).append(",")
                .append("\"ville\":\"").append(station.getVille().replace("\"", "\\\"")).append("\",")
                .append("\"adresse\":\"").append(station.getAdresse().replace("\"", "\\\"")).append("\",")
                .append("\"services\":").append(getJsonArray(station.getServices())).append(",")
                .append("\"openingHours\":").append(getJsonObject(station.getOpeningHours())).append(",")
                .append("\"fuelPrices\":").append(getJsonObject(station.getFuelPrices()))
                .append("}");

            if (i < stations.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private String getJsonArray(List<String> list) {
        StringBuilder jsonArray = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            jsonArray.append("\"").append(list.get(i).replace("\"", "\\\"")).append("\"");
            if (i < list.size() - 1) {
                jsonArray.append(",");
            }
        }
        jsonArray.append("]");
        return jsonArray.toString();
    }

    private String getJsonObject(Map<String, ?> map) {
        StringBuilder jsonObject = new StringBuilder("{");
        int i = 0;
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            jsonObject.append("\"").append(entry.getKey().replace("\"", "\\\"")).append("\":");
            if (entry.getValue() instanceof String) {
                jsonObject.append("\"").append(entry.getValue().toString().replace("\"", "\\\"")).append("\"");
            } else {
                jsonObject.append(entry.getValue());
            }
            if (i < map.size() - 1) {
                jsonObject.append(",");
            }
            i++;
        }
        jsonObject.append("}");
        return jsonObject.toString();
    }
    private Set<String> getAllServices(List<Station> stations) {
        Set<String> allServices = new HashSet<>();
        for (Station station : stations) {
            allServices.addAll(station.getServices());
        }
        return allServices;
    }

}
