<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="javaee.station.Station" %>
<!DOCTYPE html>
<html>
<head>
    <title>Carte des Stations</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster/dist/MarkerCluster.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet.markercluster/dist/MarkerCluster.Default.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        #map { height: 400px; }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1>Stations-service à proximité</h1>
        <input type="number" id="distance" class="form-control" placeholder="Entrez la distance en km" value="5">
        <button id="findStations" class="btn btn-primary mt-2">Trouver des stations</button>
        <select id="services" class="form-control mt-2">
    <option value="">Tous les services</option>
    <% 
    Set<String> allServices = (Set<String>) request.getAttribute("allServices");
    if (allServices != null) {
        for (String service : allServices) { 
    %>
            <option value="<%= service %>"><%= service %></option>
    <% 
        }
    } 
    %>
</select>
        
        
        <div id="map" class="mt-3"></div>
    </div>

    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
    <script src="https://unpkg.com/leaflet.markercluster/dist/leaflet.markercluster.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script>
    var stations = <%= (String) request.getAttribute("stationsJson") %>;
    var map = L.map('map').setView([46.2276, 2.2137], 6);
    var markers = L.markerClusterGroup();

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    var userLocation;
    if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(function(position) {
            userLocation = [position.coords.latitude, position.coords.longitude];
            L.marker(userLocation).addTo(map).bindPopup('Vous êtes ici').openPopup();
            map.setView(userLocation, 13);
        }, function(error) {
            console.error("Geolocation error: " + error.message);
        });
    }

    function findAndDisplayStations(distance, serviceFilter) {
        // Suppression de tous les marqueurs précédents
        markers.clearLayers();

        stations.forEach(function(station) {
            if (!serviceFilter || station.services.includes(serviceFilter)) {
                var stationPos = L.latLng(station.latitude, station.longitude);
                if (!userLocation || map.distance(userLocation, stationPos) <= distance * 1000) {
                    var marker = L.marker([station.latitude, station.longitude]);
                    marker.bindPopup(buildPopupContent(station));
                    markers.addLayer(marker);
                }
            }
        });

        map.addLayer(markers);
    }

    $('#findStations').click(function() {
        var distance = $('#distance').val() || 5; // Utilise 5 km par défaut si rien n'est spécifié
        var selectedService = $('#services').val();
        findAndDisplayStations(distance, selectedService);
    });

    $('#services').change(function() {
        var distance = $('#distance').val() || 5; // Utilise 5 km par défaut si rien n'est spécifié
        var selectedService = $(this).val();
        findAndDisplayStations(distance, selectedService);
    });

    function buildPopupContent(station) {
        var content = '<b>' + station.ville + '</b><br>' + station.adresse;
        content += '<br><br><b>Horaires :</b><br>';
        for (var day in station.openingHours) {
            content += day + ' : ' + station.openingHours[day] + '<br>';
        }
        if (station.services.length > 0) {
            content += '<br><b>Services :</b><br>' + station.services.join(', ') + '<br>';
        }
        content += '<br><b>Prix des carburants :</b><br>';
        for (var fuel in station.fuelPrices) {
            content += fuel + ' : ' + station.fuelPrices[fuel].toFixed(2) + '€<br>';
        }
        return content;
    }
</script>

</body>
</html>

