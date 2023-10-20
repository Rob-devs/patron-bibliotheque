package ul.miage.patron.model;

import ul.miage.patron.model.enumerations.EtatExemplaire;

public class Exemplaire {
    private int id;
    private EtatExemplaire etat;
    private boolean disponible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EtatExemplaire getEtat() {
        return etat;
    }

    public void setEtat(EtatExemplaire etat) {
        this.etat = etat;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Exemplaire(int id, EtatExemplaire etat) {
        this.id = id;
        this.etat = etat;
        this.disponible = true;
    }

    // A faire : méthode statique pour trouver un exemplaire à partir du titre de l'oeuvre
}
