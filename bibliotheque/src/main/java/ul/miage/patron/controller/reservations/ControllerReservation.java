package ul.miage.patron.controller.reservations;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ul.miage.patron.App;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.controller.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.Helper;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.database.helpers.HelperReservation;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.actions.Reservation;
import ul.miage.patron.model.enumerations.EtatReservation;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;

public class ControllerReservation {
    @FXML
    ListView<Reservation> listViewReservation = new ListView<Reservation>();

    @FXML
    Label lblFullName, lblOeuvre, lblDateDebut, lblDateFin, lblEtat;

    @FXML
    Button btnAddReservation, btnCancelReservation;

    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    List<Usager> usagers = new ArrayList<Usager>();
    List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();

    Reservation selectedReservation = null;

    @FXML
    public void initialize() {
        getAllOeuvre();
        getAllUsager();
        getAllReservation();
        listViewReservation.setItems(reservations);
        listViewReservation.setCellFactory(lv -> new ListCell<Reservation>() {
            @Override
            public void updateItem(Reservation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getUsager().getNom() + " " + item.getUsager().getPrenom() + " - "
                            + item.getOeuvre().getTitre();
                    setText(text);
                }
            }
        });

        // Ajouter un écouteur d'événements à la ListView
        listViewReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedReservation = newValue;
            if (selectedReservation != null) {
                displayDetails(); // Appeler la méthode displayDetails lorsque la sélection change
                if (selectedReservation.getEtat() == EtatReservation.EN_COURS) {
                    btnCancelReservation.setDisable(false);
                } else {
                    btnCancelReservation.setDisable(true);
                }
            }
        });
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
                Usager usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    public void displayDetails() {
        Reservation reservation = listViewReservation.getSelectionModel().getSelectedItem();
        if (reservation != null) {
            lblFullName.setVisible(true);
            lblFullName.setText(reservation.getUsager().getNom() + " " + reservation.getUsager().getPrenom());

            lblOeuvre.setVisible(true);
            lblOeuvre.setText(reservation.getOeuvre().getTitre());

            lblDateDebut.setVisible(true);
            lblDateDebut.setText("Date de début: " + reservation.getDateDebut());

            lblDateFin.setVisible(true);
            lblDateFin.setText("Date de fin: " + reservation.getDateFin());

            lblEtat.setVisible(true);
            lblEtat.setText("Etat de la réservation: " + reservation.getEtat());
        }
    }

    public void reloadListView() {
        getAllReservation();
        listViewReservation.setItems(reservations);
    }

    // ***********************************************************
    // Réservations
    // ***********************************************************
    // Sélectionner toutes les réservations
    public void getAllReservation() {
        HelperReservation helperReservation = new HelperReservation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        reservations.clear();

        ResultSet resultSet = helperReservation.selectAllReservation();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"), formatter);
                LocalDate dateFin = null;
                if (resultSet.getString("dateFin") != null) {
                    dateFin = LocalDate.parse(resultSet.getString("dateFin"), formatter);
                }
                EtatReservation etat = EtatReservation.valueOf(resultSet.getString("etat"));
                String titre = resultSet.getString("oeuvre");
                String emailUsager = resultSet.getString("usager");

                Oeuvre oeuvre = (Oeuvre) oeuvres.stream()
                        .filter(o -> o.getTitre().equals(titre)).toArray()[0];

                Usager usager = (Usager) usagers.stream()
                        .filter(o -> o.getEmail().equals(emailUsager)).toArray()[0];

                Reservation reservation = new Reservation(id, dateDebut, dateFin, etat, oeuvre, usager);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }
    }

    public Reservation selectReservation(int id) {
        HelperReservation helperReservation = new HelperReservation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ResultSet resultSet = helperReservation.selectReservation(id);
        Reservation reservation = null;
        try {
            while (resultSet.next()) {
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"), formatter);

                LocalDate dateFin = null;
                if (resultSet.getString("dateFin") != null) {
                    dateFin = LocalDate.parse(resultSet.getString("dateFin"), formatter);
                }

                EtatReservation etat = EtatReservation.valueOf(resultSet.getString("etat"));

                ControllerOeuvre ControllerOeuvre = new ControllerOeuvre();
                Oeuvre oeuvre = ControllerOeuvre.selectOeuvre(resultSet.getString("oeuvre"));

                ControllerUsager controllerUsager = new ControllerUsager();
                Usager usager = controllerUsager.selectUsager(resultSet.getString("usager"));

                reservation = new Reservation(id, dateDebut, dateFin, etat, oeuvre, usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        return reservation;

    }

    // Ouvrir popup pour ajouter une réservation
    public void openPopupAddEmprunt() {
        try {
            // Charger le fichier FXML de la fenêtre pop-up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/AjouterReservation.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer la fenêtre pop-up
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);// Propriétaire de la boîte de dialogue modale
            popupStage.setTitle("Ajouter réservation");
            popupStage.setScene(scene);

            // Récupérer le contrôleur de la fenêtre pop-up
            Stage mainStage = (Stage) listViewReservation.getScene().getWindow();
            ControllerAddReservation controllerAddReservation = loader.getController();
            controllerAddReservation.setPopupStage(popupStage);
            controllerAddReservation.setParentStage(mainStage);
            controllerAddReservation.fillCbUsager();
            controllerAddReservation.fillCbOeuvre();

            // Afficher la fenêtre pop-up
            popupStage.showAndWait();
            resetLabels();
            reloadListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelReservation() {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Annuler la réservation de  " + selectedReservation.getUsager().getNom() + " "
                        + selectedReservation.getUsager().getPrenom()
                        + " pour " + selectedReservation.getOeuvre().getTitre() + " ?",
                ButtonType.YES,
                ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            HelperReservation helperReservation = new HelperReservation();
            helperReservation.annulerReservation(selectedReservation);

            HelperOeuvre helperOeuvre = new HelperOeuvre();
            helperOeuvre.decrementNbReservations(selectedReservation.getOeuvre());
            reloadListView();
        }
        resetLabels();
    }

    public void resetLabels() {
        lblFullName.setVisible(false);
        lblOeuvre.setVisible(false);
        lblDateDebut.setVisible(false);
        lblDateFin.setVisible(false);
        lblEtat.setVisible(false);
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
    public void openMenuUsager() {
        try {
            App.switchScene("MenuBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openMenuEmprunt() {
        try {
            App.switchScene("MenuEmprunt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
