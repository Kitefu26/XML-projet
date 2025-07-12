package com.xmlproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File; // Import ajouté pour la classe File

public class SimpleInterface extends JFrame {
    private JPanel chatPanel;
    private List<String> messages = new ArrayList<>();
    private DefaultListModel<String> contactListModel = new DefaultListModel<>();
    private DefaultListModel<String> groupListModel = new DefaultListModel<>();

    public SimpleInterface() {
        setTitle("WhatsApp Clone");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon mainIcon = new ImageIcon("images/whatsapp-logo.png");
Image mainImg = mainIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
setIconImage(mainImg);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(245, 245, 245));
        tabbedPane.setForeground(new Color(0, 128, 0));

        JPanel profilePanel = createProfilePanel();
        ImageIcon profilIcon = new ImageIcon("images/contact-icon.png");
Image profilImg = profilIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
profilIcon = new ImageIcon(profilImg);
tabbedPane.addTab("Profil", profilIcon, profilePanel, "Gérer le profil");

        JPanel contactPanel = createContactPanel();
        ImageIcon contactIcon = new ImageIcon("images/contact-icon.png");
Image contactImg = contactIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
contactIcon = new ImageIcon(contactImg);
tabbedPane.addTab("Contacts", contactIcon, contactPanel, "Gérer les contacts");

        JPanel groupPanel = createGroupPanel();
        ImageIcon groupIcon = new ImageIcon("images/group-icon.png");
Image groupImg = groupIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
groupIcon = new ImageIcon(groupImg);
tabbedPane.addTab("Groupes", groupIcon, groupPanel, "Gérer les groupes");

        JPanel messagePanel = createMessagePanel();
        ImageIcon messageIcon = new ImageIcon("images/message-icon.png");
Image messageImg = messageIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
messageIcon = new ImageIcon(messageImg);
tabbedPane.addTab("Messages", messageIcon, messagePanel, "Envoyer et voir les messages");

