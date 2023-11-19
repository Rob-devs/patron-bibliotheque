package ul.miage.patron.model.actions;

import java.util.List;

public class Emprunts {
    private static List<Emprunt> emprunts;

    public Emprunts(List<Emprunt> emprunts) {
        Emprunts.emprunts = emprunts;
    }

    public static Emprunt identifier(int id) {
        return emprunts.stream().filter(emprunt -> emprunt.getId() == id).findFirst().orElse(null);
    };

}
