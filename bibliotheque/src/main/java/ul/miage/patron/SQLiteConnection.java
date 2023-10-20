package ul.miage.patron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URL;

public class SQLiteConnection {
    public static Connection connect() {
        Connection connection = null;
        try {
            // Charger le driver SQLite
            String filePath = "bibliotheque/src/main/resources/bibliotheque.db";
            Class.forName("org.sqlite.JDBC");

            // Établir la connexion
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
