package javaee.station.servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javaee.station.Station;
import javaee.station.dao.StationDao;

// Defines a servlet class that extends HttpServlet to handle HTTP requests
public class StationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Handles the GET request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); // Sets the response content type to HTML
        response.setCharacterEncoding("UTF-8"); // Sets the character encoding to UTF-8
        
        // Gets the file path of the XML file containing station data
        String realPath = getServletContext().getRealPath("/WEB-INF/PrixCarburants_annuel_2024.xml");
        
        // Initializes the DAO for station data
        StationDao stationDao = new StationDao();
        
        // Retrieves all stations from the XML file
        List<Station> stations = stationDao.getAllStations(realPath);
        
        // Converts station data to JSON format
        String stationsJson = getStationsAsJson(stations);
        
        // Collects all available services from the stations
        Set<String> allServices = getAllServices(stations);
        request.setAttribute("allServices", allServices); // Adds the services to the request attributes

        // Adds the JSON-formatted station data to the request attributes
        request.setAttribute("stationsJson", stationsJson);
        // Forwards the request and response to a JSP page for displaying the data
        request.getRequestDispatcher("/stationMap.jsp").forward(request, response);
    }

    // Converts a list of Station objects to a JSON-formatted string
    private String getStationsAsJson(List<Station> stations) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < stations.size(); i++) {
            Station station = stations.get(i);
            // Constructs a JSON object for each station
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

    // Converts a list of strings to a JSON array
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

    // Converts a map to a JSON object
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

    // Collects all unique services provided by the stations
    private Set<String> getAllServices(List<Station> stations) {
        Set<String> allServices = new HashSet<>();
        for (Station station : stations) {
            allServices.addAll(station.getServices());
        }
        return allServices;
    }
}
