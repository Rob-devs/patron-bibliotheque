package ul.miage.patron.controller.oeuvres;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ul.miage.patron.controller.exemplaires.ControllerAddExemplaire;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.model.enumerations.EtatExemplaire;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;

public class ControllerOeuvre {
    @FXML
    ListView<Oeuvre> listViewOeuvre = new ListView<Oeuvre>();

    @FXML
    Label lblTitle, lblAuteur, lblDate, lblExemplairesDispos, lblExemplairesTotal;

    ObservableList<Oeuvre> oeuvres = FXCollections.observableArrayList();

    Oeuvre selectedOeuvre = null;

    List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();

    public void initialize() {
        getAllOeuvre();
        listViewOeuvre.setItems(oeuvres);
        listViewOeuvre.setCellFactory(lv -> new ListCell<Oeuvre>() {
            @Override
            public void updateItem(Oeuvre item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getTitre();
                    setText(text);
                }
            }
        });

        // Ajouter un écouteur d'événements à la ListView
        listViewOeuvre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedOeuvre = newValue;
            displayDetails(); // Appeler la méthode displayDetails lorsque la sélection change
        });
    }

    public void displayDetails() {
        Oeuvre oeuvre = listViewOeuvre.getSelectionModel().getSelectedItem();
        if (oeuvre != null) {
            lblTitle.setVisible(true);
            lblTitle.setText(oeuvre.getTitre());

            lblAuteur.setVisible(true);
            lblAuteur.setText("Auteur: " + oeuvre.getAuteur());

            lblDate.setVisible(true);
            lblDate.setText("Date: " + oeuvre.getDatePublication());

            getAllExemplaires(oeuvre);

            lblExemplairesDispos.setVisible(true);
            lblExemplairesDispos.setText("Exemplaires disponibles: " + exemplaires.size());

            lblExemplairesTotal.setVisible(true);
            lblExemplairesTotal
                    .setText("Exemplaires totaux: " + exemplaires.stream().filter(e -> e.isDisponible()).count());
        }
    }

    public void reloadListView() {
        getAllOeuvre();
        listViewOeuvre.setItems(oeuvres);
    }

    // ***********************************************************
    // Oeuvres
    // ***********************************************************

    // Sélectionner toutes les oeuvres
    public void getAllOeuvre() {
        HelperOeuvre helperOeuvre = new HelperOeuvre();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        oeuvres.clear();

        ResultSet resultSet = helperOeuvre.selectAllOeuvre();
        try {
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                LocalDate datePublication = LocalDate.parse(resultSet.getString("datePublication"), formatter);
                GenreOeuvre genreOeuvre = GenreOeuvre.valueOf(resultSet.getString("genre"));
                Oeuvre oeuvre = new Oeuvre(titre, auteur, datePublication, genreOeuvre);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sélectionner tous les exemplaires de l'oeuvre
    public void getAllExemplaires(Oeuvre oeuvre) {

        HelperExemplaire helper = new HelperExemplaire();
        ResultSet resultSet = helper.selectAllExemplaire();

        // Vider la liste avant de la remplir
        exemplaires.clear();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String etat = resultSet.getString("etat");
                boolean disponible = resultSet.getString("disponible").toLowerCase() == "true";
                String titre = resultSet.getString("oeuvre");
                if (titre.equals(oeuvre.getTitre())) {
                    Exemplaire e = new Exemplaire(id, EtatExemplaire.valueOf(etat), oeuvre);
                    e.setDisponible(disponible);
                    exemplaires.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sélectionner une oeuvre
    public Oeuvre selectOeuvre(String titre) {
        HelperOeuvre helperOeuvre = new HelperOeuvre();
        ResultSet resultSet = helperOeuvre.selectOeuvre(titre);
        Oeuvre oeuvre = null;
        try {
            while (resultSet.next()) {
                String auteur = resultSet.getString("auteur");
                LocalDate datePublication = resultSet.getDate("datePublication").toLocalDate();
                GenreOeuvre genreOeuvre = GenreOeuvre.valueOf(resultSet.getString("genre"));
                oeuvre = new Oeuvre(titre, auteur, datePublication, genreOeuvre);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oeuvre;
    }

    // Ouvrir popup pour ajouter une oeuvre
    public void openPopupAddOeuvre() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/AjouterOeuvre.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Ajouter oeuvre");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewOeuvre.getScene().getWindow();
            ControllerAddOeuvre controllerAddOeuvre = loader.getController();
            controllerAddOeuvre.setPopupStage(popupStage);
            controllerAddOeuvre.setParentStage(mainStage);
            controllerAddOeuvre.fillCbGenreOeuvre();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPopupaddExemplaire() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/AjouterExemplaire.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Ajouter exemplaire");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewOeuvre.getScene().getWindow();
            ControllerAddExemplaire controllerAddExemplaire = loader.getController();
            controllerAddExemplaire.setPopupStage(popupStage);
            controllerAddExemplaire.setParentStage(mainStage);
            controllerAddExemplaire.fillCbEtatExemplaire();
            controllerAddExemplaire.setCurrentOeuvre(selectedOeuvre);
            controllerAddExemplaire.fillInfos();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetLabels() {
        lblTitle.setVisible(false);
        lblAuteur.setVisible(false);
        lblDate.setVisible(false);
    }

    // ***********************************************************
    // Navigation
    // ***********************************************************
    public void openMenuUsager() {
        try {
            // Charger le fichier FXML de la fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/MenuBack.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setTitle("Menu usager");
            newStage.setScene(scene);
            newStage.show();

            Stage mainStage = (Stage) listViewOeuvre.getScene().getWindow();
            mainStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
