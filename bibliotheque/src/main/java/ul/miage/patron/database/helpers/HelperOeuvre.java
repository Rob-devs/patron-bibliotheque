package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Oeuvre;

public class HelperOeuvre {

    public ResultSet selectAllOeuvre(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
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

    public ResultSet selectOeuvre(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM Oeuvre WHERE id = ?";
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

    public void insertOeuvre(Oeuvre oeuvre){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String insertQuery = "INSERT INTO oeuvre (titre, auteur, datePublication, genre) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(0, oeuvre.getTitre());
                preparedStatement.setString(1, oeuvre.getAuteur());
                preparedStatement.setString(2, oeuvre.getDatePublication().toString());
                preparedStatement.setString(3, oeuvre.getGenre().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }   
    }

    public void updateOeuvre(Oeuvre oeuvre){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String updateQuery = "UPDATE oeuvre SET titre = ?, auteur = ?, datePublication = ?, genre = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, oeuvre.getTitre());
                preparedStatement.setString(1, oeuvre.getAuteur());
                preparedStatement.setString(2, oeuvre.getDatePublication().toString());
                preparedStatement.setString(3, oeuvre.getGenre().toString());
                preparedStatement.setInt(4, oeuvre.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
