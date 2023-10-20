package ul.miage.patron.utilities;

public class Date {
    private int jour;
    private int mois;
    private int annee;

    public int getJour() {
        return jour;
    }
    public void setJour(int jour) {
        this.jour = jour;
    }
    public int getMois() {
        return mois;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public Date(int jour, int mois, int annee) {
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
    }
}
