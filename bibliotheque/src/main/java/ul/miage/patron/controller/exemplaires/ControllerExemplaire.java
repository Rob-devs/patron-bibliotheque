package ul.miage.patron.controller.exemplaires;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.database.helpers.Helper;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.model.enumerations.EtatExemplaire;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;

public class ControllerExemplaire {
    ObservableList<Exemplaire> exemplaires = FXCollections.observableArrayList();

    public void initialize() {

    }

    public Exemplaire selectExemplaire(int id) {
        HelperExemplaire helperExemplaire = new HelperExemplaire();
        ResultSet resultSet = helperExemplaire.selectExemplaire(id);
        Exemplaire exemplaire = null;
        try {
            while (resultSet.next()) {
                EtatExemplaire etatExemplaire = EtatExemplaire.valueOf(resultSet.getString("etat"));
                boolean disponible = resultSet.getBoolean("disponible");

                ControllerOeuvre controllerOeuvre = new ControllerOeuvre();
                Oeuvre oeuvre = controllerOeuvre.selectOeuvre(resultSet.getString("oeuvre"));
                exemplaire = new Exemplaire(id, etatExemplaire, disponible, oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        return exemplaire;
    }
}
