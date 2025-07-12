package com.xmlproject;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;

public class XMLReader {
    public static void main(String[] args) throws Exception {
        File file = new File("/Applications/XAMPP/xamppfiles/htdocs/xmlProject/XMLproject_java/data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        doc.getDocumentElement().normalize();

        NodeList contacts = doc.getElementsByTagName("contact");
        for (int i = 0; i < contacts.getLength(); i++) {
            Element contact = (Element) contacts.item(i);
            String nom = contact.getElementsByTagName("nom").item(0).getTextContent();
            String telephone = contact.getElementsByTagName("telephone").item(0).getTextContent();
            System.out.println("Nom: " + nom + ", Téléphone: " + telephone);
        }
    }
}