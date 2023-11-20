package ul.miage.patron.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private static Connection connection = null;

    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                // Charger le driver SQLite
                String filePath = "bibliotheque/src/main/resources/bibliotheque.db";
                Class.forName("org.sqlite.JDBC");

                // Établir la connexion
                connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            }
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}