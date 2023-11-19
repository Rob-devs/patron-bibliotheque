package ul.miage.patron.model.actions;

import ul.miage.patron.model.enumerations.EtatReservation;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private EtatReservation etat;
    private Oeuvre oeuvre;
    private Usager usager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Oeuvre getOeuvre() {
        return oeuvre;
    }

    public void setOeuvre(Oeuvre oeuvre) {
        this.oeuvre = oeuvre;
    }

    public Usager getUsager() {
        return usager;
    }

    public void setUsager(Usager usager) {
        this.usager = usager;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public EtatReservation getEtat() {
        return etat;
    }

    public void setEtat(EtatReservation etat) {
        this.etat = etat;
    }

    public Reservation(int id, LocalDate dateDebut, LocalDate dateFin, EtatReservation etat, Oeuvre oeuvre, Usager usager) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
        this.oeuvre = oeuvre;
        this.usager = usager;
    }
}
