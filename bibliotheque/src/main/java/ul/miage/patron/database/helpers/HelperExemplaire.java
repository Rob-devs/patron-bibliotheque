package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Exemplaire;

public class HelperExemplaire {
    public ResultSet selectAllExemplaire(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM Exemplaire";
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

    public ResultSet selectExemplaire(Exemplaire exemplaire){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM Exemplaire WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setInt(0, exemplaire.getId());
                preparedStatement.executeQuery();
                ResultSet resultSet = preparedStatement.getResultSet();
                return resultSet;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void insertExemplaire(Exemplaire exemplaire){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String insertQuery = "INSERT INTO exemplaire (etat, disponible, oeuvre) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(0, exemplaire.getEtat().toString());
                preparedStatement.setString(1, Boolean.toString(exemplaire.isDisponible()));
                preparedStatement.setString(2, exemplaire.getEtat().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateExemplaire(Exemplaire exemplaire){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String updateQuery = "UPDATE exemplaire SET etat = ?, disponible = ?, oeuvre = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, exemplaire.getEtat().toString());
                preparedStatement.setString(1, Boolean.toString(exemplaire.isDisponible()));
                preparedStatement.setString(2, exemplaire.getEtat().toString());
                preparedStatement.setInt(3, exemplaire.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