        loadExistingData();

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomLabel = new JLabel("Nom :"); styleLabel(nomLabel);
        JTextField nomField = new JTextField(15); styleTextField(nomField);
        JLabel emailLabel = new JLabel("Email :"); styleLabel(emailLabel);
        JTextField emailField = new JTextField(15); styleTextField(emailField);
        JLabel statutLabel = new JLabel("Statut :"); styleLabel(statutLabel);
        JTextField statutField = new JTextField(15); styleTextField(statutField);
        JLabel notificationLabel = new JLabel("Notification :"); styleLabel(notificationLabel);
        JCheckBox notificationCheck = new JCheckBox();
        JLabel themeLabel = new JLabel("Thème :"); styleLabel(themeLabel);
        String[] themes = {"clair", "sombre"};
        JComboBox<String> themeCombo = new JComboBox<>(themes);
        JButton saveButton = new JButton("Sauvegarder"); styleButton(saveButton);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(nomLabel, gbc);
        gbc.gridx = 1; panel.add(nomField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(emailLabel, gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(statutLabel, gbc);
        gbc.gridx = 1; panel.add(statutField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(notificationLabel, gbc);
        gbc.gridx = 1; panel.add(notificationCheck, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(themeLabel, gbc);
        gbc.gridx = 1; panel.add(themeCombo, gbc);
        gbc.gridx = 1; gbc.gridy = 5; panel.add(saveButton, gbc);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String email = emailField.getText();
                String statut = statutField.getText();
                boolean notification = notificationCheck.isSelected();
                String theme = (String) themeCombo.getSelectedItem();
                if (!nom.isEmpty() && !email.isEmpty() && !statut.isEmpty()) {
                    try {
                        XMLWriter.updateProfile(nom, email, statut, notification, theme);
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Profil mis à jour !");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SimpleInterface.this, "Remplissez tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private JPanel createContactPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));

        JList<String> contactList = new JList<>(contactListModel);
        contactList.setBackground(new Color(255, 255, 255));
        JScrollPane listScrollPane = new JScrollPane(contactList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomLabel = new JLabel("Nom :"); styleLabel(nomLabel);
        JTextField nomField = new JTextField(15); styleTextField(nomField);
        JLabel telephoneLabel = new JLabel("Téléphone :"); styleLabel(telephoneLabel);
        JTextField telephoneField = new JTextField(15); styleTextField(telephoneField);
        JLabel emailLabel = new JLabel("Email :"); styleLabel(emailLabel);
        JTextField emailField = new JTextField(15); styleTextField(emailField);
        JLabel statutLabel = new JLabel("Statut :"); styleLabel(statutLabel);
        JTextField statutField = new JTextField(15); styleTextField(statutField);
        JButton addButton = new JButton("Ajouter"); styleButton(addButton);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(nomLabel, gbc);
        gbc.gridx = 1; formPanel.add(nomField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(telephoneLabel, gbc);
        gbc.gridx = 1; formPanel.add(telephoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(statutLabel, gbc);
        gbc.gridx = 1; formPanel.add(statutField, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(addButton, gbc);

        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String telephone = telephoneField.getText();
                String email = emailField.getText();
                String statut = statutField.getText();
                if (!nom.isEmpty() && !statut.isEmpty()) {
                    try {
                        XMLWriter.addContact(nom, telephone, email, statut);
                        contactListModel.addElement(nom + " - " + (telephone.isEmpty() ? "" : telephone + " - ") + (email.isEmpty() ? "" : email + " - ") + statut);
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Contact ajouté !");
                        nomField.setText(""); telephoneField.setText(""); emailField.setText(""); statutField.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SimpleInterface.this, "Remplissez au moins nom et statut.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private JPanel createGroupPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));

        JList<String> groupList = new JList<>(groupListModel);
        groupList.setBackground(new Color(255, 255, 255));
        JScrollPane listScrollPane = new JScrollPane(groupList);
        listScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel groupNameLabel = new JLabel("Nom du Groupe :"); styleLabel(groupNameLabel);
        JTextField groupNameField = new JTextField(15); styleTextField(groupNameField);
        JLabel membersLabel = new JLabel("Membres (virgule) :"); styleLabel(membersLabel);
        JTextField membersField = new JTextField(15); styleTextField(membersField);
        JButton addButton = new JButton("Ajouter"); styleButton(addButton);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(groupNameLabel, gbc);
        gbc.gridx = 1; formPanel.add(groupNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(membersLabel, gbc);
        gbc.gridx = 1; formPanel.add(membersField, gbc);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(addButton, gbc);

        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupNameField.getText();
                String membersInput = membersField.getText();
                if (!groupName.isEmpty() && !membersInput.isEmpty()) {
                    String[] members = membersInput.split(",");
                    for (int i = 0; i < members.length; i++) members[i] = members[i].trim();
                    try {
                        XMLWriter.addGroup(groupName, members);
                        groupListModel.addElement(groupName + " - Membres : " + String.join(", ", members));
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Groupe ajouté !");
                        groupNameField.setText(""); membersField.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SimpleInterface.this, "Remplissez tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private JPanel createMessagePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel expéditeurLabel = new JLabel("Expéditeur :"); styleLabel(expéditeurLabel);
        JTextField expéditeurField = new JTextField(15); styleTextField(expéditeurField);
        JLabel destinatairesLabel = new JLabel("Destinataires (virgule) :"); styleLabel(destinatairesLabel);
        JTextField destinatairesField = new JTextField(15); styleTextField(destinatairesField);
        JLabel texteLabel = new JLabel("Message :"); styleLabel(texteLabel);
        JTextField texteField = new JTextField(15); styleTextField(texteField);
        JLabel horodatageLabel = new JLabel("Horodatage :"); styleLabel(horodatageLabel);
        JTextField horodatageField = new JTextField(15); styleTextField(horodatageField);
        JButton sendMessageButton = new JButton("Envoyer Message"); styleButton(sendMessageButton);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(expéditeurLabel, gbc);
        gbc.gridx = 1; formPanel.add(expéditeurField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(destinatairesLabel, gbc);
        gbc.gridx = 1; formPanel.add(destinatairesField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(texteLabel, gbc);
        gbc.gridx = 1; formPanel.add(texteField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(horodatageLabel, gbc);
        gbc.gridx = 1; formPanel.add(horodatageField, gbc);
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(sendMessageButton, gbc);

        JLabel fileNomLabel = new JLabel("Nom Fichier :"); styleLabel(fileNomLabel);
        JTextField fileNomField = new JTextField(15); styleTextField(fileNomField);
        JLabel fileTypeLabel = new JLabel("Type :"); styleLabel(fileTypeLabel);
        JTextField fileTypeField = new JTextField(15); styleTextField(fileTypeField);
        JLabel fileTailleLabel = new JLabel("Taille (Ko) :"); styleLabel(fileTailleLabel);
        JTextField fileTailleField = new JTextField(15); styleTextField(fileTailleField);
        JLabel fileContenuLabel = new JLabel("Contenu :"); styleLabel(fileContenuLabel);
        JTextField fileContenuField = new JTextField(15); styleTextField(fileContenuField);
        JButton sendFileButton = new JButton("Envoyer Fichier"); styleButton(sendFileButton);

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(fileNomLabel, gbc);
        gbc.gridx = 1; formPanel.add(fileNomField, gbc);
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(fileTypeLabel, gbc);
        gbc.gridx = 1; formPanel.add(fileTypeField, gbc);
        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(fileTailleLabel, gbc);
        gbc.gridx = 1; formPanel.add(fileTailleField, gbc);
        gbc.gridx = 0; gbc.gridy = 8; formPanel.add(fileContenuLabel, gbc);
        gbc.gridx = 1; formPanel.add(fileContenuField, gbc);
        gbc.gridx = 1; gbc.gridy = 9; formPanel.add(sendFileButton, gbc);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expéditeur = expéditeurField.getText();
                String[] destinataires = destinatairesField.getText().split(",");
                String texte = texteField.getText();
                String horodatage = horodatageField.getText().isEmpty() ?
                        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) : horodatageField.getText();
                if (!expéditeur.isEmpty() && !texte.isEmpty()) {
                    try {
                        XMLWriter.addMessage(expéditeur, destinataires, texte, horodatage);
                        messages.add(expéditeur + " (" + horodatage + "): " + texte);
                        addMessageBubble(expéditeur, texte, horodatage);
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Message envoyé !");
                        expéditeurField.setText(""); destinatairesField.setText(""); texteField.setText(""); horodatageField.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SimpleInterface.this, "Remplissez au moins expéditeur et message.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expéditeur = expéditeurField.getText();
                String[] destinataires = destinatairesField.getText().split(",");
                String nom = fileNomField.getText();
                String type = fileTypeField.getText();
                String taille = fileTailleField.getText();
                String contenu = fileContenuField.getText();
                String horodatage = horodatageField.getText().isEmpty() ?
                        new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) : horodatageField.getText();
                if (!expéditeur.isEmpty() && !nom.isEmpty() && !type.isEmpty() && !taille.isEmpty() && !contenu.isEmpty()) {
                    try {
                        XMLWriter.addFile(expéditeur, destinataires, nom, type, taille, contenu, horodatage);
                        messages.add(expéditeur + " (Fichier " + nom + " à " + horodatage + "): " + contenu.substring(0, Math.min(20, contenu.length())) + "...");
                        addMessageBubble(expéditeur, "Fichier: " + nom, horodatage);
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Fichier envoyé !");
                        fileNomField.setText(""); fileTypeField.setText(""); fileTailleField.setText(""); fileContenuField.setText("");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(SimpleInterface.this, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SimpleInterface.this, "Remplissez tous les champs du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return mainPanel;
    }

    private void styleLabel(JLabel label) { label.setForeground(new Color(0, 128, 0)); label.setFont(new Font("Arial", Font.BOLD, 12)); }
    private void styleTextField(JTextField field) { field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), BorderFactory.createEmptyBorder(5, 5, 5, 5))); field.setBackground(Color.WHITE); }
    private void styleButton(JButton button) { button.setBackground(new Color(0, 200, 0)); button.setForeground(Color.BLACK); button.setFocusPainted(false); button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); button.setCursor(new Cursor(Cursor.HAND_CURSOR)); }
    private void addMessageBubble(String expéditeur, String texte, String horodatage) {
        JPanel bubble = new JPanel(new BorderLayout(5, 5));
        bubble.setBackground(expéditeur.equals("Moi") ? new Color(0, 200, 0, 50) : new Color(220, 220, 220));
        bubble.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bubble.setMaximumSize(new Dimension(300, 100));

        JLabel messageLabel = new JLabel("<html><b>" + expéditeur + "</b> (" + horodatage + ")<br>" + texte + "</html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bubble.add(messageLabel, BorderLayout.CENTER);

        chatPanel.add(bubble);
        chatPanel.add(Box.createVerticalStrut(10));
        chatPanel.revalidate();
        chatPanel.repaint();
        scrollToBottom();
    }
    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = ((JScrollPane) chatPanel.getParent().getParent()).getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void loadExistingData() {
        try {
            File file = new File("data.xml"); // Utilisation de la classe File
            if (file.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                // Charger les contacts
                NodeList contactNodes = doc.getElementsByTagName("contact");
                for (int i = 0; i < contactNodes.getLength(); i++) {
                    Element contact = (Element) contactNodes.item(i);
                    String nom = contact.getElementsByTagName("nom").item(0).getTextContent();
                    String telephone = contact.getElementsByTagName("telephone").item(0) != null ? contact.getElementsByTagName("telephone").item(0).getTextContent() : "";
                    String email = contact.getElementsByTagName("email").item(0) != null ? contact.getElementsByTagName("email").item(0).getTextContent() : "";
                    String statut = contact.getElementsByTagName("statut").item(0).getTextContent();
                    contactListModel.addElement(nom + " - " + (telephone.isEmpty() ? "" : telephone + " - ") + (email.isEmpty() ? "" : email + " - ") + statut);
                }

                // Charger les groupes
                NodeList groupNodes = doc.getElementsByTagName("groupe");
                for (int i = 0; i < groupNodes.getLength(); i++) {
                    Element group = (Element) groupNodes.item(i);
                    String groupName = group.getElementsByTagName("nom").item(0).getTextContent();
                    NodeList memberNodes = group.getElementsByTagName("contact-ref");
                    StringBuilder members = new StringBuilder();
                    for (int j = 0; j < memberNodes.getLength(); j++) {
                        members.append(memberNodes.item(j).getTextContent()).append(j < memberNodes.getLength() - 1 ? ", " : "");
                    }
                    groupListModel.addElement(groupName + " - Membres : " + members.toString());
                }

                // Charger les messages et fichiers
                NodeList messageNodes = doc.getElementsByTagName("message");
                for (int i = 0; i < messageNodes.getLength(); i++) {
                    Element message = (Element) messageNodes.item(i);
                    String expéditeur = message.getElementsByTagName("expéditeur").item(0).getTextContent();
                    String texte = message.getElementsByTagName("texte").item(0).getTextContent();
                    String horodatage = message.getElementsByTagName("horodatage").item(0).getTextContent();
                    messages.add(expéditeur + " (" + horodatage + "): " + texte);
                    addMessageBubble(expéditeur, texte, horodatage);
                }
                NodeList fileNodes = doc.getElementsByTagName("fichier");
                for (int i = 0; i < fileNodes.getLength(); i++) {
                    Element fichierElement = (Element) fileNodes.item(i);  // Changé 'file' en 'fichierElement'
                    String expéditeur = fichierElement.getElementsByTagName("expéditeur").item(0).getTextContent();
                    String nom = fichierElement.getElementsByTagName("nom").item(0).getTextContent();
                    String horodatage = fichierElement.getElementsByTagName("horodatage").item(0).getTextContent();
                    String contenu = fichierElement.getElementsByTagName("contenu").item(0).getTextContent();
                    messages.add(expéditeur + " (Fichier " + nom + " à " + horodatage + "): " + contenu.substring(0, Math.min(20, contenu.length())) + "...");
                    addMessageBubble(expéditeur, "Fichier: " + nom, horodatage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SimpleInterface();
    }
}