<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Carte avec Leaflet et coordonnées Lambert Conique Conforme</title>
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
<style>
    #map {
        height: 400px;
        width: 100%;
    }
</style>
</head>
<body>
<div id="map"></div>

<script>
    // Créer la carte Leaflet
    var map = L.map('map').setView([46.2276, 2.2137], 6); // Coordonnées centrées sur la France

    // Ajouter les tuiles de la carte
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);

    // Ajouter un marqueur à vos coordonnées spécifiques
    var marker = L.marker([46.68832, 4.84479]).addTo(map);
    marker.bindPopup("<b>Marqueur</b><br>Coordonnées LCC (EPSG:2154)");

</script>
</body>
</html>
