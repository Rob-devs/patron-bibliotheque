package ul.miage.patron.controller.emprunts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import ul.miage.patron.controller.exemplaires.ControllerExemplaire;
import ul.miage.patron.controller.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Usager;

public class ControllerEmprunt {
    @FXML
    ListView<Emprunt> listViewEmprunt = new ListView<Emprunt>();

    @FXML
    Label lblFullName, lblOeuvre, lblDateDebut, lblDateRendu, lblDateRenduReelle;

    @FXML
    Button btnAddEmprunt, btnRendre;

    ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

    Emprunt selectedEmprunt = null;

    @FXML
    public void initialize(){
        getAllEmprunt();
        listViewEmprunt.setItems(emprunts);
        listViewEmprunt.setCellFactory(lv -> new ListCell<Emprunt>() {
            @Override
            public void updateItem(Emprunt item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getUsager().getNom() + " " + item.getUsager().getPrenom() + " - " + item.getExemplaire().getOeuvre().getTitre();
                    setText(text);
                }
            }
        });

        // Ajouter un écouteur d'événements à la ListView
        listViewEmprunt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedEmprunt = newValue;
            if (selectedEmprunt != null){
                displayDetails(); // Appeler la méthode displayDetails lorsque la sélection change
                if (selectedEmprunt.getEtat() == EtatEmprunt.EN_COURS) {
                    btnRendre.setDisable(false);
                } else {
                    btnRendre.setDisable(true);
                }
            }
        });
    }

    public void displayDetails(){
        Emprunt emprunt = listViewEmprunt.getSelectionModel().getSelectedItem();
        if(emprunt != null) {
            lblFullName.setVisible(true);
            lblFullName.setText(emprunt.getUsager().getNom() + " " + emprunt.getUsager().getPrenom());

            lblOeuvre.setVisible(true);
            lblOeuvre.setText(emprunt.getExemplaire().getOeuvre().getTitre());

            lblDateDebut.setVisible(true);
            lblDateDebut.setText("Date de début: " + emprunt.getDateDebut());

            lblDateRendu.setVisible(true);
            lblDateRendu.setText("Date de rendu: " + emprunt.getDateRendu());

            lblDateRenduReelle.setVisible(true);
            lblDateRenduReelle.setText("Date de rendu réelle: " + emprunt.getDateRenduReelle());
        }
    }

    public void reloadListView(){
        getAllEmprunt();
        listViewEmprunt.setItems(emprunts);
    }


    // ***********************************************************
    // Emprunts
    // ***********************************************************

    // Sélectionner tous les emprunts
    public void getAllEmprunt(){
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        emprunts.clear();

        ResultSet resultSet = helperEmprunt.selectAllEmprunt();
        try{
            while(resultSet.next()){
                int id = resultSet.getInt("id"); 
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"), formatter);
                LocalDate dateRendu = LocalDate.parse(resultSet.getString("dateRendu"), formatter);

                LocalDate dateRenduReelle = null;
                if(resultSet.getString("dateRenduReelle") != null){
                    dateRenduReelle = LocalDate.parse(resultSet.getString("dateRenduReelle"), formatter);
                } 
                
                EtatEmprunt etat = EtatEmprunt.valueOf(resultSet.getString("etat"));
                
                ControllerExemplaire controllerExemplaire = new ControllerExemplaire();
                Exemplaire exemplaire = controllerExemplaire.selectExemplaire(resultSet.getInt("exemplaire"));

                ControllerUsager controllerUsager = new ControllerUsager();
                Usager usager = controllerUsager.selectUsager(resultSet.getString("usager"));

                Emprunt emprunt = null;
                if(dateRenduReelle == null){
                    emprunt = new Emprunt(id, dateDebut, dateRendu, exemplaire, usager);
                } else {
                    emprunt = new Emprunt(id, dateDebut, dateRendu, dateRenduReelle, etat, exemplaire, usager);
                }
                emprunts.add(emprunt);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Emprunt selectEmprunt(int id){
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        ResultSet resultSet = helperEmprunt.selectEmprunt(id);
        Emprunt emprunt = null;
        try{
            while(resultSet.next()){
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"));
                LocalDate dateRendu = LocalDate.parse(resultSet.getString("dateRendu"));
                LocalDate dateRenduReelle = LocalDate.parse(resultSet.getString("dateRenduReelle"));
                EtatEmprunt etat = EtatEmprunt.valueOf(resultSet.getString("etat"));
                
                ControllerExemplaire controllerExemplaire = new ControllerExemplaire();
                Exemplaire exemplaire = controllerExemplaire.selectExemplaire(resultSet.getInt("exemplaire"));

                ControllerUsager controllerUsager = new ControllerUsager();
                Usager usager = controllerUsager.selectUsager(resultSet.getString("usager"));

                emprunt = new Emprunt(id, dateDebut, dateRendu, dateRenduReelle, etat, exemplaire, usager);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return emprunt;
    }

    // Ouvrir popup pour ajouter un emprunt
    public void openPopupAddEmprunt(){
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/AjouterEmprunt.fxml"));
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

    public void openPopupRendre(){
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/Rendre.fxml"));
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

    public void resetLabels(){
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
            App.switchScene("MenuOeuvre");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenuUsager(){
        try{
            App.switchScene("MenuBack");
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


