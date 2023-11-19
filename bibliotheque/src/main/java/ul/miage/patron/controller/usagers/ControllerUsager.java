package ul.miage.patron.controller.usagers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ul.miage.patron.App;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.objets.Usager;
import ul.miage.patron.model.objets.Usagers;

public class ControllerUsager {
    @FXML
    ListView<Usager> listViewUsager = new ListView<Usager>();

    @FXML
    Label lblFullName, lblEmail, lblTelephone;

    ObservableList<Usager> usagers = FXCollections.observableArrayList();
    ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();


    @FXML
    Button btnUpdate, btnDelete;

    Usager selectedUsager = null;

    @FXML
    public void initialize() {
        getAllUsager();
        listViewUsager.setItems(usagers);
        listViewUsager.setCellFactory(lv -> new ListCell<Usager>() {
            @Override
            public void updateItem(Usager item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getNom() + " " + item.getPrenom();
                    setText(text);
                }
            }
        });

        // Ajouter un écouteur d'événements à la ListView
        listViewUsager.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedUsager = newValue;
            displayDetails(); // Appeler la méthode displayDetails lorsque la sélection change
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        });
    }

    public void displayDetails() {
        Usager usager = listViewUsager.getSelectionModel().getSelectedItem();
        if (usager != null) {
            lblFullName.setVisible(true);
            lblFullName.setText(usager.getNom() + " " + usager.getPrenom());

            lblEmail.setVisible(true);
            lblEmail.setText("Adresse mail: " + usager.getEmail());

            lblTelephone.setVisible(true);
            lblTelephone.setText("Téléphone: " + usager.getTelephone());
        }
    }

    public void reloadListView() {
        getAllUsager();
        listViewUsager.setItems(usagers);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    public ObservableList<Usager> getUsagers() {
        return usagers;
    }

    // ***********************************************************
    // Usagers
    // ***********************************************************

    // Sélectionner tous les usagers
    public void getAllUsager() {
        HelperUsager helperUsager = new HelperUsager();

        // Vider la liste avant de la remplir
        usagers.clear();

        ResultSet resultSet = helperUsager.selectAllUsager();
        try {
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                Usager usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
                Usagers.setUsagers(usagers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sélectionner un usager
    public Usager selectUsager(String email) {
        HelperUsager helperUsager = new HelperUsager();
        ResultSet resultSet = helperUsager.selectUsager(email);
        Usager usager = null;
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usager;
    }

    // Ouvrir popup pour ajouter un usager
    public void openPopupAddUsager() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/AjouterUsager.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Ajouter usager");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewUsager.getScene().getWindow();
            ControllerAddUsager controllerAddUsager = loader.getController();
            controllerAddUsager.setPopupStage(popupStage);
            controllerAddUsager.setParentStage(mainStage);

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPopupUpdateUsager() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/UpdateUsager.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Modifier usager");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewUsager.getScene().getWindow();
            ControllerUpdateUsager controllerUpdateUsager = loader.getController();
            controllerUpdateUsager.setPopupStage(popupStage);
            controllerUpdateUsager.setParentStage(mainStage);
            controllerUpdateUsager.setCurrentUsager(selectedUsager);
            controllerUpdateUsager.fillInfos();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            displayDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetLabels() {
        lblFullName.setText("");
        lblEmail.setText("");
        lblTelephone.setText("");
    }

    public void deleteUsager() {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Supprimer  " + selectedUsager.getNom() + " " + selectedUsager.getPrenom() + " ?", ButtonType.YES,
                ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            HelperUsager helperUsager = new HelperUsager();
            helperUsager.deleteUsager(selectedUsager);
            reloadListView();
        }
        resetLabels();
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
