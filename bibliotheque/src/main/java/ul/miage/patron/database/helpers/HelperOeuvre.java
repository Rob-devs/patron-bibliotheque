package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Oeuvre;

public class HelperOeuvre {

    // ***********************************************************
    // Sélectionner toutes les oeuvres
    // ***********************************************************
    public ResultSet selectAllOeuvre() {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Oeuvre";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // ***********************************************************
    // Sélectionner une oeuvre
    // ***********************************************************
    public ResultSet selectOeuvre(String titre) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Oeuvre WHERE titre = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, titre);;
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // ***********************************************************
    // Insérer une oeuvre
    // ***********************************************************
    public void insertOeuvre(Oeuvre oeuvre) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String datePublication = oeuvre.getDatePublication().getDayOfMonth() + "/" + oeuvre.getDatePublication().getMonthValue() + "/" + oeuvre.getDatePublication().getYear();
                // Définir le modèle pour le format "D/MM/YYYY"
                DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

                // Définir le modèle pour le format "DD/MM/YYYY"
                DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                // Parser la chaîne pour obtenir un objet LocalDate
                LocalDate localDate = LocalDate.parse(datePublication, originalFormatter);

                // Formatter la date avec le nouveau modèle
                String formattedDatePublication = localDate.format(targetFormatter);


                String insertQuery = "INSERT INTO oeuvre (titre, auteur, datePublication, genre) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, oeuvre.getTitre());
                preparedStatement.setString(2, oeuvre.getAuteur());
                preparedStatement.setString(3, formattedDatePublication);
                preparedStatement.setString(4, oeuvre.getGenre().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNbExemplairesDispos(String titre){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT COUNT(*) FROM Exemplaire WHERE oeuvre = ? AND disponible = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, titre);
                preparedStatement.setString(2, "true");
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public int getNbExemplairesTotal(String titre){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT COUNT(*) FROM Exemplaire WHERE oeuvre = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, titre);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
