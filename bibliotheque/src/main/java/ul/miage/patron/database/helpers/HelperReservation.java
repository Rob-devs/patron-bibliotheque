package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.util.Arrays;

import ul.miage.patron.model.actions.Reservation;
import ul.miage.patron.model.enumerations.EtatReservation;

public class HelperReservation extends Helper {

    // ***********************************************************
    // Sélectionner toutes les réservations
    // ***********************************************************
    public ResultSet selectAllReservation() {
        return super.execute("SELECT * FROM Reservation");
    }

    // ***********************************************************
    // Sélectionner une réservation
    // ***********************************************************s
    public ResultSet selectReservation(int id) {
        return super.execute("SELECT * FROM Reservation WHERE id = ?", Arrays.asList(id));
    }

    // ***********************************************************
    // Insérer une réservation
    // ***********************************************************
    public void insertReservation(Reservation reservation) {
        String dateDebut = reservation.getDateDebut().getDayOfMonth() + "/"
                + reservation.getDateDebut().getMonthValue() + "/" + reservation.getDateDebut().getYear();
        
        // Formatter la date avec le nouveau modèle
        String formattedDateDebut = super.convertFormatDate(dateDebut);

        super.execute("INSERT INTO reservation (dateDebut, dateFin, etat, oeuvre, usager) VALUES (?, ?, ?, ?, ?)",
                Arrays.asList(
                        formattedDateDebut,
                        null,
                        reservation.getEtat().toString(),
                        reservation.getOeuvre().getTitre(),
                        reservation.getUsager().getEmail()));
    }

    // ***********************************************************
    // Modifier une réservation
    // ***********************************************************
    public void updateReservation(Reservation reservation) {
        super.executeUpdate(
                "UPDATE reservation SET dateDebut = ?, dateFin = ?, etat = ?, oeuvre = ?, usager = ? WHERE id = ?",
                Arrays.asList(
                        reservation.getDateDebut().toString(),
                        reservation.getDateFin().toString(),
                        reservation.getEtat().toString(),
                        reservation.getOeuvre().getTitre(),
                        reservation.getUsager().getEmail(),
                        reservation.getId()));
    }

    // ***********************************************************
    // Annuler une réservation
    // ***********************************************************
    public void annulerReservation(Reservation reservation) {
        super.executeUpdate("UPDATE reservation SET etat = ? WHERE id = ?",
                Arrays.asList(
                        EtatReservation.ANNULEE.toString(),
                        reservation.getId()));
    }

    // ***********************************************************
    // Calculer le nombre de réservations
    // ***********************************************************
    public int countReservations() {
        ResultSet resultSet = super.execute("SELECT COUNT(*) FROM reservation");
        int count = 0;
        try {
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
