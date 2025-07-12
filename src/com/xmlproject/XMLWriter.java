package com.xmlproject;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class XMLWriter {
    public static void addContact(String nom, String telephone, String email, String statut) throws Exception {
        File file = new File("data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = doc.getDocumentElement();
        Element contacts = (Element) root.getElementsByTagName("contacts").item(0);

        Element newContact = doc.createElement("contact");
        contacts.appendChild(newContact);

        Element nomElement = doc.createElement("nom");
        nomElement.setTextContent(nom);
        newContact.appendChild(nomElement);

        if (telephone != null && !telephone.isEmpty()) {
            Element telElement = doc.createElement("telephone");
            telElement.setTextContent(telephone);
            newContact.appendChild(telElement);
        }
        if (email != null && !email.isEmpty()) {
            Element emailElement = doc.createElement("email");
            emailElement.setTextContent(email);
            newContact.appendChild(emailElement);
        }
        Element statutElement = doc.createElement("statut");
        statutElement.setTextContent(statut);
        newContact.appendChild(statutElement);

        saveDocument(doc, file);
    }

    public static void addGroup(String groupName, String[] memberNames) throws Exception {
        File file = new File("data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = doc.getDocumentElement();
        Element groupes = (Element) root.getElementsByTagName("groupes").item(0);

        Element newGroup = doc.createElement("groupe");
        groupes.appendChild(newGroup);

        Element nomElement = doc.createElement("nom");
        nomElement.setTextContent(groupName);
        newGroup.appendChild(nomElement);

        Element membresElement = doc.createElement("membres");
        newGroup.appendChild(membresElement);

        for (String memberName : memberNames) {
            Element member = doc.createElement("contact-ref");
            member.setTextContent(memberName.trim());
            membresElement.appendChild(member);
        }

        saveDocument(doc, file);
    }

    public static void addMessage(String expéditeur, String[] destinataires, String texte, String horodatage) throws Exception {
        File file = new File("data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = doc.getDocumentElement();
        Element discussions = (Element) root.getElementsByTagName("discussions").item(0);

        Element newMessage = doc.createElement("message");
        discussions.appendChild(newMessage);

        Element expéditeurElement = doc.createElement("expéditeur");
        expéditeurElement.setTextContent(expéditeur);
        newMessage.appendChild(expéditeurElement);

        for (String destinataire : destinataires) {
            Element destElement = doc.createElement("destinataire");
            destElement.setTextContent(destinataire.trim());
            newMessage.appendChild(destElement);
        }

        Element texteElement = doc.createElement("texte");
        texteElement.setTextContent(texte);
        newMessage.appendChild(texteElement);

        Element horodatageElement = doc.createElement("horodatage");
        horodatageElement.setTextContent(horodatage);
        newMessage.appendChild(horodatageElement);

        saveDocument(doc, file);
    }

    public static void addFile(String expéditeur, String[] destinataires, String nom, String type, String taille, String contenu, String horodatage) throws Exception {
        File file = new File("data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = doc.getDocumentElement();
        Element discussions = (Element) root.getElementsByTagName("discussions").item(0);

        Element newFile = doc.createElement("fichier");
        discussions.appendChild(newFile);

        Element expéditeurElement = doc.createElement("expéditeur");
        expéditeurElement.setTextContent(expéditeur);
        newFile.appendChild(expéditeurElement);

        for (String destinataire : destinataires) {
            Element destElement = doc.createElement("destinataire");
            destElement.setTextContent(destinataire.trim());
            newFile.appendChild(destElement);
        }

        Element nomElement = doc.createElement("nom");
        nomElement.setTextContent(nom);
        newFile.appendChild(nomElement);

        Element typeElement = doc.createElement("type");
        typeElement.setTextContent(type);
        newFile.appendChild(typeElement);

        Element tailleElement = doc.createElement("taille");
        tailleElement.setTextContent(taille);
        newFile.appendChild(tailleElement);

        Element contenuElement = doc.createElement("contenu");
        contenuElement.setTextContent(contenu);
        newFile.appendChild(contenuElement);

        Element horodatageElement = doc.createElement("horodatage");
        horodatageElement.setTextContent(horodatage);
        newFile.appendChild(horodatageElement);

        saveDocument(doc, file);
    }

    public static void updateProfile(String nom, String email, String statut, boolean notification, String theme) throws Exception {
        File file = new File("data.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = doc.getDocumentElement();
        Element profil = (Element) root.getElementsByTagName("profil").item(0);

        Element nomElement = (Element) profil.getElementsByTagName("nom").item(0);
        nomElement.setTextContent(nom);

        Element emailElement = (Element) profil.getElementsByTagName("email").item(0);
        emailElement.setTextContent(email);

        Element statutElement = (Element) profil.getElementsByTagName("statut").item(0);
        statutElement.setTextContent(statut);

        Element parametres = (Element) profil.getElementsByTagName("parametres").item(0);
        Element notificationElement = (Element) parametres.getElementsByTagName("notification").item(0);
        notificationElement.setAttribute("active", notification ? "oui" : "non");

        Element themeElement = (Element) parametres.getElementsByTagName("theme").item(0);
        themeElement.setTextContent(theme);

        saveDocument(doc, file);
    }

    private static void saveDocument(Document doc, File file) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }
}