package ul.miage.patron.model.objets;

import java.util.List;

public class Oeuvres {
    private static List<Oeuvre> oeuvres;

    public Oeuvres(List<Oeuvre> oeuvres) {
        Oeuvres.oeuvres = oeuvres;
    }

    public static Oeuvre identifier(String titre) {
        return oeuvres.stream().filter(oeuvre -> oeuvre.getTitre() == titre).findFirst().orElse(null);
    };
}
