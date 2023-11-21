package ul.miage.patron.controller.backoffice.usagers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.objets.Usager;

public class ControllerUpdateUsager {
    @FXML
    TextField tfNom, tfPrenom, tfMail, tfTelephone;

    @FXML
    Button btnConfirm, btnCancel;

    private Stage popupStage;
    private Stage parentStage;

    Usager currentUsager = null;

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

    public void fillInfos() {
        tfNom.setText(currentUsager.getNom());
        tfPrenom.setText(currentUsager.getPrenom());
        tfMail.setText(currentUsager.getEmail());
        tfTelephone.setText(String.valueOf(currentUsager.getTelephone()));
    }

    // Modifier un usager
    public void updateUsager(Usager usager) {
        String oldMail = usager.getEmail();
        usager.setNom(tfNom.getText());
        usager.setPrenom(tfPrenom.getText());
        usager.setEmail(tfMail.getText());
        usager.setTelephone(tfTelephone.getText());
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager, oldMail);
    }

    public void confirmUpdate() {
        updateUsager(currentUsager);

        popupStage.close();
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public void setCurrentUsager(Usager selectedUsager) {
        this.currentUsager = selectedUsager;
    }

    public void close() {
        // Close the stage (pop-up window)
        if (popupStage != null) {
            popupStage.close();
        }
    }
}
