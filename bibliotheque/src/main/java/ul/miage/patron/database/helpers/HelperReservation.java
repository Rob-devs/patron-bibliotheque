package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.miage.patron.database.SQLiteConnection;
import ul.miage.patron.model.Reservation;

public class HelperReservation {
    
    public ResultSet selectAllReservation(){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Reservation";
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

    public ResultSet selectReservation(int id){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String selectQuery = "SELECT * FROM Reservation WHERE id = ?";
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

    public void insertReservation(Reservation reservation){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String insertQuery = "INSERT INTO reservation (dateDebut, dateFin, etat, oeuvre, usager) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery); 
                preparedStatement.setString(1, reservation.getDateDebut().toString());
                preparedStatement.setString(2, reservation.getDateFin().toString());
                preparedStatement.setString(3, reservation.getEtat().toString());
                preparedStatement.setString(4, reservation.getOeuvre().getTitre());
                preparedStatement.setString(5, reservation.getUsager().getEmail());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateReservation(Reservation reservation){
        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String updateQuery = "UPDATE reservation SET dateDebut = ?, dateFin = ?, etat = ?, oeuvre = ?, usager = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, reservation.getDateDebut().toString());
                preparedStatement.setString(2, reservation.getDateFin().toString());
                preparedStatement.setString(3, reservation.getEtat().toString());
                preparedStatement.setString(4, reservation.getOeuvre().getTitre());
                preparedStatement.setString(5, reservation.getUsager().getEmail());
                preparedStatement.setInt(6, reservation.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
