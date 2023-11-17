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

public class ControllerBack {
    @FXML
    Button btnAdd;

    @FXML
    ListView<Usager> listViewUsager;

    ObservableList<Usager> usagers = FXCollections.observableArrayList();

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

    // ***********************************************************
    // Usagers
    // ***********************************************************

    // Sélectionner tous les usagers
    public void getAllUsager() {
        HelperUsager helperUsager = new HelperUsager();
        ResultSet resultSet = helperUsager.selectAllUsager();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int telephone = resultSet.getInt("telephone");
                Usager usager = new Usager(id, email, nom, prenom, telephone);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Sélectionner un usager
    public void selectUsager() {
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.selectUsager();
    }

    // Insérer un usager
    public void insertUsager() {
        Usager usager = new Usager(0, "email", "nom", "prenom", 0);
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.insertUsager(usager);
    }

    // Modifier un usager
    public void updateUsager(Usager usager) {
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager);
    }
}
