package ul.miage.patron.controller.emprunts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.controller.reservations.ControllerReservation;
import ul.miage.patron.controller.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.Helper;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.database.helpers.HelperReservation;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.actions.Reservation;
import ul.miage.patron.model.enumerations.EtatExemplaire;
import ul.miage.patron.model.enumerations.EtatReservation;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class ControllerAddEmprunt {

    @FXML
    ChoiceBox<Usager> cbUsager = new ChoiceBox<Usager>();

    @FXML
    ChoiceBox<Oeuvre> cbOeuvre = new ChoiceBox<Oeuvre>();

    @FXML
    ChoiceBox<Exemplaire> cbExemplaire = new ChoiceBox<Exemplaire>();

    @FXML
    Button btnConfirm, btnCancel;

    List<Oeuvre> oeuvres = new ArrayList<Oeuvre>();
    List<Usager> usagers = new ArrayList<Usager>();
    List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize() {
        // Add event listener to cbOeuvre
        cbOeuvre.valueProperty().addListener(new ChangeListener<Oeuvre>() {
            @Override
            public void changed(ObservableValue<? extends Oeuvre> observable, Oeuvre oldValue, Oeuvre newValue) {
                fillCbExemplaire();
            }
        });
    }

    public void insertEmprunt() {

        Reservation reservation = null;

        HelperReservation helperReservation = new HelperReservation();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut = LocalDate.parse(LocalDate.now().format(formatter), formatter);

        LocalDate dateRendu = dateDebut.plusMonths(3);

        Emprunt emprunt = new Emprunt(
                getNextIdTable(),
                dateDebut,
                dateRendu,
                cbExemplaire.getValue(),
                cbUsager.getValue());

        HelperEmprunt helperEmprunt = new HelperEmprunt();
        helperEmprunt.insertEmprunt(emprunt);

        HelperExemplaire helperExemplaire = new HelperExemplaire();
        helperExemplaire.switchDisponible(emprunt.getExemplaire());

        try {
            ResultSet resultSetReservation = helperReservation.getExistingReservation(cbUsager.getValue(),
                    cbOeuvre.getValue());
            if (resultSetReservation.next()) {
                reservation = selectReservation(resultSetReservation.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (reservation != null) {
            helperReservation.annulerReservation(reservation);
        }
    }

    public Reservation selectReservation(int id) {

        HelperReservation helperReservation = new HelperReservation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ResultSet resultSet = helperReservation.selectReservation(id);
        Reservation reservation = null;
        try {

            String titre = resultSet.getString("oeuvre");
            String email = resultSet.getString("usager");

            while (resultSet.next()) {
                LocalDate dateDebut = LocalDate.parse(resultSet.getString("dateDebut"), formatter);

                LocalDate dateFin = null;
                if (resultSet.getString("dateFin") != null) {
                    dateFin = LocalDate.parse(resultSet.getString("dateFin"), formatter);
                }

                EtatReservation etat = EtatReservation.valueOf(resultSet.getString("etat"));

                Oeuvre oeuvre = (Oeuvre) oeuvres.stream()
                        .filter(o -> o.getTitre().equals(titre)).toArray()[0];

                Usager usager = (Usager) usagers.stream().filter(o -> o.getEmail().equals(email)).toArray()[0];

                reservation = new Reservation(id, dateDebut, dateFin, etat, oeuvre, usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        return reservation;

    }

    public void fillCbUsager() {
        HelperUsager helperUsager = new HelperUsager();

        // Vider la liste avant de la remplir
        if (usagers != null)
            usagers.clear();

        ResultSet resultSet = helperUsager.selectAllUsager();
        try {
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String telephone = resultSet.getString("telephone");
                Usager usager = new Usager(email, nom, prenom, telephone);
                usagers.add(usager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        cbUsager.setConverter(new StringConverter<Usager>() {
            @Override
            public String toString(Usager usager) {
                return usager.getNom() + " " + usager.getPrenom() + " - " + usager.getEmail();
            }

            @Override
            public Usager fromString(String string) {
                return null;
            }
        });

        cbUsager.setItems(FXCollections.observableList(usagers));
        cbUsager.setValue(cbUsager.getItems().get(0));
    }

    public void fillCbOeuvre() {

        HelperOeuvre helperOeuvre = new HelperOeuvre();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Vider la liste avant de la remplir
        oeuvres.clear();

        ResultSet resultSet = helperOeuvre.selectAllOeuvre();
        try {
            while (resultSet.next()) {
                String titre = resultSet.getString("titre");
                String auteur = resultSet.getString("auteur");
                LocalDate datePublication = LocalDate.parse(resultSet.getString("datePublication"), formatter);
                GenreOeuvre genreOeuvre = GenreOeuvre.valueOf(resultSet.getString("genre"));
                int nbReservations = resultSet.getInt("nbReservations");
                Oeuvre oeuvre = new Oeuvre(titre, auteur, datePublication, genreOeuvre, nbReservations);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        cbOeuvre.setConverter(new StringConverter<Oeuvre>() {
            @Override
            public String toString(Oeuvre oeuvre) {
                return oeuvre.getTitre();
            }

            @Override
            public Oeuvre fromString(String string) {
                return null;
            }
        });

        cbOeuvre.setItems(FXCollections.observableList(oeuvres));
        cbOeuvre.setValue(cbOeuvre.getItems().get(0));
    }

    public void fillCbExemplaire() {

        HelperExemplaire helper = new HelperExemplaire();
        ResultSet resultSet = helper.selectAllExemplaire();

        // Vider la liste avant de la remplir
        exemplaires.clear();

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String etat = resultSet.getString("etat");
                boolean disponible = resultSet.getString("disponible").toLowerCase().equals("true");
                String titre = resultSet.getString("oeuvre");
                if (titre.equals(cbOeuvre.getValue().getTitre())) {
                    Exemplaire e = new Exemplaire(id, EtatExemplaire.valueOf(etat), disponible, cbOeuvre.getValue());
                    if (e.isDisponible()) {
                        exemplaires.add(e);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Helper.disconnect();
        }

        cbExemplaire.setConverter(new StringConverter<Exemplaire>() {
            @Override
            public String toString(Exemplaire exemplaire) {
                if (exemplaire != null) {
                    return exemplaire.getId() + " - " + exemplaire.getOeuvre().getTitre() + " - "
                            + exemplaire.getEtat();
                }
                return null;
            }

            @Override
            public Exemplaire fromString(String string) {
                return null;
            }
        });

        cbExemplaire.setItems(FXCollections.observableList(exemplaires));
        if (cbExemplaire.getItems().size() > 0) {
            cbExemplaire.setDisable(false);
            cbExemplaire.setValue(cbExemplaire.getItems().get(0));
        } else {
            cbExemplaire.setDisable(true);
            cbExemplaire.setValue(null);
        }

    }

    public void confirmAdd() {
        insertEmprunt();

        popupStage.close();
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void close() {
        // Close the stage (pop-up window)
        if (popupStage != null) {
            popupStage.close();
        }
    }

    public int getNextIdTable() {
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        int nextId = helperEmprunt.countEmprunts() + 1;
        Helper.disconnect();
        return nextId;
    }

}
