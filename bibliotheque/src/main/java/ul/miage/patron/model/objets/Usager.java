package ul.miage.patron.model.objets;

public class Usager {

    private String email;
    private String nom;
    private String prenom;
    private int telephone;
    private int penalites;

    public Usager(String email, String nom, String prenom, int telephone) {
        this.email = email;
        this.prenom = nom;
        this.nom = prenom;
        this.telephone = telephone;
        this.penalites = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public static Usager identifier(String email) {
        return Usagers.identifier(email);
    }
}
