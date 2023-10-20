package ul.miage.patron.model;
public class Usager {
    private String prenom;
    private String nom;
    private String email;
    private int telephone;

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

    public Usager(String name, String surname, String email, int telephone) {
        this.prenom = name;
        this.nom = surname;
        this.email = email;
        this.telephone = telephone;
    }

    // TODO : fonction statique de recherche d'usager par nom
}
