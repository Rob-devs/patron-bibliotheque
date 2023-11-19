package ul.miage.patron.model.objets;

import java.time.LocalDate;
import ul.miage.patron.model.enumerations.GenreOeuvre;

public class Oeuvre {

    private String titre;
    private String auteur;
    private LocalDate datePublication;
    private GenreOeuvre genre;
    private int nbReservations;

    public Oeuvre(String titre, String auteur, LocalDate date, GenreOeuvre genre, int nbReservations) {
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = date;
        this.genre = genre;
        this.nbReservations = nbReservations;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(int jour, int mois, int annee) {
        this.datePublication = LocalDate.of(annee, mois, jour);
    }

    public GenreOeuvre getGenre() {
        return genre;
    }

    public void setGenre(GenreOeuvre genre) {
        this.genre = genre;
    }

    public int getNbReservations() {
        return nbReservations;
    }

    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    // A faire : fonction statique de recherche d'oeuvre par titre (et auteur?)

}
