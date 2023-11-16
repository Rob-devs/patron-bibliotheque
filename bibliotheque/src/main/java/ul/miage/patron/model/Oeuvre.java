package ul.miage.patron.model;

import ul.miage.patron.utilities.Date;

import ul.miage.patron.model.enumerations.GenreOeuvre;

public class Oeuvre {
    private int id;
    private String titre;
    private String auteur;
    private Date datePublication;
    private GenreOeuvre genre;
    private int nbReservations;

    public Oeuvre(String titre, String auteur, int jour, int mois, int annee, GenreOeuvre genre) {
        this.titre = titre;
        this.auteur = auteur;
        this.datePublication = new Date(jour, mois, annee);
        this.genre = genre;
        this.nbReservations = 0;
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

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(int jour, int mois, int annee) {
        this.datePublication = new Date(jour, mois, annee);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    // A faire : fonction statique de recherche d'oeuvre par titre (et auteur?)

    
}
