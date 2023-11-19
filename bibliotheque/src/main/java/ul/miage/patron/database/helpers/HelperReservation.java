package ul.miage.patron.database.helpers;

import java.sql.ResultSet;
import java.util.Arrays;

import ul.miage.patron.model.actions.Reservation;

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
        super.execute("INSERT INTO reservation (dateDebut, dateFin, etat, oeuvre, usager) VALUES (?, ?, ?, ?, ?)",
                Arrays.asList(
                        reservation.getDateDebut().toString(),
                        reservation.getDateFin().toString(),
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
}
