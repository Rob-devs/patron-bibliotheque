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

    // Insérer un usager
    public void insertUsager() {
        Usager usager = new Usager(
                tfMail.getText(),
                tfNom.getText(),
                tfPrenom.getText(),
                Integer.parseInt(tfTelephone.getText()));
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.insertUsager(usager);
    }

    public void confirmAdd() {
        insertUsager();

        ControllerUsager controllerBack = new ControllerUsager();
        controllerBack.reloadListView();

        popupStage.close();

        if (parentStage != null) {
            parentStage.show();
        }
    }

    public void setPopupStage(Stage popupStage) {
        this.popupStage = popupStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
}
