package ul.miage.patron.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.Usager;
import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.enumerations.EtatExemplaire;

public class ControllerFront {
    @FXML
    Button btnAdd;

    @FXML
    ListView<Usager> listViewUsager;

    ObservableList<Usager> usagers = FXCollections.observableArrayList();

    public void initialize() {

    }

    // ***********************************************************
    // Réserver oeuvre
    // ***********************************************************
    public void reserverOeuvre(String email, String titre) {

    }

    // ***********************************************************
    // Annuler une réservation
    // ***********************************************************
    public void annulerReservation(String email, String titre) {

    }

    // ***********************************************************
    // Emprunter un exemplaire
    // ***********************************************************
    public void emprunterExemplaire(String email, String titre) {

    }

    // ***********************************************************
    // Rendre un exemplaire
    // ***********************************************************
    public void rendreExemplaire(String email, String titre, EtatExemplaire etat) {

    }

}
