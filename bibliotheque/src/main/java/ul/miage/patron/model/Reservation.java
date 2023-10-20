package ul.miage.patron.model;

import ul.miage.patron.model.enumerations.EtatReservation;
import ul.miage.patron.utilities.Date;

public class Reservation {
    private Date dateDebut;
    private Date dateFin;
    private EtatReservation etat;

    public Date getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    public Date getDateFin() {
        return dateFin;
    }
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    public EtatReservation getEtat() {
        return etat;
    }
    public void setEtat(EtatReservation etat) {
        this.etat = etat;
    }

    public Reservation(Date dateDebut, Date dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = EtatReservation.EN_COURS;
    }
}
