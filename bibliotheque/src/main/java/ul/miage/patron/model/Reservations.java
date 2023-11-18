package ul.miage.patron.model;

import java.util.List;

public class Reservations {
    private static List<Reservation> reservations;

    public Reservations(List<Reservation> reservations) {
        Reservations.reservations = reservations;
    }

    public static Reservation identifier(int id) {
        return reservations.stream().filter(reservation -> reservation.getId() == id).findFirst().orElse(null);
    };
}
