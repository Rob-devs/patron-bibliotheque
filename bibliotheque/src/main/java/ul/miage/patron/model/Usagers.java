package ul.miage.patron.model;

import java.util.List;

public class Usagers {

    List<Usager> usagers;

    public Usagers(List<Usager> usagers) {
        this.usagers = usagers;
    }

    public Usager identifier(String email) {
        return usagers.stream().filter(usager -> usager.getEmail().equals(email)).findFirst().orElse(null);
    };

}
