package ul.miage.patron.database.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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

    public String convertFormatDate(String date) {
        List<String> formatsPossibles = Arrays.asList("d/MM/yyyy", "dd/MM/yyyy", "d/M/yyyy", "dd/M/yyyy", "d/MM/yy", "dd/MM/yy", "d/M/yy", "dd/M/yy");

        for (String format : formatsPossibles) {
            try {
                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
                return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                // Le format actuel ne correspond pas, essayer le prochain
            }
        }

        // Aucun format n'a fonctionné, retourner une chaîne vide ou gérer l'erreur selon vos besoins
        return "";
    }

}
