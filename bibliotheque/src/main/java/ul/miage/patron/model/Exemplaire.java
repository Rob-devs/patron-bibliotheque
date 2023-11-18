package ul.miage.patron.model;

import ul.miage.patron.model.enumerations.EtatExemplaire;

public class Exemplaire {

    private int id;
    private EtatExemplaire etat;
    private boolean disponible;
    private Oeuvre oeuvre;

    public Exemplaire(int id, EtatExemplaire etat, Oeuvre oeuvre) {
        this.id = id;
        this.etat = etat;
        this.disponible = true;
        this.oeuvre = oeuvre;
    }

    public Exemplaire(int id, EtatExemplaire etat, boolean disponible, Oeuvre oeuvre) {
        this.id = id;
        this.etat = etat;
        this.disponible = disponible;
        this.oeuvre = oeuvre;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

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
}
