package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import ul.miage.patron.model.objets.Oeuvre;

public class HelperOeuvre extends Helper {

    // ***********************************************************
    // Sélectionner toutes les oeuvres
    // ***********************************************************
    public ResultSet selectAllOeuvre() {
        return super.execute("SELECT * FROM Oeuvre");
    }

    // ***********************************************************
    // Sélectionner une oeuvre
    // ***********************************************************
    public ResultSet selectOeuvre(String titre) {
        return super.execute("SELECT * FROM Oeuvre WHERE titre = ?", Arrays.asList(titre));
    }

    // ***********************************************************
    // Insérer une oeuvre
    // ***********************************************************
    public void insertOeuvre(Oeuvre oeuvre) {
        String datePublication = oeuvre.getDatePublication().getDayOfMonth() + "/"
                + oeuvre.getDatePublication().getMonthValue() + "/" + oeuvre.getDatePublication().getYear();

        // Formatter la date avec le nouveau modèle
        String formattedDatePublication = super.convertFormatDate(datePublication);

        super.executeUpdate("INSERT INTO oeuvre (titre, auteur, datePublication, genre) VALUES (?, ?, ?, ?)",
                Arrays.asList(
                        oeuvre.getTitre(),
                        oeuvre.getAuteur(),
                        formattedDatePublication,
                        oeuvre.getGenre().toString()));
    }

    // ***********************************************************
    // Incrémenter nb réservations
    // ***********************************************************
    public void incrementNbReservations(Oeuvre oeuvre) {
        super.executeUpdate("UPDATE oeuvre SET nbReservations = ? WHERE titre = ?",
                Arrays.asList(
                        oeuvre.getNbReservations() + 1,
                        oeuvre.getTitre()));
    }

    // ***********************************************************
    // Décrémenter nb réservations
    // ***********************************************************
    public void decrementNbReservations(Oeuvre oeuvre) {
        super.executeUpdate("UPDATE oeuvre SET nbReservations = ? WHERE titre = ?",
                Arrays.asList(
                        oeuvre.getNbReservations() - 1,
                        oeuvre.getTitre()));
    }
}
