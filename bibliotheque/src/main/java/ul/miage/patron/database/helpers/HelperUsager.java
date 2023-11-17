package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Usager;

public class HelperUsager {

    // ***********************************************************
    // Sélectionner tous les usagers
    // ***********************************************************
    public ResultSet selectAllUsager() {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Usager";
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
    // Sélectionner un usager
    // ***********************************************************
    public ResultSet selectUsager() {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Usager WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
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
    // Insérer un usager
    // ***********************************************************
    public void insertUsager(Usager usager) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String insertQuery = "INSERT INTO usager (email, nom, prenom, telephone) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(0, usager.getEmail());
                preparedStatement.setString(1, usager.getNom());
                preparedStatement.setString(2, usager.getPrenom());
                preparedStatement.setInt(3, usager.getTelephone());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ***********************************************************
    // Modifier un usager
    // ***********************************************************
    public void updateUsager(Usager usager) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String updateQuery = "UPDATE usager SET email = ?, nom = ?, prenom = ?, telephone = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, usager.getEmail());
                preparedStatement.setString(1, usager.getNom());
                preparedStatement.setString(2, usager.getPrenom());
                preparedStatement.setInt(3, usager.getTelephone());
                preparedStatement.setInt(4, usager.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}