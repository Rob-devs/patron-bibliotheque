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
        super.execute("INSERT INTO usager (email, nom, prenom, telephone, penalites) VALUES (?, ?, ?, ?, ?)",
                Arrays.asList(
                        usager.getEmail(),
                        usager.getNom(),
                        usager.getPrenom(),
                        usager.getTelephone(),
                        usager.getPenalites()));
    }

    // ***********************************************************
    // Modifier un usager
    // ***********************************************************
    public void updateUsager(Usager usager, String oldMail) {
        super.execute("UPDATE usager SET email = ?, nom = ?, prenom = ?, telephone = ?, penalites = ? WHERE email = ?",
                Arrays.asList(
                        usager.getEmail(),
                        usager.getNom(),
                        usager.getPrenom(),
                        usager.getTelephone(),
                        usager.getPenalites(),
                        oldMail));
    }

    // ***********************************************************
    // Supprimer un usager
    // ***********************************************************
    public void deleteUsager(Usager usager) {
        super.executeUpdate("DELETE FROM usager WHERE email = ?",
                Arrays.asList(usager.getEmail()));
    }

    // ***********************************************************
    // Incrémenter le nombre de pénalités
    // ***********************************************************
    public void incrementPenalites(Usager usager) {
        super.executeUpdate("UPDATE usager SET penalites = penalites + 1 WHERE email = ?",
                Arrays.asList(usager.getEmail()));
    }

    // ***********************************************************
    // Décrémenter le nombre de pénalités
    // ***********************************************************
    public void decrementPenalites(Usager usager) {
        super.executeUpdate("UPDATE usager SET penalites = penalites - 1 WHERE email = ?",
                Arrays.asList(usager.getEmail()));
    }

}