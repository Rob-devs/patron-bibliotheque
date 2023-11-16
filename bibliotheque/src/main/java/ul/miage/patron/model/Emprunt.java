package ul.miage.patron.model;

import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.utilities.Date;

public class Emprunt {
    private int id;
    private Date dateDebut;
    private Date dateRendu;
    private Date dateRenduReelle;
    private EtatEmprunt etat;
    private Exemplaire exemplaire;
    private Usager usager;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDateRendu() {
        return dateRendu;
    }
    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }
    public Date getDateRenduReelle() {
        return dateRenduReelle;
    }
    public void setDateRenduReelle(Date dateRenduReelle) {
        this.dateRenduReelle = dateRenduReelle;
    }
    public EtatEmprunt getEtat() {
        return etat;
    }
    public void setEtat(EtatEmprunt etat) {
        this.etat = etat;
    }

    public Emprunt(Date dateDebut, Date dateRendu) {
        this.dateDebut = dateDebut;
        this.dateRendu = dateRendu;
        this.etat = EtatEmprunt.EN_COURS;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }
    public void setExemplaire(Exemplaire exemplaire) {
        this.exemplaire = exemplaire;
    }
    public Usager getUsager() {
        return usager;
    }
    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    public void rendre() {
        this.etat = EtatEmprunt.TERMINE;
    }

    // A faire : rendreReserve, rendreAbime, rendreRetard, (declarerPerte)?
}
