package ul.miage.patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {

        Connection connection = SQLiteConnection.connect();
        if (connection != null) {
            try {
                String insertQuery = "INSERT INTO livre (num, titre) VALUES (9, 9)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, 123);
                preparedStatement.setString(2, "test");
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
