package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Emprunt;
import ul.miage.patron.model.enumerations.EtatEmprunt;

public class HelperEmprunt {

    // ***********************************************************
    // Rendre un exemplaire
    // ***********************************************************
    public void rendreExemplaire(Emprunt emprunt) {
        Connection connection = SQLiteConnection.connect();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateRenduReelle = dateFormat.format(currentDate);

        if (connection != null) {
            try {
                String updateQuery = "UPDATE emprunt SET dateRenduReelle = ?, etat = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, dateRenduReelle);
                preparedStatement.setString(2, EtatEmprunt.TERMINE.toString());
                preparedStatement.setInt(3, emprunt.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ***********************************************************
    // Sélectionner tous les emprunts
    // ***********************************************************
    public ResultSet selectAllEmprunt() {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Emprunt";
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
    // Sélectionner un emprunt
    // ***********************************************************
    public ResultSet selectEmprunt(int id) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Emprunt WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(1, id);
                preparedStatement.executeQuery();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // ***********************************************************
    // Insérer un emprunt
    // ***********************************************************
    public void insertEmprunt(Emprunt emprunt) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String insertQuery = "INSERT INTO emprunt (dateDebut, dateRendu, exemplaire, usager) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, emprunt.getDateDebut().toString());
                preparedStatement.setString(2, emprunt.getDateRendu().toString());
                preparedStatement.setInt(3, emprunt.getExemplaire().getId());
                preparedStatement.setString(4, emprunt.getUsager().getEmail());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ***********************************************************
    // Modifier un emprunt
    // ***********************************************************
    public void updateEmprunt(Emprunt emprunt) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String updateQuery = "UPDATE emprunt SET dateDebut = ?, dateRendu = ?, exemplaire = ?, usager = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, emprunt.getDateDebut().toString());
                preparedStatement.setString(2, emprunt.getDateRendu().toString());
                preparedStatement.setInt(3, emprunt.getExemplaire().getId());
                preparedStatement.setString(4, emprunt.getUsager().getEmail());
                preparedStatement.setInt(5, emprunt.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
