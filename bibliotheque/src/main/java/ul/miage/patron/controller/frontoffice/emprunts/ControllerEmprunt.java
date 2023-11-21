package ul.miage.patron.controller.frontoffice.emprunts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ul.miage.patron.App;
import ul.miage.patron.controller.backoffice.exemplaires.ControllerExemplaire;
import ul.miage.patron.controller.backoffice.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.Helper;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.enumerations.EtatExemplaire;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;
import ul.miage.patron.model.objets.Usagers;

public class ControllerEmprunt {
    @FXML
    ListView<Emprunt> listViewEmprunt = new ListView<Emprunt>();

    @FXML
    Label lblFullName, lblOeuvre, lblDateDebut, lblDateRendu, lblDateRenduReelle;

    @FXML
    Button btnAddEmprunt, btnRendre;

    ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

    Emprunt selectedEmprunt = null;

    List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
    List<Usager> usagers = new ArrayList<Usager>();
    List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();

    @FXML
    public void initialize() {
        getAllOeuvre();
        getAllExemplaire();
        getAllUsager();
        getAllEmprunt();
        listViewEmprunt.setItems(emprunts);
        listViewEmprunt.setCellFactory(lv -> new ListCell<Emprunt>() {
            @Override
            public void updateItem(Emprunt item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getUsager().getNom() + " " + item.getUsager().getPrenom() + " - "
                            + item.getExemplaire().getOeuvre().getTitre();
                    setText(text);
                }
            }
        });

