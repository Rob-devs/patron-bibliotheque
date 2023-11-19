package ul.miage.patron.controller.emprunts;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ul.miage.patron.controller.oeuvres.ControllerOeuvre;
import ul.miage.patron.controller.reservations.ControllerReservation;
import ul.miage.patron.controller.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.HelperEmprunt;
import ul.miage.patron.database.helpers.HelperExemplaire;
import ul.miage.patron.database.helpers.HelperReservation;
import ul.miage.patron.model.actions.Emprunt;
import ul.miage.patron.model.actions.Reservation;
import ul.miage.patron.model.objets.Exemplaire;
import ul.miage.patron.model.objets.Oeuvre;
import ul.miage.patron.model.objets.Usager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControllerAddEmprunt {
    @FXML
    ChoiceBox<Usager> cbUsager = new ChoiceBox<Usager>();

    @FXML
    ChoiceBox<Oeuvre> cbOeuvre = new ChoiceBox<Oeuvre>();

    @FXML
    ChoiceBox<Exemplaire> cbExemplaire = new ChoiceBox<Exemplaire>();

    @FXML
    Button btnConfirm, btnCancel;

    

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize(){
        // Add event listener to cbOeuvre
        cbOeuvre.valueProperty().addListener(new ChangeListener<Oeuvre>() {
            @Override
            public void changed(ObservableValue<? extends Oeuvre> observable, Oeuvre oldValue, Oeuvre newValue) {
                fillCbExemplaire();
            }
        });
    }


    public void insertEmprunt(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut = LocalDate.parse(LocalDate.now().format(formatter), formatter);
        
        LocalDate dateRendu = dateDebut.plusMonths(3);

        Emprunt emprunt = new Emprunt(
            getNextIdTable(),
            dateDebut,
            dateRendu,
            cbExemplaire.getValue(),
            cbUsager.getValue()
        );
        HelperEmprunt helperEmprunt = new HelperEmprunt();
        helperEmprunt.insertEmprunt(emprunt);

        HelperExemplaire helperExemplaire = new HelperExemplaire();
        helperExemplaire.switchDisponible(emprunt.getExemplaire());

        HelperReservation helperReservation = new HelperReservation();
        ResultSet resultSetReservation = helperReservation.getExistingReservation(cbUsager.getValue(), cbOeuvre.getValue());
        try {
            if (resultSetReservation.next()) {
                ControllerReservation controllerReservation = new ControllerReservation();
                Reservation reservation = controllerReservation.selectReservation(resultSetReservation.getInt("id"));

                helperReservation.annulerReservation(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillCbUsager(){
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

    public void fillCbOeuvre(){
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

    

    public void fillCbExemplaire(){
        ControllerOeuvre controllerOeuvre = new ControllerOeuvre();
        controllerOeuvre.getAllOeuvre();
        controllerOeuvre.getAllExemplaires(cbOeuvre.getValue());
        
        cbExemplaire.setConverter(new StringConverter<Exemplaire>() {
            @Override
            public String toString(Exemplaire exemplaire) {
                if (exemplaire != null) {
                    return exemplaire.getId() + " - " + exemplaire.getOeuvre().getTitre() + " - " + exemplaire.getEtat();
                }
                return null;
            }

            @Override
            public Exemplaire fromString(String string) {
                return null;
            }
        });
        
        cbExemplaire.setItems(controllerOeuvre.getExemplairesDisponibles());
        if(cbExemplaire.getItems().size() > 0){
            cbExemplaire.setDisable(false);
            cbExemplaire.setValue(cbExemplaire.getItems().get(0));
        } else {
            cbExemplaire.setDisable(true);
            cbExemplaire.setValue(null);
        }
        
    }

    public void confirmAdd(){
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
        return nextId;
    }

    
}
