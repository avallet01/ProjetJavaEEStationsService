package javaee.stations;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        try {
            File inputFile = new File("PrixCarburants_annuel_2024.xml"); // Ensure the file name matches your XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("pdv");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Station ID : " + eElement.getAttribute("id"));
                    System.out.println("Latitude : " + eElement.getAttribute("latitude"));
                    System.out.println("Longitude : " + eElement.getAttribute("longitude"));
                    System.out.println("Postal Code : " + eElement.getAttribute("cp"));
                    System.out.println("Population : " + eElement.getAttribute("pop"));
                    System.out.println("Address : " + eElement.getElementsByTagName("adresse").item(0).getTextContent());
                    System.out.println("City : " + eElement.getElementsByTagName("ville").item(0).getTextContent());

                    // Further processing for 'horaires', 'services', 'prix', and 'rupture' can be added here
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
