package ul.miage.patron.model;

import java.util.List;

public class Usagers {
    private static List<Usager> usagers;

    public Usagers(List<Usager> usagers) {
        Usagers.usagers = usagers;
    }

    public static Usager identifier(String email) {
        return usagers.stream().filter(usager -> usager.getEmail().equals(email)).findFirst().orElse(null);
    };

    public static void setUsagers(List<Usager> usagers) {
        Usagers.usagers = usagers;
    }

}