        // Ajouter un écouteur d'événements à la ListView
        listViewEmprunt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedEmprunt = newValue;
            if (selectedEmprunt != null) {
                displayDetails(); // Appeler la méthode displayDetails lorsque la sélection change
                if (selectedEmprunt.getEtat() == EtatEmprunt.EN_COURS) {
                    btnRendre.setDisable(false);
                } else {
                    btnRendre.setDisable(true);
                }
            }
        });
    }

    // Sélectionner tous les usagers
    public void getAllUsager() {
        HelperUsager helperUsager = new HelperUsager();

        // Vider la liste avant de la remplir
        if (usagers != null)
            usagers.clear();

        ResultSet resultSet = helperUsager.selectAllUsager();
        try {
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                int penalites = resultSet.getInt("penalites");
                Usager usager = new Usager(email, nom, prenom, telephone, penalites);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    // Sélectionner toutes les oeuvres
    public void getAllOeuvre() {
        HelperOeuvre helperOeuvre = new HelperOeuvre();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        if (oeuvres != null)
            oeuvres.clear();

        ResultSet resultSet = helperOeuvre.selectAllOeuvre();
        try {
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                LocalDate datePublication = LocalDate.parse(resultSet.getString("datePublication"), formatter);
                GenreOeuvre genreOeuvre = GenreOeuvre.valueOf(resultSet.getString("genre"));
                int nbReservations = resultSet.getInt("nbReservations");
                Oeuvre oeuvre = new Oeuvre(titre, auteur, datePublication, genreOeuvre, nbReservations);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    public void getAllExemplaire() {
        HelperExemplaire helper = new HelperExemplaire();
        ResultSet resultSet = helper.selectAllExemplaire();

        // Vider la liste avant de la remplir
        if (exemplaires != null)
            exemplaires.clear();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String etat = resultSet.getString("etat");
                boolean disponible = resultSet.getString("disponible").toLowerCase().equals("true");
                String titre = resultSet.getString("oeuvre");
                Oeuvre oeuvre = (Oeuvre) oeuvres.stream().filter(o -> o.getTitre().equals(titre)).toArray()[0];
                Exemplaire e = new Exemplaire(id, EtatExemplaire.valueOf(etat), disponible, oeuvre);
                exemplaires.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    public void displayDetails() {
        Emprunt emprunt = listViewEmprunt.getSelectionModel().getSelectedItem();
        if (emprunt != null) {
            lblFullName.setVisible(true);
            lblFullName.setText(emprunt.getUsager().getNom() + " " + emprunt.getUsager().getPrenom());

            lblOeuvre.setVisible(true);
            lblOeuvre.setText(emprunt.getExemplaire().getOeuvre().getTitre());

            lblDateDebut.setVisible(true);
            lblDateDebut.setText("Date de début: " + emprunt.getDateDebut());

            lblDateRendu.setVisible(true);
            lblDateRendu.setText("Date de rendu: " + emprunt.getDateRendu());

            if (emprunt.getDateRenduReelle() != null) {
                lblDateRenduReelle.setVisible(true);
                lblDateRenduReelle.setText("Date de rendu réelle: " + emprunt.getDateRenduReelle());
            }

        }
    }

    public void reloadListView() {
        getAllExemplaire();
        getAllEmprunt();
        listViewEmprunt.setItems(emprunts);
    }

    // ***********************************************************
    // Emprunts
    // ***********************************************************

    // Sélectionner tous les emprunts
    public void getAllEmprunt() {
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        if (emprunts != null)
            emprunts.clear();

        ResultSet resultSet = helperEmprunt.selectAllEmprunt();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"), formatter);
                LocalDate dateRendu = LocalDate.parse(resultSet.getString("dateRendu"), formatter);

                LocalDate dateRenduReelle = null;
                if (resultSet.getString("dateRenduReelle") != null) {
                    dateRenduReelle = LocalDate.parse(resultSet.getString("dateRenduReelle"), formatter);
                }

                EtatEmprunt etat = EtatEmprunt.valueOf(resultSet.getString("etat"));
                int numExemplaire = resultSet.getInt("exemplaire");
                String emailUsager = resultSet.getString("usager");

                Exemplaire exemplaire = (Exemplaire) exemplaires.stream()
                        .filter(o -> o.getId() == numExemplaire).toArray()[0];

                Usager usager = (Usager) usagers.stream()
                        .filter(o -> o.getEmail().equals(emailUsager)).toArray()[0];

                Emprunt emprunt = null;
                if (dateRenduReelle == null) {
                    emprunt = new Emprunt(id, dateDebut, dateRendu, exemplaire, usager);
                } else {
                    emprunt = new Emprunt(id, dateDebut, dateRendu, dateRenduReelle, etat, exemplaire, usager);
                }
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    public Emprunt selectEmprunt(int id) {
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ResultSet resultSet = helperEmprunt.selectEmprunt(id);
        Emprunt emprunt = null;
        try {
            while (resultSet.next()) {
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"));
                LocalDate dateRendu = LocalDate.parse(resultSet.getString("dateRendu"));
                LocalDate dateRenduReelle = null;
                if (resultSet.getString("dateRenduReelle") != null) {
                    dateRenduReelle = LocalDate.parse(resultSet.getString("dateRenduReelle"), formatter);
                }
                EtatEmprunt etat = EtatEmprunt.valueOf(resultSet.getString("etat"));

                int numExemplaire = resultSet.getInt("exemplaire");
                String emailUsager = resultSet.getString("usager");

                Exemplaire exemplaire = (Exemplaire) exemplaires.stream()
                        .filter(o -> o.getId() == numExemplaire).toArray()[0];

                Usager usager = (Usager) usagers.stream()
                        .filter(o -> o.getEmail().equals(emailUsager)).toArray()[0];

                if (dateRenduReelle == null) {
                    emprunt = new Emprunt(id, dateDebut, dateRendu, exemplaire, usager);
                } else {
                    emprunt = new Emprunt(id, dateDebut, dateRendu, dateRenduReelle, etat, exemplaire, usager);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        return emprunt;
    }

    // Ouvrir popup pour ajouter un emprunt
    public void openPopupAddEmprunt() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/frontoffice/AjouterEmprunt.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Ajouter emprunt");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewEmprunt.getScene().getWindow();
            ControllerAddEmprunt controllerAddEmprunt = loader.getController();
            controllerAddEmprunt.setPopupStage(popupStage);
            controllerAddEmprunt.setParentStage(mainStage);
            controllerAddEmprunt.fillCbUsager();
            controllerAddEmprunt.fillCbOeuvre();
            controllerAddEmprunt.fillCbExemplaire();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPopupRendre() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/frontoffice/Rendre.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Rendre l'exemplaire");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewEmprunt.getScene().getWindow();
            ControllerRendre controllerRendre = loader.getController();
            controllerRendre.setPopupStage(popupStage);
            controllerRendre.setParentStage(mainStage);
            controllerRendre.setSelectedEmprunt(selectedEmprunt);
            controllerRendre.fillCbEtatExemplaire();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetLabels() {
        lblFullName.setVisible(false);
        lblOeuvre.setVisible(false);
        lblDateDebut.setVisible(false);
        lblDateRendu.setVisible(false);
        lblDateRenduReelle.setVisible(false);
    }

    // ***********************************************************
    // Navigation
    // ***********************************************************
    @FXML
    public void openMenuOeuvre() {
        try {
            App.switchScene("backoffice/MenuOeuvre");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenuUsager() {
        try {
            App.switchScene("backoffice/MenuBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenuReservation() {
        try {
            App.switchScene("frontoffice/MenuReservation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
