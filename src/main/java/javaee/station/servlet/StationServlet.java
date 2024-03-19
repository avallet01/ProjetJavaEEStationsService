package javaee.station.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaee.station.Station;
import javaee.station.dao.StationDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

		        StationDao stationDao = new StationDao();
		        List<Station> stations = stationDao.getAllStations();

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
		            out.println("<div id='map' style='height: 400px;' class='mt-3'></div>");
		            out.println("</div>");
		            out.println("<script src='https://unpkg.com/leaflet@1.7.1/dist/leaflet.js'></script>");
		            out.println("<script src='https://unpkg.com/leaflet.markercluster/dist/leaflet.markercluster.js'></script>");
		            out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js'></script>");
		            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js'></script>");
		            out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js'></script>");
		            out.println("<script>");

		            // Script de géolocalisation et affichage des stations à proximité
		            out.println("var userLocation;");
		            out.println("var map = L.map('map').setView([46.2276, 2.2137], 6);");
		            out.println("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
		            out.println("    maxZoom: 19,");
		            out.println("    attribution: '© OpenStreetMap contributors'");
		            out.println("}).addTo(map);");
		            out.println("if ('geolocation' in navigator) {");
		            out.println("    navigator.geolocation.getCurrentPosition(function(position) {");
		            out.println("        userLocation = [position.coords.latitude, position.coords.longitude];");
		            out.println("        L.marker(userLocation).addTo(map).bindPopup('Vous êtes ici').openPopup();");
		            out.println("        map.setView(userLocation, 13);");
		            out.println("    });");
		            out.println("}");

		            // Script pour filtrer et afficher les stations à moins de n km
		            out.println("$('#findStations').click(function() {");
		            out.println("    var distance = $('#distance').val();");
		            out.println("    map.eachLayer(function(layer) {");
		            out.println("        map.removeLayer(layer);");
		            out.println("    });");
		            out.println("    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {");
		            out.println("        maxZoom: 19,");
		            out.println("        attribution: '© OpenStreetMap contributors'");
		            out.println("    }).addTo(map);");
		            out.println("    L.marker(userLocation).addTo(map).bindPopup('Vous êtes ici').openPopup();");
		            out.println("    var markers = L.markerClusterGroup();");

		            // Itérer sur la liste des stations et créer des marqueurs
		            for (Station station : stations) {
		            	String ville = station.getVille().replace("'", "\\'").replace("\"", "&quot;");
		            	String adresse = station.getAdresse().replace("'", "\\'").replace("\"", "&quot;");
		                out.println("    var marker = L.marker([" + station.getLatitudeInDegrees() + ", " + station.getLongitudeInDegrees() + "]);");
		                out.println("marker.bindPopup('<b>" + ville + "</b><br>" + adresse + "');");
		                out.println("    var stationLocation = [" + station.getLatitudeInDegrees() + ", " + station.getLongitudeInDegrees() + "];");
		                out.println("    if (map.distance(userLocation, stationLocation) <= distance * 1000) {");
		                out.println("        markers.addLayer(marker);");
		                out.println("    }");
		            }
		            out.println("    map.addLayer(markers);");
		            out.println("});");
		            out.println("function escapeHtml(unsafe) {");
		            out.println("    return unsafe");
		            out.println("         .replace(/&/g, \"&amp;\")");
		            out.println("         .replace(/</g, \"&lt;\")");
		            out.println("         .replace(/>/g, \"&gt;\")");
		            out.println("         .replace(/\"/g, \"&quot;\")");
		            out.println("         .replace(/'/g, \"&#039;\");");
		            out.println(" }");

		            out.println("</script>");
		            out.println("</body>");
		            out.println("</html>");
		        }


	}

}
