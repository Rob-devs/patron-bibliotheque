package ul.miage.patron.controller.reservations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.controller.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.Helper;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.database.helpers.HelperReservation;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.actions.Reservation;
import ul.miage.patron.model.enumerations.EtatReservation;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;

public class ControllerAddReservation {
    @FXML
    ChoiceBox<Usager> cbUsager = new ChoiceBox<Usager>();

    @FXML
    ChoiceBox<Oeuvre> cbOeuvre = new ChoiceBox<Oeuvre>();

    @FXML
    Button btnConfirm, btnCancel;

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize() {

    }

    public void insertReservation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut = LocalDate.parse(LocalDate.now().format(formatter), formatter);

        Reservation reservation = new Reservation(
                getNextIdTable(),
                dateDebut,
                null,
                EtatReservation.EN_COURS,
                cbOeuvre.getValue(),
                cbUsager.getValue());
        HelperReservation helperReservation = new HelperReservation();
        helperReservation.insertReservation(reservation);

        HelperOeuvre helperOeuvre = new HelperOeuvre();
        helperOeuvre.incrementNbReservations(cbOeuvre.getValue());
    }

    public void fillCbUsager() {
        ControllerUsager controllerUsager = new ControllerUsager();
        controllerUsager.getAllUsager();

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

        cbUsager.setItems(controllerUsager.getUsagers());
        cbUsager.setValue(cbUsager.getItems().get(0));
    }

    public void fillCbOeuvre() {
        ControllerOeuvre controllerOeuvre = new ControllerOeuvre();
        controllerOeuvre.getAllOeuvre();

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

        cbOeuvre.setItems(controllerOeuvre.getOeuvres());
        cbOeuvre.setValue(cbOeuvre.getItems().get(0));
    }

    public void confirmAdd() {
        insertReservation();

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
        HelperReservation helperReservation = new HelperReservation();
        int nextId = helperReservation.countReservations() + 1;
        Helper.disconnect();
        return nextId;
    }
}
