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
        // Définir le modèle pour le format "D/MM/YYYY"
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        // Définir le modèle pour le format "DD/MM/YYYY"
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Parser la chaîne pour obtenir un objet LocalDate
        LocalDate localDate = LocalDate.parse(datePublication, originalFormatter);

        // Formatter la date avec le nouveau modèle
        String formattedDatePublication = localDate.format(targetFormatter);

        super.executeUpdate("INSERT INTO oeuvre (titre, auteur, datePublication, genre) VALUES (?, ?, ?, ?)",
                Arrays.asList(
                        oeuvre.getTitre(),
                        oeuvre.getAuteur(),
                        formattedDatePublication,
                        oeuvre.getGenre().toString()));
    }
}
