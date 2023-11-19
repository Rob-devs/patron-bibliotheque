package ul.miage.patron.controller.usagers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.objets.Usager;

public class ControllerAddUsager {
    @FXML
    TextField tfNom, tfPrenom, tfMail, tfTelephone;

    @FXML
    Button btnConfirm, btnCancel;

    private Stage popupStage;
    private Stage parentStage;

    @FXML
    public void initialize() {

        tfNom.textProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
        tfPrenom.textProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
        tfMail.textProperty().addListener((observable, oldValue, newValue) -> {
            checkValues();
        });
    }

    private void checkValues() {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || tfMail.getText().isEmpty()) {
            btnConfirm.setDisable(true);
        } else {
            btnConfirm.setDisable(false);
        }
    }

    // Insérer un usager
    public void insertUsager() {
        Usager usager = new Usager(
                tfMail.getText(),
                tfNom.getText(),
                tfPrenom.getText(),
                tfTelephone.getText());
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.insertUsager(usager);
    }

    public void confirmAdd() {
        insertUsager();

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
