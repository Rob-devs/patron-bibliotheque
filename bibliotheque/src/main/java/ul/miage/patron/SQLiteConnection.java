package ul.miage.patron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.net.URL;

public class SQLiteConnection {
    public static Connection connect() {
        Connection connection = null;
        try {
            URL dbUrl = SQLiteConnection.class.getResource("/bibliotheque.db");
            if (dbUrl != null) {
                Class.forName("org.sqlite.JDBC");

                // Établir la connexion
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbUrl.getPath());
                return connection;
            } else {
                System.err.println("La base de données n'a pas été trouvée.");
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
