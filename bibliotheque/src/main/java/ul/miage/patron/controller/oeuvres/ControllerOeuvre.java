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
import javafx.scene.control.Button;
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
import ul.miage.patron.App;

public class ControllerOeuvre {
    @FXML
    ListView<Oeuvre> listViewOeuvre = new ListView<Oeuvre>();

    @FXML
    Label lblTitle, lblAuteur, lblDate, lblExemplairesDispos, lblExemplairesTotal;

    @FXML
    Button btnAddExemplaire;

    ObservableList<Oeuvre> oeuvres = FXCollections.observableArrayList();

    Oeuvre selectedOeuvre = null;

    List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
    List<Exemplaire> exemplairesDisponibles = new ArrayList<Exemplaire>();

    @FXML
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
            btnAddExemplaire.setDisable(false);
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
            lblExemplairesDispos
                    .setText("Exemplaires disponibles: "
                            + exemplaires.stream().filter(e -> e.isDisponible()).toList().size());

            lblExemplairesTotal.setVisible(true);
            lblExemplairesTotal
                    .setText("Exemplaires totaux: " + exemplaires.size());
        }
    }

    public void reloadListView() {
        getAllOeuvre();
        listViewOeuvre.setItems(oeuvres);
        btnAddExemplaire.setDisable(true);
    }

    public ObservableList<Oeuvre> getOeuvres() {
        return oeuvres;
    }

    public ObservableList<Exemplaire> getExemplaires(){
        return FXCollections.observableArrayList(exemplaires);
    }

    public ObservableList<Exemplaire> getExemplairesDisponibles(){
        return FXCollections.observableArrayList(exemplairesDisponibles);
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
        exemplairesDisponibles.clear();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String etat = resultSet.getString("etat");
                boolean disponible = resultSet.getString("disponible").toLowerCase().equals("true");
                String titre = resultSet.getString("oeuvre");
                if (titre.equals(oeuvre.getTitre())) {
                    Exemplaire e = new Exemplaire(id, EtatExemplaire.valueOf(etat), disponible, oeuvre);
                    exemplaires.add(e);
                    if (e.isDisponible()) {
                        exemplairesDisponibles.add(e);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sélectionner une oeuvre
    public Oeuvre selectOeuvre(String titre) {
        HelperOeuvre helperOeuvre = new HelperOeuvre();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ResultSet resultSet = helperOeuvre.selectOeuvre(titre);
        Oeuvre oeuvre = null;
        try {
            while (resultSet.next()) {
                String auteur = resultSet.getString("auteur");
                LocalDate datePublication = LocalDate.parse(resultSet.getString("datePublication"), formatter);
                GenreOeuvre genreOeuvre = GenreOeuvre.valueOf(resultSet.getString("genre"));
                oeuvre = new Oeuvre(titre, auteur, datePublication, genreOeuvre);
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
            displayDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetLabels() {
        lblTitle.setVisible(false);
        lblAuteur.setVisible(false);
        lblDate.setVisible(false);
        lblExemplairesDispos.setVisible(false);
        lblExemplairesTotal.setVisible(false);
    }

    // ***********************************************************
    // Navigation
    // ***********************************************************
    @FXML
    public void openMenuUsager() {
        try {
            App.switchScene("MenuBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenuEmprunt(){
        try{
            App.switchScene("MenuEmprunt");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML public void openMenuReservation(){
        try{
            App.switchScene("MenuReservation");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
