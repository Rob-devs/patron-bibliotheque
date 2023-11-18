package ul.miage.patron.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ul.miage.patron.App;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.Emprunt;
import ul.miage.patron.model.Exemplaire;
import ul.miage.patron.model.Oeuvre;
import ul.miage.patron.model.Usager;
import ul.miage.patron.model.Usagers;
import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.enumerations.EtatExemplaire;
import ul.miage.patron.model.enumerations.GenreOeuvre;

public class ControllerBack {
    @FXML
    Button btnAdd;

    @FXML
    ListView<Usager> listViewUsager = new ListView<Usager>();

    ObservableList<Usager> usagers = FXCollections.observableArrayList();
    ObservableList<Emprunt> emprunts = FXCollections.observableArrayList();

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
    }

    public void reloadListView() {
        getAllUsager();
        listViewUsager.setItems(usagers);
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
                int telephone = resultSet.getInt("telephone");
                Usager usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
                Usagers.setUsagers(usagers);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
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
                int telephone = resultSet.getInt("telephone");
                usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return usager;
    }

    // Modifier un usager
    public void updateUsager(Usager usager) {
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager);
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
            Stage mainStage = (Stage) btnAdd.getScene().getWindow();
            ControllerAddUsager controllerAddUsager = loader.getController();
            controllerAddUsager.setPopupStage(popupStage);
            controllerAddUsager.setParentStage(mainStage);
            controllerAddUsager.setControllerBack(this);

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();

            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ***********************************************************
    // Emprunts
    // ***********************************************************
    public void getAllEmprunts() {
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        ResultSet resultSet = helperEmprunt.selectAllEmprunt();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date dateDebut = resultSet.getDate("dateDebut");
                Date dateRendu = resultSet.getDate("dateRendu");
                Date dateRenduReelle = resultSet.getDate("dateRenduReelle");
                EtatEmprunt etat = EtatEmprunt.valueOf(resultSet.getString("etat"));

                HelperExemplaire helperExemplaire = new HelperExemplaire();
                ResultSet resultExemplaire = helperExemplaire.selectExemplaire(resultSet.getInt("exemplaire"));
                HelperOeuvre helperOeuvre = new HelperOeuvre();
                ResultSet resultOeuvre = helperOeuvre.selectOeuvre(resultExemplaire.getInt("oeuvre"));
                Oeuvre oeuvre = new Oeuvre(resultOeuvre.getString("titre"), resultOeuvre.getString("auteur"),
                        resultOeuvre.getDate("datePublication"), GenreOeuvre.valueOf(resultOeuvre.getString("genre")));
                Exemplaire exemplaire = new Exemplaire(resultExemplaire.getInt("id"),
                        EtatExemplaire.valueOf(resultExemplaire.getString("etat")),
                        resultExemplaire.getBoolean("disponible"), oeuvre);

                HelperUsager helperUsager = new HelperUsager();
                ResultSet resultUsager = helperUsager.selectUsager(resultSet.getString("usager"));
                Usager usager = new Usager(resultUsager.getString("email"), resultUsager.getString("nom"),
                        resultUsager.getString("prenom"), resultUsager.getInt("telephone"));

                Emprunt emprunt = new Emprunt(id, dateDebut, dateRendu, dateRenduReelle, etat, exemplaire, usager);
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ***********************************************************
    // Réservations
    // ***********************************************************
}
