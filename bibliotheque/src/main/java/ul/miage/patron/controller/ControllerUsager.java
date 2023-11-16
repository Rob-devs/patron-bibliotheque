package ul.miage.patron.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.Usager;

public class ControllerUsager {
    @FXML
    Button btnAdd;

    @FXML
    ListView<Usager> listViewUsager;

    ObservableList<Usager> usagers = FXCollections.observableArrayList();

    public void initialize(){
        getAllUsager();
        listViewUsager.setItems(usagers);
    }

    public void getAllUsager(){
        HelperUsager helperUsager = new HelperUsager();
        ResultSet resultSet = helperUsager.selectAllUsager();
        try {
            while(resultSet.next()){
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

    public void insertUsager(){
        Usager usager = new Usager(0, "email", "nom", "prenom", 0);
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.insertUsager(usager);
    }

    public void updateUsager(Usager usager){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager);
    }

    public void deleteUsager(Usager usager){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.deleteUsager(usager);
    }

    public void selectUsager(){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.selectUsager();
    }

    public void selectAllUsager(){
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.selectAllUsager();
    }
}
