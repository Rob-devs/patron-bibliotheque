package ul.miage.patron.controller.usagers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ul.miage.patron.database.helpers.HelperUsager;
import ul.miage.patron.model.Usager;

public class ControllerUpdateUsager {
    @FXML
    TextField tfNom, tfPrenom, tfMail, tfTelephone;
    
    @FXML
    Button btnConfirm, btnCancel;

    private Stage popupStage;
    private Stage parentStage;
    
    Usager currentUsager = null;

    public void fillInfos(){
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
        usager.setTelephone(Integer.parseInt(tfTelephone.getText()));
        HelperUsager helperUsager = new HelperUsager();
        helperUsager.updateUsager(usager, oldMail);
    }

    public void confirmUpdate(){
        updateUsager(currentUsager);

        ControllerUsager controllerBack = new ControllerUsager();
        controllerBack.reloadListView();

        popupStage.close();

        if(parentStage != null){
            parentStage.show();
        }
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
}
