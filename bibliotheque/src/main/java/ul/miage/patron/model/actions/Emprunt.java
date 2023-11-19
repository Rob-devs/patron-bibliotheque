package ul.miage.patron.model.actions;

import ul.miage.patron.model.enumerations.EtatEmprunt;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Usager;

import java.time.LocalDate;

public class Emprunt {

    private int id;
    private LocalDate dateDebut;
    private LocalDate dateRendu;
    private LocalDate dateRenduReelle;
    private EtatEmprunt etat;
    private Exemplaire exemplaire;
    private Usager usager;

    public Emprunt(int id, LocalDate dateDebut, LocalDate dateRendu, Exemplaire exemplaire, Usager usager) {
        this.dateDebut = dateDebut;
        this.dateRendu = dateRendu;
        this.etat = EtatEmprunt.EN_COURS;
        this.exemplaire = exemplaire;
        this.usager = usager;
    }

    public Emprunt(int id, LocalDate dateDebut, LocalDate dateRendu, LocalDate dateRenduRelle, EtatEmprunt etat, Exemplaire exemplaire,
            Usager usager) {
        this.dateDebut = dateDebut;
        this.dateRendu = dateRendu;
        this.etat = etat;
        this.exemplaire = exemplaire;
        this.usager = usager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateRendu() {
        return dateRendu;
    }

    public void setDateRendu(LocalDate dateRendu) {
        this.dateRendu = dateRendu;
    }

    public LocalDate getDateRenduReelle() {
        return dateRenduReelle;
    }

    public void setDateRenduReelle(LocalDate dateRenduReelle) {
        this.dateRenduReelle = dateRenduReelle;
    }

    public EtatEmprunt getEtat() {
        return etat;
    }

    public void setEtat(EtatEmprunt etat) {
        this.etat = etat;
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
        this.exemplaire.setDisponible(true);
    }
}
