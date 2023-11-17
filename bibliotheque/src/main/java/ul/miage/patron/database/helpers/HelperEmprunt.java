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

    public void rendreExemplaire(Emprunt emprunt){
        Connection connection = SQLiteConnection.connect();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateRenduReelle = dateFormat.format(currentDate);
        
        if(connection != null){
            try {
                String updateQuery = "UPDATE emprunt SET dateRenduReelle = ?, etat = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, dateRenduReelle);
                preparedStatement.setString(1, EtatEmprunt.TERMINE.toString());
                preparedStatement.setInt(2, emprunt.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ResultSet selectAllEmprunt(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
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

    public ResultSet selectEmprunt(){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String selectQuery = "SELECT * FROM Emprunt WHERE id = ?";
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

    public void insertEmprunt(Emprunt emprunt){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String insertQuery = "INSERT INTO emprunt (dateDebut, dateRendu, exemplaire, usager) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(0, emprunt.getDateDebut().toString());
                preparedStatement.setString(1, emprunt.getDateRendu().toString());
                preparedStatement.setInt(2, emprunt.getExemplaire().getId());
                preparedStatement.setInt(3, emprunt.getUsager().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEmprunt(Emprunt emprunt){
        Connection connection = SQLiteConnection.connect();
        if(connection != null){
            try {
                String updateQuery = "UPDATE emprunt SET dateDebut = ?, dateRendu = ?, exemplaire = ?, usager = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(0, emprunt.getDateDebut().toString());
                preparedStatement.setString(1, emprunt.getDateRendu().toString());
                preparedStatement.setInt(2, emprunt.getExemplaire().getId());
                preparedStatement.setInt(3, emprunt.getUsager().getId());
                preparedStatement.setInt(4, emprunt.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
