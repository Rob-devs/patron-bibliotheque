package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public ResultSet selectOeuvre(int id) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Oeuvre WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);;
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
                String insertQuery = "INSERT INTO oeuvre (titre, auteur, datePublication, genre) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, oeuvre.getTitre());
                preparedStatement.setString(2, oeuvre.getAuteur());
                preparedStatement.setString(3, oeuvre.getDatePublication().toString());
                preparedStatement.setString(4, oeuvre.getGenre().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
