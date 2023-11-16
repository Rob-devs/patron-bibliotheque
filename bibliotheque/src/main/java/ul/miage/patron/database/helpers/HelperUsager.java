package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Usager;

public class HelperUsager {

    public void selectAllUsager(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM usager";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void selectUsager(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM usager WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertUsager(Usager usager){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String insertQuery = "INSERT INTO usager (email, nom, prenom, telephone) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(0, usager.getEmail());
                preparedStatement.setString(1, usager.getNom());
                preparedStatement.setString(2, usager.getPrenom());
                preparedStatement.setInt(3, usager.getTelephone());
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUsager(Usager usager){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String updateQuery = "UPDATE usager SET email = ?, nom = ?, prenom = ?, telephone = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, usager.getEmail());
                preparedStatement.setString(1, usager.getNom());
                preparedStatement.setString(2, usager.getPrenom());
                preparedStatement.setInt(3, usager.getTelephone());
                preparedStatement.setInt(4, usager.getId());
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUsager(Usager usager){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String deleteQuery = "DELETE FROM usager WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(0, usager.getId());
                preparedStatement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}