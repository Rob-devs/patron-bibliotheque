package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;

public class HelperEmprunt extends Helper {

    // ***********************************************************
    // Rendre un exemplaire
    // ***********************************************************
    public void rendreExemplaire(Emprunt emprunt) {
        LocalDate now = LocalDate.now();
        String dateRenduReelle = now.getDayOfMonth() + "/"
                + now.getMonthValue() + "/" + now.getYear();

        String formattedDateRenduReelle = super.convertFormatDate(dateRenduReelle);

        super.executeUpdate("UPDATE emprunt SET dateRenduReelle = ?, etat = ? WHERE id = ?",
                Arrays.asList(
                        formattedDateRenduReelle,
                        emprunt.getEtat().toString(),
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
        String dateDebut = emprunt.getDateDebut().getDayOfMonth() + "/"
                + emprunt.getDateDebut().getMonthValue() + "/" + emprunt.getDateDebut().getYear();

        String dateRendu = emprunt.getDateRendu().getDayOfMonth() + "/"
                + emprunt.getDateRendu().getMonthValue() + "/" + emprunt.getDateRendu().getYear();
        
        // Formatter la date avec le nouveau modèle
        String formattedDateDebut = super.convertFormatDate(dateDebut);
        String formattedDateRendu = super.convertFormatDate(dateRendu);

        super.executeUpdate(
                "INSERT INTO emprunt (dateDebut, dateRendu, exemplaire, usager) VALUES (?, ?, ?, ?)",
                Arrays.asList(
                        formattedDateDebut,
                        formattedDateRendu,
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

    // ***********************************************************
    // Calculer le nombre d'emprunts
    // ***********************************************************
        public int countEmprunts() {
                ResultSet result = super.execute("SELECT COUNT(*) FROM emprunt");
                int count = 0;
                try {
                        while (result.next()) {
                                count = result.getInt(1);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return count;
        }
}
