package javaee.station.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaee.station.Station;
import javaee.station.dao.StationDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Servlet implementation class StationServlet
 */
public class StationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Les importations nécessaires
		
		        response.setContentType("text/html");
		        response.setCharacterEncoding("UTF-8");
		        String realPath = getServletContext().getRealPath("/WEB-INF/PrixCarburants_annuel_2024.xml");		
		        System.out.println("Real path of the application: " + realPath);
		        StationDao dao = new StationDao();

		        StationDao stationDao = new StationDao();
		        List<Station> stations = stationDao.getAllStations(realPath);
		        Set<String> allServices = new HashSet<>();
		        for (Station station : stations) {
		            allServices.addAll(station.getServices());
		        }
		        try (PrintWriter out = response.getWriter()) {
		            out.println("<!DOCTYPE html>");
		            out.println("<html>");
		            out.println("<head>");
		            out.println("<title>Carte des Stations</title>");
		            out.println("<link rel='stylesheet' href='https://unpkg.com/leaflet@1.7.1/dist/leaflet.css' />");
		            out.println("<link rel='stylesheet' href='https://unpkg.com/leaflet.markercluster/dist/MarkerCluster.css' />");
		            out.println("<link rel='stylesheet' href='https://unpkg.com/leaflet.markercluster/dist/MarkerCluster.Default.css' />");
		            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css'>");
		            out.println("</head>");
		            out.println("<body>");
		            out.println("<div class='container mt-4'>");
		            out.println("<h1>Stations-service à proximité</h1>");
		            out.println("<input type='number' id='distance' class='form-control' placeholder='Entrez la distance en km'>");
		            out.println("<button id='findStations' class='btn btn-primary mt-2'>Trouver des stations</button>");
		            out.println("<select id='services' class='form-control mt-2'>");
		            out.println("<option value=''>Tous les services</option>");
		            for (String service : allServices) {
		                out.println("<option value='" + service.replace("'", "\\'") + "'>" + service + "</option>");
		            }
		            out.println("</select>");
		            
		            out.println("<div id='map' style='height: 400px;' class='mt-3'></div>");
		            out.println("</div>");
		            out.println("<script src='https://unpkg.com/leaflet@1.7.1/dist/leaflet.js'></script>");
		            out.println("<script src='https://unpkg.com/leaflet.markercluster/dist/leaflet.markercluster.js'></script>");
		            out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js'></script>");
		            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js'></script>");
		            out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js'></script>");
		            out.println("<script>");

		            // Define user's location variable
		            out.println("var userLocation;");

		            // Initialize map
		            out.println("var map = L.map('map').setView([46.2276, 2.2137], 6);");
		            out.println("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
		            out.println("    maxZoom: 19,");
		            out.println("    attribution: '© OpenStreetMap contributors'");
		            out.println("}).addTo(map);");

		            // Try to get user's current position
		            out.println("if ('geolocation' in navigator) {");
		            out.println("    navigator.geolocation.getCurrentPosition(function(position) {");
		            out.println("        userLocation = [position.coords.latitude, position.coords.longitude];");
		            out.println("        L.marker(userLocation).addTo(map).bindPopup('Vous êtes ici').openPopup();");
		            out.println("        map.setView(userLocation, 13);");
		            out.println("    });");
		            out.println("}");

		            // Function to find and display stations
		            out.println("function findAndDisplayStations(distance, serviceFilter) {");
		            out.println("    map.eachLayer(function(layer) {");
		            out.println("        if (!(layer instanceof L.TileLayer)) {");
		            out.println("            map.removeLayer(layer);");
		            out.println("        }");
		            out.println("    });");
		            out.println("    var markers = L.markerClusterGroup();");
		            out.println("    stations.forEach(function(station) {");
		            out.println("        if (!serviceFilter || station.services.includes(serviceFilter)) {");
		            out.println("            var marker = L.marker([station.latitude, station.longitude]);");
		            out.println("            marker.bindPopup(buildPopupContent(station));");
		            out.println("            if (map.distance(userLocation, [station.latitude, station.longitude]) <= distance * 1000) {");
		            out.println("                markers.addLayer(marker);");
		            out.println("            }");
		            out.println("        }");
		            out.println("    });");
		            out.println("    map.addLayer(markers);");
		            out.println("}");

		            // Event listener for the 'Find Stations' button
		            out.println("$('#findStations').click(function() {");
		            out.println("    var distance = $('#distance').val() || 5;"); // Default distance is 5km
		            out.println("    var selectedService = $('#services').val();");
		            out.println("    findAndDisplayStations(distance, selectedService);");
		            out.println("});");

		            // Event listener for service selection change
		            out.println("$('#services').change(function() {");
		            out.println("    var distance = $('#distance').val() || 5;"); // Default distance is 5km
		            out.println("    var selectedService = $(this).val();");
		            out.println("    findAndDisplayStations(distance, selectedService);");
		            out.println("});");

		            // Function to build the popup content for markers
		            out.println("function buildPopupContent(station) {");
		            out.println("    var content = '<b>' + station.ville + '</b><br>' + station.adresse + '<br><br><b>Opening Hours:</b><br>';");

		            // Adding opening hours
		            out.println("    for (var day in station.openingHours) {");
		            out.println("        content += day + ': ' + station.openingHours[day] + '<br>';");
		            out.println("    }");

		            // Adding services if available
		            out.println("    if (station.services && station.services.length > 0) {");
		            out.println("        content += '<br><b>Services:</b><br>' + station.services.join(', ') + '<br>';"); // Assuming services is an array
		            out.println("    }");

		            // Adding fuel prices
		            out.println("    content += '<br><b>Fuel Prices:</b><br>';");
		            out.println("    for (var fuel in station.fuelPrices) {");
		            out.println("        content += fuel + ': ' + station.fuelPrices[fuel] + '€<br>';");
		            out.println("    }");

		            out.println("    return content;");
		            out.println("}");


		            // Initializing stations array with data
		            out.println("var stations = " + getStationsAsJson(stations) + ";");

		            out.println("</script>");
		            out.println("</body>");
		            out.println("</html>");
		        }
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


}
