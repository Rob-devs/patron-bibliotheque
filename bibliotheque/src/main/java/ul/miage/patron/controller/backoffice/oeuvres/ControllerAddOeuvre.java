package ul.miage.patron.controller.backoffice.oeuvres;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ul.miage.patron.controller.backoffice.usagers.ControllerUsager;
import ul.miage.patron.database.helpers.HelperOeuvre;
import ul.miage.patron.model.enumerations.GenreOeuvre;
import ul.miage.patron.model.objets.Oeuvre;

public class ControllerAddOeuvre {
    @FXML
    TextField tfTitre, tfAuteur;

    @FXML
    DatePicker dpDate;

    @FXML
    ChoiceBox<GenreOeuvre> cbGenreOeuvre = new ChoiceBox<GenreOeuvre>();

    @FXML
    Button btnConfirm, btnCancel;

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize() {

        tfTitre.textProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
        tfAuteur.textProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
        dpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
    }

    private void checkValues() {
        if (tfTitre.getText().isEmpty() || tfAuteur.getText().isEmpty() || dpDate.getValue().toString().isEmpty()) {
            btnConfirm.setDisable(true);
        } else {
            btnConfirm.setDisable(false);
        }
    }

    // Insérer une oeuvre
    public void insertOeuvre() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dpDate.getValue().format(formatter), formatter);
        Oeuvre oeuvre = new Oeuvre(
                tfTitre.getText(),
                tfAuteur.getText(),
                localDate,
                cbGenreOeuvre.getValue(),
                0);
        HelperOeuvre helperOeuvre = new HelperOeuvre();
        helperOeuvre.insertOeuvre(oeuvre);
    }

    public void fillCbGenreOeuvre() {
        cbGenreOeuvre.setItems(FXCollections.observableArrayList(GenreOeuvre.values()));
        cbGenreOeuvre.setValue(cbGenreOeuvre.getItems().get(0));
    }

    public void confirmAdd() {
        insertOeuvre();

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
}
