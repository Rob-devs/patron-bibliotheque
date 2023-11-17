package ul.miage.patron.model;

public class Usager {

    private int id;

    private String prenom;
    private String nom;
    private String email;

    private int telephone;
    private int penalites;

    public Usager(int id, String name, String surname, String email, int telephone) {
        this.id = id;
        this.prenom = name;
        this.nom = surname;
        this.email = email;
        this.telephone = telephone;
        this.penalites = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String surname) {
        this.nom = surname;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String name) {
        this.prenom = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getPenalites() {
        return penalites;
    }

    public void setPenalites(int penalites) {
        this.penalites = penalites;
    }

    public void addPenalite() {
        this.penalites++;
    }
}
