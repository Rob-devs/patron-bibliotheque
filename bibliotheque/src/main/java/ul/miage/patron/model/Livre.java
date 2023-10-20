package ul.miage.patron.model;

import ul.miage.patron.model.enumerations.TypeLivre;

public class Livre {
    private TypeLivre type;

    public TypeLivre getType() {
        return type;
    }

    public void setType(TypeLivre type) {
        this.type = type;
    }
}
