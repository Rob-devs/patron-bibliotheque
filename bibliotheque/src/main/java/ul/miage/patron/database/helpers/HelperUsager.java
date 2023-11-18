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
    public ResultSet selectUsager(String email) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Usager WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setString(1, email);
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
    // Insérer un usager
    // ***********************************************************
    public void insertUsager(Usager usager) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String insertQuery = "INSERT INTO usager (email, nom, prenom, telephone) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, usager.getEmail());
                preparedStatement.setString(2, usager.getNom());
                preparedStatement.setString(3, usager.getPrenom());
                preparedStatement.setInt(4, usager.getTelephone());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ***********************************************************
    // Modifier un usager
    // ***********************************************************
    public void updateUsager(Usager usager, String oldMail) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String updateQuery = "UPDATE usager SET email = ?, nom = ?, prenom = ?, telephone = ? WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, usager.getEmail());
                preparedStatement.setString(2, usager.getNom());
                preparedStatement.setString(3, usager.getPrenom());
                preparedStatement.setInt(4, usager.getTelephone());
                preparedStatement.setString(5, oldMail);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void deleteUsager(Usager usager) {
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String deleteQuery = "DELETE FROM usager WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, usager.getEmail());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}