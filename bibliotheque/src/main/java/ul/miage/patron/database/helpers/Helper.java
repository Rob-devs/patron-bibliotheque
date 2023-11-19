package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ul.miage.patron.database.SQLiteConnection;

public abstract class Helper {

    // Connection à la base
    public static Connection connection;

    // ***********************************************************
    // Exécution en select
    // ***********************************************************
    public ResultSet execute(String query) {
        return this.execute(query, null);
    }

    public ResultSet execute(String query, List<Object> args) {
        try {
            connection = SQLiteConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            if (args != null) {
                int i = 1;
                for (Object o : args) {
                    if (o instanceof Integer) {
                        preparedStatement.setInt(i, Integer.parseInt(o.toString()));
                    } else if (o instanceof String) {
                        preparedStatement.setString(i, o.toString());
                    }
                    i++;
                }
            }
            System.out
                    .println("\u001B[33m" + "Exécution de " + query + (args == null ? "" : " avec " + args.toString())
                            + "\u001B[0m");
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ***********************************************************
    // Exécution en insertion, update ou delete
    // ***********************************************************
    public void executeUpdate(String query) {
        this.executeUpdate(query, null);
    }

    public void executeUpdate(String query, List<Object> args) {
        try {
            connection = SQLiteConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            if (args != null) {
                int i = 1;
                for (Object o : args) {
                    if (o instanceof Integer) {
                        preparedStatement.setInt(i, Integer.parseInt(o.toString()));
                    } else if (o instanceof String) {
                        preparedStatement.setString(i, o.toString());
                    }
                    i++;
                }
            }
            System.out
                    .println("\u001B[33m" + "Exécution de " + query + (args == null ? "" : " avec " + args.toString())
                            + "\u001B[0m");
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
