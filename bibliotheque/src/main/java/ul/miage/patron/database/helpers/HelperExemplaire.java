package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.util.Arrays;

import ul.miage.patron.model.objets.Exemplaire;

public class HelperExemplaire extends Helper {

    // ***********************************************************
    // Sélectionner tous les exemplaires
    // ***********************************************************
    public ResultSet selectAllExemplaire() {
        return super.execute("SELECT * FROM Exemplaire");
    }

    // ***********************************************************
    // Sélectionner un exemplaire
    // ***********************************************************
    public ResultSet selectExemplaire(int id) {
        return super.execute("SELECT * FROM Exemplaire WHERE id = ?", Arrays.asList(id));
    }

    // ***********************************************************
    // Insérer un exemplaire
    // ***********************************************************
    public void insertExemplaire(Exemplaire exemplaire) {
        super.executeUpdate("INSERT INTO exemplaire (etat, disponible, oeuvre) VALUES (?, ?, ?)",
                Arrays.asList(
                        exemplaire.getEtat().toString(),
                        Boolean.toString(exemplaire.isDisponible()),
                        exemplaire.getOeuvre().getTitre()));
    }

    // ***********************************************************
    // Modifier un exemplaire
    // ***********************************************************
    public void updateExemplaire(Exemplaire exemplaire) {
        super.executeUpdate("UPDATE exemplaire SET etat = ?, disponible = ?, oeuvre = ? WHERE id = ?",
                Arrays.asList(
                        exemplaire.getEtat().toString(),
                        Boolean.toString(exemplaire.isDisponible()),
                        exemplaire.getOeuvre().getTitre(),
                        exemplaire.getId()));
    }
}
