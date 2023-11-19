package ul.miage.patron.model.objets;

import java.util.List;

public class Exemplaires {
    private static List<Exemplaire> exemplaires;

    public Exemplaires(List<Exemplaire> exemplaires) {
        Exemplaires.exemplaires = exemplaires;
    }

    public static Exemplaire identifier(int id) {
        return exemplaires.stream().filter(exemplaire -> exemplaire.getId() == id).findFirst().orElse(null);
    };
}
