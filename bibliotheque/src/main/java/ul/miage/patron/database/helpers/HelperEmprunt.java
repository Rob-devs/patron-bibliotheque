package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;

public class HelperEmprunt extends Helper {

    // ***********************************************************
    // Rendre un exemplaire
    // ***********************************************************
    public void rendreExemplaire(Emprunt emprunt) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateRenduReelle = dateFormat.format(currentDate);

        super.executeUpdate("UPDATE emprunt SET dateRenduReelle = ?, etat = ? WHERE id = ?",
                Arrays.asList(
                        dateRenduReelle,
                        EtatEmprunt.TERMINE.toString(),
                        emprunt.getId()));
    }

    // ***********************************************************
    // Sélectionner tous les emprunts
    // ***********************************************************
    public ResultSet selectAllEmprunt() {
        return super.execute("SELECT * FROM Emprunt");
    }

    // ***********************************************************
    // Sélectionner un emprunt
    // ***********************************************************
    public ResultSet selectEmprunt(int id) {
        return super.execute("SELECT * FROM Emprunt WHERE id = ?", Arrays.asList(id));
    }

    // ***********************************************************
    // Insérer un emprunt
    // ***********************************************************
    public void insertEmprunt(Emprunt emprunt) {
        super.executeUpdate(
                "INSERT INTO emprunt (dateDebut, dateRendu, exemplaire, usager) VALUES (?, ?, ?, ?)",
                Arrays.asList(
                        emprunt.getDateDebut().toString(),
                        emprunt.getDateRendu().toString(),
                        emprunt.getExemplaire().getId(),
                        emprunt.getUsager().getEmail()));
    }

    // ***********************************************************
    // Modifier un emprunt
    // ***********************************************************
    public void updateEmprunt(Emprunt emprunt) {
        super.executeUpdate(
                "UPDATE emprunt SET dateDebut = ?, dateRendu = ?, exemplaire = ?, usager = ? WHERE id = ?",
                Arrays.asList(
                        emprunt.getDateDebut().toString(),
                        emprunt.getDateRendu().toString(),
                        emprunt.getExemplaire().getId(),
                        emprunt.getUsager().getEmail(),
                        emprunt.getId()));
    }
}
