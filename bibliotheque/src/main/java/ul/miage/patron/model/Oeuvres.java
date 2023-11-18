package ul.miage.patron.model;

import java.util.List;

public class Oeuvres {
    private static List<Oeuvre> oeuvres;

    public Oeuvres(List<Oeuvre> oeuvres) {
        Oeuvres.oeuvres = oeuvres;
    }

    public static Oeuvre identifier(int id) {
        return oeuvres.stream().filter(oeuvre -> oeuvre.getId() == id).findFirst().orElse(null);
    };
}
