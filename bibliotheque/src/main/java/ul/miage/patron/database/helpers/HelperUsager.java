package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.util.Arrays;

import ul.miage.patron.model.objets.Usager;

public class HelperUsager extends Helper {

    // ***********************************************************
    // Sélectionner tous les usagers
    // ***********************************************************
    public ResultSet selectAllUsager() {
        return super.execute("SELECT * FROM Usager");
    }

    // ***********************************************************
    // Sélectionner un usager
    // ***********************************************************
    public ResultSet selectUsager(String email) {
        return super.execute("SELECT * FROM Usager WHERE email = ?", Arrays.asList(email));
    }

    // ***********************************************************
    // Insérer un usager
    // ***********************************************************
    public void insertUsager(Usager usager) {
        super.executeUpdate("INSERT INTO usager (email, nom, prenom, telephone) VALUES (?, ?, ?, ?)",
                Arrays.asList(
                        usager.getEmail(),
                        usager.getNom(),
                        usager.getPrenom(),
                        usager.getTelephone()));
    }

    // ***********************************************************
    // Modifier un usager
    // ***********************************************************
    public void updateUsager(Usager usager, String oldMail) {
        super.executeUpdate("UPDATE usager SET email = ?, nom = ?, prenom = ?, telephone = ? WHERE email = ?",
                Arrays.asList(
                        usager.getEmail(),
                        usager.getNom(),
                        usager.getPrenom(),
                        usager.getTelephone(),
                        oldMail));
    }

    // ***********************************************************
    // Supprimer un usager
    // ***********************************************************
    public void deleteUsager(Usager usager) {
        super.executeUpdate("DELETE FROM usager WHERE email = ?",
                Arrays.asList(usager.getEmail()));
    }

}